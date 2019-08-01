package com.sean.dao;

import com.sean.entity.Subway;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.SQLOutput;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SubwayMapperTest {


    @Autowired
    private SubwayMapper subwayMapper;


    @Test
    public void select() {
        List<Subway> list = subwayMapper.selectList(null);
        list.forEach(System.out::println);
    }
}