package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.base.ServiceMultiResult;
import com.sean.dao.SupportAddressMapper;
import com.sean.entity.SupportAddress;
import com.sean.service.IAddressService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl extends ServiceImpl<SupportAddressMapper, SupportAddress> implements IAddressService {

    @Override
    public ServiceMultiResult<SupportAddress> findAllCities() {
        List<SupportAddress> result = this.lambdaQuery().eq(SupportAddress::getLevel, "city").list();
        return new ServiceMultiResult(result.size(), result);
    }
}
