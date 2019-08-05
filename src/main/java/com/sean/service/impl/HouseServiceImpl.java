package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.HouseSubscribeStatus;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.dao.HouseMapper;
import com.sean.dto.HouseDTO;
import com.sean.entity.House;
import com.sean.entity.HouseSubscribe;
import com.sean.form.DatatableSearch;
import com.sean.form.HouseForm;
import com.sean.form.MapSearch;
import com.sean.form.RentSearch;
import com.sean.service.IHouseService;
import javafx.util.Pair;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class HouseServiceImpl extends ServiceImpl<HouseMapper, House> implements IHouseService {

    @Override
    public ServiceResult<HouseDTO> save(HouseForm houseForm) {
        return null;
    }

    @Override
    public ServiceResult update(HouseForm houseForm) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseDTO> adminQuery(DatatableSearch searchBody) {
        return null;
    }

    @Override
    public ServiceResult<HouseDTO> findCompleteOne(Long id) {
        return null;
    }

    @Override
    public ServiceResult removePhoto(Long id) {
        return null;
    }

    @Override
    public ServiceResult updateCover(Long coverId, Long targetId) {
        return null;
    }

    @Override
    public ServiceResult addTag(Long houseId, String tag) {
        return null;
    }

    @Override
    public ServiceResult removeTag(Long houseId, String tag) {
        return null;
    }

    @Override
    public ServiceResult updateStatus(Long id, int status) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseDTO> query(RentSearch rentSearch) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseDTO> wholeMapQuery(MapSearch mapSearch) {
        return null;
    }

    @Override
    public ServiceMultiResult<HouseDTO> boundMapQuery(MapSearch mapSearch) {
        return null;
    }

    @Override
    public ServiceResult addSubscribeOrder(Long houseId) {
        return null;
    }

    @Override
    public ServiceMultiResult<Pair<HouseDTO, HouseSubscribe>> querySubscribeList(HouseSubscribeStatus status, int start, int size) {
        return null;
    }

    @Override
    public ServiceResult subscribe(Long houseId, Date orderTime, String telephone, String desc) {
        return null;
    }

    @Override
    public ServiceResult cancelSubscribe(Long houseId) {
        return null;
    }

    @Override
    public ServiceMultiResult<Pair<HouseDTO, HouseSubscribe>> findSubscribeList(int start, int size) {
        return null;
    }

    @Override
    public ServiceResult finishSubscribe(Long houseId) {
        return null;
    }
}
