package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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

    @Test
    public void listRoleByUserId() throws Exception {
        List<Role> roleList = roleService.listRoleByUserId("92077e8b-5cf7-4f10-a762-61b691724583");
        Assert.assertNotNull(roleList);
    }

}