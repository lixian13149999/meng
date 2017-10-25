package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.common.util.MD5Util;
import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.enums.UserTypeEnum;
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
 * @Date 2017/8/13 下午5:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class UserServiceImplTest {
    @Autowired
    private UserServiceImpl userService;

    /**
     * 添加用户的方法
     */
    @Test
    public void saveTest(){
        String username = "summer1234";
        String password = "123123";

        User user = new User();
        user.setUsername(username);
        user.setUserPassword(MD5Util.getMD5Code(password));
        user.setNickname(username);

        UserDTO result = userService.save(user);

        Assert.assertNotNull(result);
//        log.info(result.toString());
    }

//    @Test
//    public void saveDTOTest(){
//        String username = "summerDTO";
//
//        UserDTO userDTO = new UserDTO();
//        userDTO.setUsername(username);
//        userDTO.setNickname(username);
//
//        userService.save(userDTO);
//    }

    /**
     * 根据id查用户对象
     * @throws Exception
     */
    @Test
    public void findOneTest() throws Exception {
        User user = userService.findOne("2c924b6a-03b7-4292-95d8-74134c430294");

        Assert.assertNotNull(user);
    }

    /**
     * 根据用户名查询用户对象
     */
    @Test
    public void findByUsernameTest(){
        String username = "summer23";

        User user = userService.findByUsername(username);

        Assert.assertNotNull(user);
    }

    /**
     * 根据用户名和密码查询用户对象
     * @throws Exception
     */
    @Test
    public void findUserByUsernameAndUserPassword() throws Exception {
        String username = "summer23";
        String password = "123123";

        String MD5Password = MD5Util.getMD5Code(password);
        User user = userService.findByUsernameAndUserPassword(username,MD5Password);

        if(user!=null){
            log.info(user.toString());
        }
//        log.error("[查询用户] 根据用户名和密码查询用户对象");
        Assert.assertNotNull(user);
    }

    /**
     * 根据手机号查询用户对象
     */
    @Test
    public void findByMobileTest(){
        User user = userService.findByMobile("18866668888");

        Assert.assertNotNull(user);
        log.info("[查询用户] 根据用户的手机号查询用户对象");
    }

    /**
     * 根据用户email查询用户对象
     */
    @Test
    public void findByEmailTest(){
        User user = userService.findByEmail("xianforwork@163.com");

        Assert.assertNotNull(user);
    }

    /**
     * 根据角色id查询用户对象集合
     */
    @Test
    public void listUserIdByRoleIdTest(){
        //-TODO 稍后整理此测试类
    }

    /**
     * 根据用户类型查询用户集合
     */
    @Test
    public void listUserByUserTypeAndKeyTest(){
        //Integer userType, String parameter, Pageable pageable
        PageRequest pageRequest = new PageRequest(0, 2,new Sort(Sort.Direction.DESC,"updateTime"));

        Page<UserDTO> userDTOPage = userService.listUserByUserTypeAndKey(UserTypeEnum.ORDINARY_USER.getCode(),"summer",pageRequest);

        Assert.assertNotNull(userDTOPage);

    }

    @Test
    public void putRoleTest(){
        String userId = "c79ad348-3dca-43c5-a345-e21841ed5b40";
        String roleId = "fd618570-8778-4b1c-809b-826eba55ace3";

        boolean result = userService.putRole(userId,roleId);

        Assert.assertEquals("[用户添加角色] 添加角色结果",true,result);
    }

}