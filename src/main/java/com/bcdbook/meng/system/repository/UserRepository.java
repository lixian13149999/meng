package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
public interface UserRepository extends JpaRepository<User,String> {

    /**
     * 根据用户名, 获取用户对象
     * @param username
     * @return
     */
    User findByUsername(String username);

    User findByUsernameAndUserPassword(String username, String userPassword);

    User findByMobile(String mobile);

    User findByEmail(String email);
}
