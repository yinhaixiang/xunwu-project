package com.sean.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.sean.base.ServiceMultiResult;
import com.sean.base.ServiceResult;
import com.sean.dao.HouseMapper;
import com.sean.dto.HouseDTO;
import com.sean.entity.House;
import com.sean.entity.Subway;
import com.sean.form.DatatableSearch;
import com.sean.form.HouseForm;
import com.sean.form.PhotoForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceTest {


    @Autowired
    private IHouseService houseService;

    @Autowired
    private HouseMapper houseMapper;


    @Test
    @Rollback(false)
    public void save() {
        HouseForm houseForm = new HouseForm();
        houseForm.setTitle("title05");
        houseForm.setCityEnName("sh");
        houseForm.setRegionEnName("pudong");
        houseForm.setStreet("花木街道");
        houseForm.setDistrict("由由新村");
        houseForm.setDetailAddress("严中路373弄");
        houseForm.setRoom(3);
        houseForm.setParlour(1);
        houseForm.setFloor(6);
        houseForm.setTotalFloor(6);
        houseForm.setDirection(2);
        houseForm.setBuildYear(1990);
        houseForm.setArea(100);
        houseForm.setPrice(10000);
        houseForm.setRentWay(0);
        houseForm.setSubwayLineId(7L);
        houseForm.setSubwayStationId(7L);
        houseForm.setLayoutDesc("layoutDesc01");
        houseForm.setRoundService("roundService01");
        houseForm.setTraffic("traffic01");
        houseForm.setDescription("description01");
        houseForm.setCover("cover01");
        houseForm.setTags(Arrays.asList("tag01", "tag02"));

        List<PhotoForm> photos = Arrays.asList(
                new PhotoForm("ph01", 100, 100),
                new PhotoForm("ph02", 100, 100),
                new PhotoForm("ph03", 100, 100)
        );
        houseForm.setPhotos(photos);


        ServiceResult<HouseDTO> result = houseService.save(houseForm);
        System.out.println(result);
    }


    @Test
    public void adminQuery() throws ParseException {
        DatatableSearch searchBody = new DatatableSearch();
        searchBody.setStart(0);
        searchBody.setLength(10);
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date createTimeMax = ft.parse("2019-08-06 00:00:00");

        searchBody.setCreateTimeMax(createTimeMax);
        ServiceMultiResult<HouseDTO> result = houseService.adminQuery(searchBody);
        System.out.println(result);
    }


    @Test
    public void selectPage() {
        Page<House> page = new Page<House>(1, 2);
        LambdaQueryWrapper<House> wp = Wrappers.<House>lambdaQuery().ge(House::getId, 1L);
        IPage<House> result = houseService.page(page, wp);
        System.out.println(result);
    }

    @Test
    public void apply() {
        List<House> result = houseService.lambdaQuery().apply("date_format(create_time,'%Y-%m-%d') <= date_format({0},'%Y-%m-%d')", "2019-08-06 00:00:00").list();
        System.out.println(result);
    }


}