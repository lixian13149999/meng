package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.model.Role;

import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:43
 * 角色的service接口
 */
public interface RoleService {
    Role save(Role role);

    List<Role> listRoleByUserId(String userId);

    List<Role> findAllOrderBySort();

    void delete(String roleId);

    boolean sort(Map<String, Integer> sortFormMaps);

    Role findOne(String roleId);
}
