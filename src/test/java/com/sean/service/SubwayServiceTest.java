package com.sean.service;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.sean.dao.SubwayMapper;
import com.sean.entity.Subway;
import com.sean.service.impl.SubwayServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@MapperScan("com.sean.dao")
@SpringBootTest(classes = {
        SubwayMapper.class,
        SubwayServiceImpl.class
})
public class SubwayServiceTest {

    @Autowired
    private SubwayMapper subwayMapper;

    @Autowired
    private ISubwayService subwayService;

    @Test
    public void select() {
        List<Subway> result = subwayMapper.selectList(null);
        result.forEach(System.out::println);
    }


    @Test
    public void update() {
        UpdateWrapper<Subway> wrapper = new UpdateWrapper<>();
        wrapper.eq("id", 1);
        wrapper.set("city_en_name", null);
        Subway subway = new Subway();
        subway.setId(1L);
        subway.setName("123");
        subway.setCityEnName("456");
        boolean result = subwayService.updateById(subway);
        System.out.println(result);


    }


    @Test
    public void insert() {
        Subway subway = new Subway();
        subway.setName("123");
        subway.setCityEnName("456");
        boolean result = subwayService.save(subway);
        System.out.println(subway);
        System.out.println(result);


    }

    public static void main(String[] args) {
        List<Subway> list = new ArrayList<>();
        list.add(Subway.builder().id(1L).name("aa").cityEnName("xx").build());
        list.add(Subway.builder().id(2L).name("bb").cityEnName("yy").build());
        list.add(Subway.builder().id(3L).name("cc").cityEnName("zz").build());

//        System.out.println(list);

        Map<String, List<Subway>> result = list.stream().collect(Collectors.groupingBy(Subway::getName));
        System.out.println(result);
    }

}