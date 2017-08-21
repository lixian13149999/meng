package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @Author summer
 * @Date 2017/8/16 下午8:54
 */
@Controller
@RequestMapping("/role")
@Slf4j
@Api(tags = {SwaggerTagsConstant.ROLE})
public class RoleController {

    @PostMapping("/save")
    public String save() {

        //- TODO 添加角色
        return null;
    }

    @GetMapping("/list")
    public String list(){
        //- TODO 查询角色列表
        return null;
    }

    @DeleteMapping("/delete/{id}")
    public String delete(){
        //- TODO 删除角色
        return null;
    }

    @PutMapping("/update")
    public String update(){
        //- TODO 更新角色
        return null;
    }

    @PutMapping("/sort")
    public String sort(){
        //- TODO 对角色进行排序
        return null;
    }
}
