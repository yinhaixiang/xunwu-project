package com.sean.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sean.base.ServiceResult;
import com.sean.entity.User;

public interface IUserService extends IService<User> {

    /**
     * 通过手机号注册用户
     *
     * @param telehone
     * @return
     */
    User addUserByPhone(String telephone);

    /**
     * 修改指定属性值
     *
     * @param profile
     * @param value
     * @return
     */
    ServiceResult modifyUserProfile(String profile, String value);
}
