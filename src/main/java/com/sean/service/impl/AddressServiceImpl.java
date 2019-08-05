package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.base.ServiceMultiResult;
import com.sean.dao.SubwayMapper;
import com.sean.dao.SupportAddressMapper;
import com.sean.entity.Subway;
import com.sean.entity.SupportAddress;
import com.sean.service.IAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements IAddressService {


    @Autowired
    private SubwayMapper subwayMapper;

    @Override
    public ServiceMultiResult<SupportAddress> findAllCities() {
        List<SupportAddress> result = this.lambdaQuery().eq(SupportAddress::getLevel, "city").list();
        return new ServiceMultiResult(result.size(), result);
    }

    @Override
    public List<Subway> findAllSubwayByCity(String cityEnName) {
        List<Subway> result = new LambdaQueryChainWrapper<Subway>(subwayMapper).eq(Subway::getCityEnName, cityEnName).list();
        return result;
    }
}
