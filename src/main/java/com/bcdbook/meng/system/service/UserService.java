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

    /**
     * 根据用户名,查询用户对象
     * @param username
     * @return
     */
    User findByUsername(String username);

    /**
     * 根据用户名和密码,查询用户对象
     * @param username
     * @param userPassword
     * @return
     */
    User findByUsernameAndUserPassword(String username, String userPassword);

    /**
     * 保存用户(传入DTO)
     * @param userDTO
     * @return
     */
    UserDTO save(UserDTO userDTO);

    /**
     * 保存用户(传入user对象)
     * @param user
     * @return
     */
    UserDTO save(User user);

    /**
     * 根据用户的手机号查询用户
     * @param mobile
     * @return
     */
    User findByMobile(String mobile);

    /**
     * 根据用户的邮箱,查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据角色的id,查询用户的id集合
     * @param roleId
     * @return
     */
    List<String> listUserIdByRoleId(String roleId);

    /**
     * 根据用户的类型和关键字(用户名/昵称/手机号),查询用户对象
     * @param userType
     * @param parameter
     * @param pageable
     * @return
     */
    Page<UserDTO> listUserByUserTypeAndKey(Integer userType,String parameter, Pageable pageable);
}
