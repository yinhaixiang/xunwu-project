package com.sean.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.entity.House;
import com.sean.entity.HouseDetail;
import com.sean.entity.HouseTag;
import com.sean.form.MapSearch;
import com.sean.form.RentSearch;
import com.sean.service.IHouseDetailService;
import com.sean.service.IHouseService;
import com.sean.service.IHouseTagService;
import lombok.extern.slf4j.Slf4j;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.index.reindex.DeleteByQueryRequestBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchServiceImpl implements ISearchService {

    private static final String INDEX_NAME = "xunwu";

    @Autowired
    private IHouseService houseService;

    @Autowired
    private ModelMapper modelMapper;

    @Resource
    private RestHighLevelClient esClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IHouseDetailService houseDetailService;

    @Autowired
    private IHouseTagService houseTagService;


    @Override
    public void index(Long houseId) {
        try {
            House house = houseService.getById(houseId);
            if (house == null) {
                log.error("Index house {} does not exist!", houseId);
            }
            HouseIndexTemplate indexTemplate = new HouseIndexTemplate();
            modelMapper.map(house, indexTemplate);

            HouseDetail detail = houseDetailService.lambdaQuery().eq(HouseDetail::getHouseId, houseId).one();
            modelMapper.map(detail, indexTemplate);

            List<HouseTag> tags = houseTagService.lambdaQuery().eq(HouseTag::getHouseId, houseId).list();

            if (tags != null && !tags.isEmpty()) {
                List<String> tagStrings = new ArrayList<>();
                tags.forEach(houseTag -> tagStrings.add(houseTag.getName()));
                indexTemplate.setTags(tagStrings);
            }

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().query(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, houseId));
            SearchRequest searchRequest = new SearchRequest().source(searchSourceBuilder);
            log.debug("searchSourceBuilder: {}", searchSourceBuilder);
            SearchResponse searchResponse = esClient.search(searchRequest, RequestOptions.DEFAULT);
            log.debug("searchResponse: {}", searchResponse);


            boolean success;
            int totalHit = searchResponse.getHits().getHits().length;
            if (totalHit == 0) {
                success = create(indexTemplate);
            } else if (totalHit == 1) {
                String esId = searchResponse.getHits().getAt(0).getId();
                success = update(esId, indexTemplate);
            } else {
                success = deleteAndCreate(totalHit, indexTemplate);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private boolean create(HouseIndexTemplate indexTemplate) {
        try {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME).source(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON);
            log.debug("indexRequest.source: {}", indexRequest.sourceAsMap());
            IndexResponse indexResponse = esClient.index(indexRequest, RequestOptions.DEFAULT);
            log.debug("indexResponse: {}", indexResponse);
            if (indexResponse.status() == RestStatus.CREATED) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            log.error("Error to index house " + indexTemplate.getHouseId(), e);
            return false;
        }
    }

    private boolean update(String esId, HouseIndexTemplate indexTemplate) {
        try {
            UpdateRequest request = new UpdateRequest(INDEX_NAME, esId);
            request.doc(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON);
            UpdateResponse updateResponse = this.esClient.update(request, RequestOptions.DEFAULT);

            log.debug("updateResponse: {}", updateResponse);
            if (updateResponse.status() == RestStatus.OK) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            log.error("Error to index house " + indexTemplate.getHouseId(), e);
            return false;
        }
    }

    private boolean deleteAndCreate(long totalHit, HouseIndexTemplate indexTemplate) {

        try {
            DeleteByQueryRequest deleteByQueryRequest = new DeleteByQueryRequest(INDEX_NAME);
            deleteByQueryRequest.setConflicts("proceed");
            deleteByQueryRequest.setQuery(new TermQueryBuilder(HouseIndexKey.HOUSE_ID, indexTemplate.getHouseId()));


            log.debug("Delete by query for house: " + deleteByQueryRequest);

            BulkByScrollResponse bulkByScrollResponse = esClient.deleteByQuery(deleteByQueryRequest, RequestOptions.DEFAULT);

            log.debug("bulkByScrollResponse: {}", bulkByScrollResponse);

            long deleted = bulkByScrollResponse.getDeleted();
            if (deleted != totalHit) {
                log.warn("Need delete {}, but {} was deleted!", totalHit, deleted);
                return false;
            } else {
                return create(indexTemplate);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public void remove(Long houseId) {

    }

    @Override
    public ServiceMultiResult<Long> query(RentSearch rentSearch) {
        return null;
    }

    @Override
    public ServiceResult<List<String>> suggest(String prefix) {
        return null;
    }

    @Override
    public ServiceResult<Long> aggregateDistrictHouse(String cityEnName, String regionEnName, String district) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseBucketDTO> mapAggregate(String cityEnName) {
        return null;
    }

    @Override
    public ServiceMultiResult<Long> mapQuery(String cityEnName, String orderBy, String orderDirection, int start, int size) {
        return null;
    }

    @Override
    public ServiceMultiResult<Long> mapQuery(MapSearch mapSearch) {
        return null;
    }
}
