package com.sean.service;

import com.sean.esdemo.BookService;
import com.sean.esdemo.BookVO;
import com.sean.esdemo.BoolQueryVO;
import com.sean.search.ISearchService;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class SearchServiceTest {


    @Autowired
    private ISearchService searchService;


    @Test
    public void index() {
        searchService.index(17L);
    }


    @Test
    public void remove() {
        searchService.remove(17L);
    }




}