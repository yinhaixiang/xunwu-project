package com.sean.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sean.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AddressServiceTest {


    @Autowired
    private IUserService userService;


    @Test
    public void getOne() {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getStatus, 0), false);
        System.out.println(user);
    }




    @Test
    @Rollback(true)
    public void update() {
        boolean result = userService.lambdaUpdate().gt(User::getStatus, 0).set(User::getAvatar, "xxx").update();
        System.out.println(result);
    }

}