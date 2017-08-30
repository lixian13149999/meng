package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void saveTest(){
        User user = new User();
        user.setUsername("summer2");
        user.setUserPassword("123321");
//        user.setCertificationStatus(1);
//        user.setUserStatus(2);
        User result = userRepository.save(user);
        Assert.assertNotNull(result);

        User user2 = new User();
        user2.setUsername("summer3");
        user2.setUserPassword("123321");
//        user.setCertificationStatus(1);
//        user.setUserStatus(2);
        User result2 = userRepository.save(user2);
        Assert.assertNotNull(result);


        log.info("[测试-添加用户]");
    }

    @Test
    public void deleteTest(){
        User user = userRepository.findByUsername("summer");

        userRepository.delete(user);
        log.info("[测试-删除用户]");
    }

    @Test
    public void updateTest(){
//        User user = new User();
//        user.setId("92077e8b-5cf7-4f10-a762-61b691724583");
//        user.setUsername("summer");
//        user.setUserPassword("123321");
//        userRepository.save(user);
        User user = userRepository.findOne("92077e8b-5cf7-4f10-a762-61b691724583");
        user.setUsername("summer");
        userRepository.save(user);
    }

    @Test
    public void findTest(){
        User user = userRepository.findOne("92077e8b-5cf7-4f10-a762-61b691724583");
        log.info(user.toString());
    }

    @Test
    public void findUserByUsernameAndUserPasswordTest(){
        User user = userRepository.findByUsernameAndUserPassword("summer","123321");
        log.info(user.toString());
    }

    @Test
    public void findAllByUserTypeTest(){
        Sort sort = new Sort(Sort.Direction.DESC,"updateTime");
        PageRequest pageRequest = new PageRequest(0,2,sort);

//        Page<User> userPage = userRepository.findByUserTypeOrderByUpdateTimeDesc(1,pageRequest);
        Page<User> userPage = userRepository.findByUserType(1,pageRequest);
        Long total = userPage.getTotalElements();
        Assert.assertNotNull(userPage);
    }

}