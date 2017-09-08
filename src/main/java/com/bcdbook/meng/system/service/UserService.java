package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

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

    User findByUsername(String username);

    User findByUsernameAndUserPassword(String username, String userPassword);

    UserDTO save(UserDTO userDTO);

    UserDTO save(User user);

    User findByMobile(String mobile);

    User findByEmail(String email);

    List<String> listUserIdByRoleId(String roleId);

    Page<UserDTO> listUserByUserTypeAndKey(Integer userType,String parameter, Pageable pageable);
}
