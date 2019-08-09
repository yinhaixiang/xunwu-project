package com.sean.esdemo;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;

/**
 * es接口
 */
@Slf4j
@Service
public class BookService {
    @Resource
    private RestHighLevelClient client;

    public GetResponse findBookById(String id) {
        GetRequest request = new GetRequest("book", id);
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public IndexResponse addBook(BookVO vo) {
        try {
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("type", vo.getType())
                    .field("word_count", vo.getWordCount())
                    .field("author", vo.getAuthor())
                    .field("title", vo.getTitle())
                    .timeField("publish_date", vo.getPublishDate())
                    .endObject();
            IndexRequest request = new IndexRequest("book").source(content);
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public UpdateResponse update(BookVO vo) {
        try {
            UpdateRequest request = new UpdateRequest("book", vo.getId());
            XContentBuilder content = XContentFactory.jsonBuilder().startObject()
                    .field("type", vo.getType())
                    .field("word_count", vo.getWordCount())
                    .field("author", vo.getAuthor())
                    .field("title", vo.getTitle())
                    .timeField("publish_date", vo.getPublishDate())
                    .endObject();
            request.doc(content);
            UpdateResponse response = client.update(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public DeleteResponse delete(String id) {
        try {
            DeleteRequest request = new DeleteRequest("book").id(id);
            DeleteResponse response = client.delete(request, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public SearchResponse boolQuery(BoolQueryVO vo) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (!StringUtils.isEmpty(vo.getAuthor())) {
            boolQuery.must(QueryBuilders.matchQuery("author", vo.getAuthor()));
        }
        if (!StringUtils.isEmpty(vo.getTitle())) {
            boolQuery.must(QueryBuilders.matchQuery("title", vo.getTitle()));
        }
        if (vo.getGtWordCount() != null && vo.getLtWordCount() != null) {
            RangeQueryBuilder rangeQuery = QueryBuilders.rangeQuery("word_count")
                    .from(vo.getGtWordCount()).to(vo.getLtWordCount());
            boolQuery.filter(rangeQuery);
        }
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(boolQuery);
        SearchRequest searchRequest = new SearchRequest().source(searchSourceBuilder);
        log.warn("searchSourceBuilder: {}", searchSourceBuilder);
        try {
            SearchResponse response = client.search(searchRequest, RequestOptions.DEFAULT);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
