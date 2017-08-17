package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.model.Role;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:43
 * 角色的service接口
 */
public interface RoleService {

    List<Role> listRoleByUserId(String userId);
}
