package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.UserRole;
import com.bcdbook.meng.system.module.UserRoleKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:10
 */
public interface UserRoleRepository extends JpaRepository<UserRole,UserRoleKey> {

    /**
     * @param userId
     * @return
     * 根据用户id,获取用户/角色列表
     */
    List<UserRole> findByUserRoleKey_UserId(String userId);

    /**
     * @param roleId
     * @return
     * 根据角色id, 获取用户/角色列表
     */
    List<UserRole> findByUserRoleKey_RoleId(String roleId);
}
