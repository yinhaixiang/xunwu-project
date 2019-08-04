package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.entity.SupportAddress;

import java.util.List;

public interface IAddressService extends IService<SupportAddress> {

    /**
     * 获取所有支持的城市列表
     * @return
     */
    List<SupportAddress> findAllCities();

}
