package com.sean.search;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.entity.House;
import com.sean.form.MapSearch;
import com.sean.form.RentSearch;
import com.sean.service.IHouseService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    private RestHighLevelClient client;

    @Autowired
    private ObjectMapper objectMapper;


    @Override
    public void index(Long houseId) {
        House house = houseService.getById(houseId);
        if (house == null) {
            log.error("Index house {} does not exist!", houseId);
        }
        HouseIndexTemplate indexTemplate = new HouseIndexTemplate();
        modelMapper.map(house, indexTemplate);


    }


    private boolean create(HouseIndexTemplate indexTemplate) {
        try {
            IndexRequest request = new IndexRequest(INDEX_NAME).source(objectMapper.writeValueAsBytes(indexTemplate), XContentType.JSON);
            log.debug("source: {}", request.sourceAsMap());
            IndexResponse response = client.index(request, RequestOptions.DEFAULT);
            if (response.status() == RestStatus.CREATED) {
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
            UpdateResponse response = this.client.update(request, RequestOptions.DEFAULT);

            log.debug("Update index with house: " + indexTemplate.getHouseId());
            if (response.status() == RestStatus.OK) {
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
//        DeleteByQueryRequestBuilder builder = DeleteByQueryAction.INSTANCE
//                .newRequestBuilder(esClient)
//                .filter(QueryBuilders.termQuery(HouseIndexKey.HOUSE_ID, indexTemplate.getHouseId()))
//                .source(INDEX_NAME);
//
//        logger.debug("Delete by query for house: " + builder);
//
//        BulkByScrollResponse response = builder.get();
//        long deleted = response.getDeleted();
//        if (deleted != totalHit) {
//            logger.warn("Need delete {}, but {} was deleted!", totalHit, deleted);
//            return false;
//        } else {
//            return create(indexTemplate);
//        }
        return true;
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
