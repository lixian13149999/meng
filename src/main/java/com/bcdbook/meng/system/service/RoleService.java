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

    /**
     * 保存角色
     * @param role
     * @return
     */
    Role save(Role role);

    /**
     * 根据用户的id, 查询出角色的id集合
     * @param userId
     * @return
     */
    List<String> listRoleIdByUserId(String userId);

    /**
     * 根据用户的id, 查询角色的集合
     * @param userId
     * @return
     */
    List<Role> listRoleByUserId(String userId);

    /**
     * 查询所有的集合,并根据排序值进行排序
     * @return
     */
    List<Role> findAllOrderBySort();

    /**
     * 删除角色(根据角色的id)
     * @param roleId
     */
    void delete(String roleId);

    /**
     * 对角色进行排序
     * @param sortFormMaps
     * @return
     */
    boolean sort(Map<String, Integer> sortFormMaps);

    /**
     * 查询单个角色对象
     * @param roleId
     * @return
     */
    Role findOne(String roleId);
}
