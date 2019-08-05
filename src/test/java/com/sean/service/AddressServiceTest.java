package com.sean.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.sean.base.ServiceMultiResult;
import com.sean.entity.Subway;
import com.sean.entity.SupportAddress;
import com.sean.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {


    @Autowired
    private IAddressService addressService;


    @Test
    public void findAllCities() {
        ServiceMultiResult<SupportAddress> result = addressService.findAllCities();
        System.out.println(result);
    }

    @Test
    public void findAllSubwayByCity() {
        String cityEnName = "bj";
        List<Subway> result = addressService.findAllSubwayByCity(cityEnName);
        System.out.println(result);
    }


    @Test
    public void findCityAndRegion() {
        Map<SupportAddress.Level, SupportAddress> result = addressService.findCityAndRegion("bj", "dcq");
        System.out.println(result);
    }





}