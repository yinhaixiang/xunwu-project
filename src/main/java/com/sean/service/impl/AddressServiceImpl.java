package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.dao.SubwayMapper;
import com.sean.dao.SubwayStationMapper;
import com.sean.dao.SupportAddressMapper;
import com.sean.dto.BaiduMapLocation;
import com.sean.entity.Subway;
import com.sean.entity.SubwayStation;
import com.sean.entity.SupportAddress;
import com.sean.service.IAddressService;
import com.sean.service.ISubwayService;
import com.sean.service.ISubwayStationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements IAddressService {


    @Autowired
    private ISubwayService subwayService;

    @Autowired
    private ISubwayStationService subwayStationService;

    @Override
    public ServiceMultiResult<SupportAddress> findAllCities() {
        List<SupportAddress> result = this.lambdaQuery().eq(SupportAddress::getLevel, SupportAddress.Level.CITY.getValue()).list();
        return new ServiceMultiResult(result.size(), result);
    }

    @Override
    public Map<SupportAddress.Level, SupportAddress> findCityAndRegion(String cityEnName, String regionEnName) {
        Map<SupportAddress.Level, SupportAddress> result = new HashMap<>();

        SupportAddress city = this.lambdaQuery().eq(SupportAddress::getEnName, cityEnName).eq(SupportAddress::getLevel, SupportAddress.Level.CITY.getValue()).one();
        SupportAddress region = this.lambdaQuery().eq(SupportAddress::getEnName, regionEnName).eq(SupportAddress::getBelongTo, city.getEnName()).one();

        result.put(SupportAddress.Level.CITY, city);
        result.put(SupportAddress.Level.REGION, region);
        return result;
    }

    @Override
    public ServiceMultiResult findAllRegionsByCityName(String cityName) {
        if (cityName == null) {
            return new ServiceMultiResult<>(0, null);
        }

        List<SupportAddress> regions = this.lambdaQuery().eq(SupportAddress::getLevel, SupportAddress.Level.REGION.getValue()).eq(SupportAddress::getBelongTo, cityName).list();

        return new ServiceMultiResult(regions.size(), regions);
    }

    @Override
    public List<Subway> findAllSubwayByCity(String cityEnName) {
        List<Subway> result = subwayService.lambdaQuery().eq(Subway::getCityEnName, cityEnName).list();
        return result;
    }

    @Override
    public List<SubwayStation> findAllStationBySubway(Long subwayId) {
        List<SubwayStation> stations = subwayStationService.lambdaQuery().eq(SubwayStation::getId, subwayId).list();
        return stations;
    }

    @Override
    public ServiceResult<Subway> findSubway(Long subwayId) {
        if (subwayId == null) {
            return ServiceResult.notFound();
        }
        Subway subway = subwayService.getById(subwayId);
        if (subway == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(subway);
    }

    @Override
    public ServiceResult<SubwayStation> findSubwayStation(Long stationId) {
        if (stationId == null) {
            return ServiceResult.notFound();
        }
        SubwayStation station = subwayStationService.getById(stationId);
        if (station == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(station);
    }

    @Override
    public ServiceResult<SupportAddress> findCity(String cityEnName) {
        if (cityEnName == null) {
            return ServiceResult.notFound();
        }


        SupportAddress supportAddress = this.lambdaQuery().eq(SupportAddress::getEnName, cityEnName).eq(SupportAddress::getLevel, SupportAddress.Level.CITY.getValue()).one();
        if (supportAddress == null) {
            return ServiceResult.notFound();
        }
        return ServiceResult.of(supportAddress);
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
