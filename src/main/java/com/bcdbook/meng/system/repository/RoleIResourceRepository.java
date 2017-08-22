package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.RoleIResource;
import com.bcdbook.meng.system.module.RoleIResourceKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:10
 */
public interface RoleIResourceRepository extends JpaRepository<RoleIResource,RoleIResourceKey> {

    /**
     * @param roleIdList
     * @return
     * 根据角色的id集合, 获取角色资源中间表对象
     */
    List<RoleIResource> findByRoleIResourceKey_RoleIdIn(List<String> roleIdList);
}
