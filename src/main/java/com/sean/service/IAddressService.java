package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.base.ServiceMultiResult;
import com.sean.entity.SupportAddress;

public interface IAddressService extends IService<SupportAddress> {

    /**
     * 获取所有支持的城市列表
     *
     * @return
     */
    ServiceMultiResult<SupportAddress> findAllCities();

}
