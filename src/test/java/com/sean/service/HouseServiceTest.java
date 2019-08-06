package com.sean.service;

import com.sean.base.ServiceResult;
import com.sean.dto.HouseDTO;
import com.sean.form.HouseForm;
import com.sean.form.PhotoForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class HouseServiceTest {


    @Autowired
    private IHouseService houseService;


    @Test
    @Rollback(true)
    public void save() {
        HouseForm houseForm = new HouseForm();
        houseForm.setTitle("title01");
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


}