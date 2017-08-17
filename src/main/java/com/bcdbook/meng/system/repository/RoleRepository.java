package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
public interface RoleRepository extends JpaRepository<Role,String> {
    /**
     * @param roleList
     * @return
     * 根据角色id集合,获取角色对象集合
     */
    List<Role> findByIdIsIn(List<String> roleList);

}
