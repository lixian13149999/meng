package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.system.model.Role;
import com.bcdbook.meng.system.model.UserRole;
import com.bcdbook.meng.system.repository.RoleRepository;
import com.bcdbook.meng.system.repository.UserRoleRepository;
import com.bcdbook.meng.system.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:45
 * 角色service的实现类
 */
@Service
@Slf4j
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

    /**
     * @author summer
     * @date 2017/8/24 下午2:17
     * @param role
     * @return com.bcdbook.meng.system.model.Role
     * @description 添加角色的方法
     */
    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    /**
     * @author summer
     * @date 2017/8/24 下午2:16
     * @return java.util.List<com.bcdbook.meng.system.model.Role>
     * @description 查询所有角色列表
     */
    @Override
    public List<Role> findAllOrderBySort() {

        return roleRepository.findAll(new Sort("sort"));
    }

    @Override
    public void delete(String roleId) {
        roleRepository.delete(roleId);
    }

    /**
     * @author summer
     * @date 2017/8/21 下午3:14
     * @param roleMap
     * @return boolean
     * @description 对资源进行排序
     */
    @Override
    @Transactional
    public boolean sort(Map<String, Integer> roleMap) {
        //参数合法性校验, 如果传入的对象为空,则直接返回true
        if(roleMap==null){
            return true;
        }

        //循环传入的map集合,获取对应的值和顺序值
        for (String roleId: roleMap.keySet()) {
            if(StringUtils.isEmpty(roleId)){
                log.error("[角色排序] 角色id为空,无法执行排序操作");
                throw new CommonException(ResultEnum.PARAM_ERROR);
            }
            //根据资源id,获取资源对象
            Role sourceRole = roleRepository.findOne(roleId);
            //如果获取的对象为空,则跳过本次循环
            if(sourceRole==null){
                log.error("[角色排序] 需要排序的角色对象为空");
                throw new CommonException(ResultEnum.FOREIGN_DATA_IS_EMPTY);
            }
            //设置最新的顺序值
            sourceRole.setSort(roleMap.get(roleId));
            //保存排序
            roleRepository.save(sourceRole);
        }

        return true;
    }

    @Override
    public Role findOne(String roleId) {
        return roleRepository.findOne(roleId);
    }
}
