package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.model.Role;
import com.bcdbook.meng.system.model.UserRole;
import com.bcdbook.meng.system.repository.RoleRepository;
import com.bcdbook.meng.system.repository.UserRoleRepository;
import com.bcdbook.meng.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:45
 * 角色service的实现类
 */
@Service
public class RoleServiceImpl implements RoleService {
    /*
     * 注入用户/角色中间表的数据库交互对象
     */
    @Autowired
    private UserRoleRepository userRoleRepository;
    /*
     * 注入角色表的资源对象
     */
    @Autowired
    private RoleRepository roleRepository;

    /**
     * @param userId
     * @return java.util.List<com.bcdbook.meng.system.model.Role>
     * @author summer
     * @date 2017/8/12 下午11:46
     * @description 根据用户的id, 获取其角色集合
     */
    @Override
    public List<Role> listRoleByUserId(String userId) {
        //根据用户id获取用户角色中间表对象的集合
        List<UserRole> userRoleList = userRoleRepository.findByUserRoleKey_UserId(userId);
        //判断集合的合法性, 为空则直接返回空
        if(userRoleList==null||userRoleList.isEmpty()){
            return null;
        }

        //根据获取到的中间对象的结合, 封装一个角色id的集合对象
        List<String> roleIdList = userRoleList
                .stream()
                .map(
                        e -> e.getUserRoleKey().getRoleId()
                )
                .collect(
                        Collectors.toList()
                );
        //根据角色id集合,获取角色对象集合
        List<Role> roleList = roleRepository.findByIdIsIn(roleIdList);

        return roleList;
    }
}
