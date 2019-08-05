package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.base.ServiceMultiResult;
import com.sean.entity.Subway;
import com.sean.entity.SupportAddress;

import java.util.List;

public interface IAddressService extends IService<SupportAddress> {

    /**
     * 获取所有支持的城市列表
     *
     * @return
     */
    ServiceMultiResult<SupportAddress> findAllCities();


    /**
     * 获取该城市所有的地铁线路
     * @param cityEnName
     * @return
     */
    List<Subway> findAllSubwayByCity(String cityEnName);

}
