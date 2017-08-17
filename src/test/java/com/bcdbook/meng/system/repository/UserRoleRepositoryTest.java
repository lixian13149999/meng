package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.UserRole;
import com.bcdbook.meng.system.module.UserRoleKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午11:12
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRoleRepositoryTest {

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Test
    public void saveTest(){
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId("92077e8b-5cf7-4f10-a762-61b691724583");
        userRoleKey.setRoleId("83cafa4d-a03e-412a-bf1d-7cf947146ab7");

        UserRole userRole = new UserRole();
        userRole.setUserRoleKey(userRoleKey);

        userRoleRepository.save(userRole);

        userRoleKey.setRoleId("e5f82bed-46cd-4411-bf6b-0d6d182eff90");
        userRole.setUserRoleKey(userRoleKey);

        userRoleRepository.save(userRole);

    }

    @Test
    public void findTest(){
        UserRoleKey userRoleKey = new UserRoleKey();
        userRoleKey.setUserId("123");
//        userRoleKey.setRoleId("123");

        UserRole userRole = userRoleRepository.findOne(userRoleKey);
    }

    @Test
    public void findByUserId() throws Exception {
        List<UserRole> userRoleList = userRoleRepository.findByUserRoleKey_UserId("123");
        for (UserRole userRole: userRoleList) {
            log.info(userRole.getUserRoleKey().getRoleId());
        }
    }

}