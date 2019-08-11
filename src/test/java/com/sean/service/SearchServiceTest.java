package com.sean.service;

import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.esdemo.BookService;
import com.sean.esdemo.BookVO;
import com.sean.esdemo.BoolQueryVO;
import com.sean.form.RentSearch;
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


    @Test
    public void query() {
        RentSearch rentSearch = new RentSearch();
        rentSearch.setCityEnName("bj");
        rentSearch.setStart(0);
        rentSearch.setSize(10);
        rentSearch.setOrderBy("houseId");
        rentSearch.setOrderDirection("asc");
        ServiceMultiResult<Long> result = searchService.query(rentSearch);
        System.out.println(result);
    }

    @Test
    public void aggregateDistrictHouse() {
        String cityEnName = "bj";
        String regionEnName = "hdq";
        String district = "融泽嘉园";
        ServiceResult<Long> result = searchService.aggregateDistrictHouse(cityEnName, regionEnName, district);
        System.out.println(result);
    }


}