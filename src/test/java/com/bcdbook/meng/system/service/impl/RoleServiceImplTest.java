package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/13 上午12:19
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RoleServiceImplTest {

    @Autowired
    private RoleServiceImpl roleService;

    /**
     * 测试添加角色的方法
     */
    @Test
    public void saveTest(){
        Role role = new Role();
        role.setName("管理员");
        role.setSort(1);
        role.setIntro("管理员角色");

        Role result = roleService.save(role);

        Assert.assertNotNull(result);
    }

    /**
     * 根据用户的id,查询角色的id集合
     */
    @Test
    public void listRoleIdByUserIdTest(){
        //-TODO 处理用户的基础操作后再进行此处的测试
    }

    /**
     * 根据用户的id,查询角色的对象集合
     */
    @Test
    public void listRoleByUserIdTest(){
        //-TODO 处理用户的基础操作后再进行此处的测试
    }

    /**
     * 查询所有的角色集合
     * 1. 按照sort值进行排序
     */
    @Test
    public void findAllOrderBySortTest(){
        List<Role> roleList = roleService.findAllOrderBySort();
        Assert.assertNotNull(roleList);
    }

    @Test
    public void deleteTest(){
        roleService.delete("3ef50c9c-6d98-460e-848f-24207b084439");
    }

    @Test
    public void sortTest(){
        Map<String,Integer> sort = new HashMap<>();
        sort.put("fd618570-8778-4b1c-809b-826eba55ace3",1);
        sort.put("ae51d7e1-14fd-4797-a240-e30a4b4f3455",3);
        sort.put("8509902c-8726-4b16-8dfd-133da03fec2a",2);

        roleService.sort(sort);
    }

//    @Test
//    public void listRoleByUserIdTest() throws Exception {
//        List<Role> roleList = roleService.listRoleByUserId("92077e8b-5cf7-4f10-a762-61b691724583");
//        Assert.assertNotNull(roleList);
//    }

}