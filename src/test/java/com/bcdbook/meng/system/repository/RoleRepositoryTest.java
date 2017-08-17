package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午7:26
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveTest(){
        Role role = new Role();

        role.setName("角色名称2");

        roleRepository.save(role);
    }

    @Test
    public void updateTest(){
        Role role = new Role();
        role.setId("7a6c203b-578d-4a1f-860e-fd8281d86a81");
        role.setName("更新后的角色名称");
        roleRepository.save(role);
    }

    @Test
    public void findByIdIsInTest(){
        List<String> roleIdList = Arrays.asList("83cafa4d-a03e-412a-bf1d-7cf947146ab7", "e5f82bed-46cd-4411-bf6b-0d6d182eff90");
        List<Role> roleList = roleRepository.findByIdIsIn(roleIdList);
        Assert.assertNotEquals(0,roleList.size());
    }

}