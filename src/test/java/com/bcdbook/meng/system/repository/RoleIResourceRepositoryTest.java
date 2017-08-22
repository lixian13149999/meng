package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.RoleIResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/22 下午3:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class RoleIResourceRepositoryTest {

    @Autowired
    private RoleIResourceRepository roleIResourceRepository;

    @Test
    public void findByRoleIResourceKey_RoleIdIn() throws Exception {

        List<String> roleIdList = Arrays.asList("123","1");

        List<RoleIResource> roleIResourceList = roleIResourceRepository.findByRoleIResourceKey_RoleIdIn(roleIdList);

        log.info("[资源查询] 根据用户查询资源 size={}",roleIResourceList.size());
    }

}