package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.dao.SubwayMapper;
import com.sean.dao.SupportAddressMapper;
import com.sean.dto.BaiduMapLocation;
import com.sean.entity.Subway;
import com.sean.entity.SubwayStation;
import com.sean.entity.SupportAddress;
import com.sean.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements IAddressService {


    @Autowired
    private SubwayMapper subwayMapper;

    @Override
    public ServiceMultiResult<SupportAddress> findAllCities() {
        List<SupportAddress> result = this.lambdaQuery().eq(SupportAddress::getLevel, SupportAddress.Level.CITY.getValue()).list();
        return new ServiceMultiResult(result.size(), result);
    }

    @Override
    public Map<SupportAddress.Level, SupportAddress> findCityAndRegion(String cityEnName, String regionEnName) {
        Map<SupportAddress.Level, SupportAddress> result = new HashMap<>();

        SupportAddress city = this.lambdaQuery().eq(SupportAddress::getEnName, cityEnName).eq(SupportAddress::getLevel, SupportAddress.Level.CITY.getValue()).one();
        SupportAddress region = this.lambdaQuery().eq(SupportAddress::getEnName, regionEnName).eq(SupportAddress::getLevel, SupportAddress.Level.REGION.getValue()).one();

        result.put(SupportAddress.Level.CITY, city);
        result.put(SupportAddress.Level.REGION, region);
        return result;
    }

    @Override
    public ServiceMultiResult findAllRegionsByCityName(String cityName) {
        return null;
    }

    @Override
    public List<Subway> findAllSubwayByCity(String cityEnName) {
        List<Subway> result = new LambdaQueryChainWrapper<Subway>(subwayMapper).eq(Subway::getCityEnName, cityEnName).list();
        return result;
    }

    @Override
    public List<SubwayStation> findAllStationBySubway(Long subwayId) {
        return null;
    }

    @Override
    public ServiceResult<Subway> findSubway(Long subwayId) {
        return null;
    }

    @Override
    public ServiceResult<SubwayStation> findSubwayStation(Long stationId) {
        return null;
    }

    @Override
    public ServiceResult<SupportAddress> findCity(String cityEnName) {
        return null;
    }

    @Override
    public ServiceResult<BaiduMapLocation> getBaiduMapLocation(String city, String address) {
        return null;
    }

    @Override
    public ServiceResult lbsUpload(BaiduMapLocation location, String title, String address, long houseId, int price, int area) {
        return null;
    }

    @Override
    public ServiceResult removeLbs(Long houseId) {
        return null;
    }
}
