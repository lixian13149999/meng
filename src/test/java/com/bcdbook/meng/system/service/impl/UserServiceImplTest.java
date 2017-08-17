package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author summer
 * @Date 2017/8/13 下午5:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void findOne() throws Exception {
    }

    @Test
    public void findUserByUsernameAndUserPassword() throws Exception {
        User user = userService.findUserByUsernameAndUserPassword("summer","123321");
        if(user!=null){
            log.info(user.toString());
        }
        log.error("测试错误log日志");
    }

}