package com.sean.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sean.base.ServiceResult;
import com.sean.dao.UserMapper;
import com.sean.entity.User;
import com.sean.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User addUserByPhone(String telephone) {
        User user = new User();
        user.setPhoneNumber(telephone);
        user.setName(telephone.substring(0, 3) + "****" + telephone.substring(7, telephone.length()));
        Date now = new Date();
        user.setCreateTime(now);
        user.setLastLoginTime(now);
        user.setLastUpdateTime(now);
        userMapper.insert(user);
        return user;
    }

    @Override
    public ServiceResult modifyUserProfile(String profile, String value) {
        Long userId = 1L;
        if (profile == null || profile.isEmpty()) {
            return new ServiceResult(false, "属性不可以为空");
        }
        switch (profile) {
            case "name":
                this.lambdaUpdate().eq(User::getId, userId).set(User::getName, value).update();
                break;
            case "email":
                this.lambdaUpdate().eq(User::getId, userId).set(User::getEmail, value).update();
                break;
            default:
                return new ServiceResult(false, "不支持的属性");
        }
        return ServiceResult.success();
    }
}
