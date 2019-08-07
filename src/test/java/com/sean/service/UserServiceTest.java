package com.sean.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.sean.base.ServiceResult;
import com.sean.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {


    @Autowired
    private IUserService userService;


    @Test
    public void getOne() {
        User user = userService.getOne(Wrappers.<User>lambdaQuery().gt(User::getStatus, 0), false);
        System.out.println(user);
    }


    @Test
    public void getOne2() {
        User result = userService.lambdaQuery().gt(User::getStatus, 0).one();
        System.out.println(result);
    }


    @Test
    @Rollback(false)
    public void update() {
        boolean result = userService.lambdaUpdate().gt(User::getStatus, 0).set(User::getAvatar, "zzz").update();
        System.out.println(result);
    }


    @Test
    @Rollback(true)
    public void addUserByPhone() {
        User result = userService.addUserByPhone("13477777777");
        System.out.println(result);
    }

    @Test
    @Rollback(false)
    public void modifyUserProfile() {
        ServiceResult result = userService.modifyUserProfile("name", "sean");
        System.out.println(result);
    }








}