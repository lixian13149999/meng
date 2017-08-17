package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.model.User;
import com.bcdbook.meng.system.repository.UserRepository;
import com.bcdbook.meng.system.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @Author summer
 * @Date 2017/8/13 上午9:52
 * UserService的实现类
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //引入user的资源对象
    @Autowired
    private UserRepository userRepository;

    @Override
    public User findOne(String userId) {
        return userRepository.findOne(userId);
    }

    @Override
    public User findUserByUsernameAndUserPassword(String username, String userPassword) {
        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(userPassword)){
            log.warn("[用户登录] 用户名或密码为空");
            //- TODO 此处需要抛出异常,
            return null;
        }

        return userRepository.findUserByUsernameAndUserPassword(username,userPassword);
    }
}
