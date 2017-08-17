package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import com.bcdbook.meng.system.model.User;
import com.bcdbook.meng.system.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author summer
 * @Date 2017/8/13 上午9:57
 * 用户的Controller
 */
@Controller
@RequestMapping("/user")
@Slf4j
@Api(tags = {SwaggerTagsConstant.USER})
public class UserController {

    @Autowired
    public UserService userService;

    @ResponseBody
    @PostMapping("/save")
    public String save(@RequestParam(value = "categoryId", required = true) String username){
        //- TODO 后台添加用户
        return null;
    }

    @GetMapping("/list")
    public String list(){
        //- TODO 查询用户列表
        return null;
    }

    @GetMapping("/get/{userId}")
    public User getUser(@PathVariable String userId){
        //- TODO 根据id查询用户的详细信息
        return null;
    }

    @PutMapping("/update")
    public String update() {
        //- TODO 更新用户资料
        return null;
    }

    @PostMapping("/changePassword")
    public String changePassword(){
        //- TODO 修改密码
        return null;
    }

    @PostMapping("/resetPassword")
    public String resetPassword() {
        //- TODO 重置密码
        return null;
    }


    @GetMapping
    public String test(){
        return "success";
    }
}
