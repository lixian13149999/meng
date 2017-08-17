package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.model.User;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:42
 * 用户的service接口
 */
public interface UserService {

    /**
     * 根据用户id获取用户对象的方法
     * @param userId
     * @return
     */
    User findOne(String userId);

    User findUserByUsernameAndUserPassword(String username, String userPassword);
}
