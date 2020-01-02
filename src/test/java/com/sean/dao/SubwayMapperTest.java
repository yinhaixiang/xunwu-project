package com.sean.dao;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sean.entity.Subway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@MapperScan("com.sean.dao")
@SpringBootTest(classes = {
        SubwayMapper.class
})
public class SubwayMapperTest {


    @Autowired
    private SubwayMapper subwayMapper;


    @Test
    public void select() {
        List<Subway> result = subwayMapper.selectList(null);
        result.forEach(System.out::println);
    }


    @Test
    public void insert() {
        Subway subway = new Subway();
        subway.setName("你好");
        subway.setCityEnName("sh");
        int result = subwayMapper.insert(subway);
        System.out.println(result);

    }

    @Test
    public void selectBatchIds() {
        List<Long> idList = Arrays.asList(1L, 2L, 3L);
        List<Subway> result = subwayMapper.selectBatchIds(idList);
        result.forEach(System.out::println);
    }

    @Test
    public void selectPage() {
        QueryWrapper<Subway> wrapper = new QueryWrapper<Subway>();

        Page<Subway> page = new Page<Subway>(2, 2);

        IPage<Subway> result = subwayMapper.selectPage(page, wrapper);
        System.out.println(result.getPages());
        System.out.println(result.getTotal());
        System.out.println(result.getRecords());
    }


}