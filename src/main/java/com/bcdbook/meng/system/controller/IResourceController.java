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
@RequestMapping("/resource")
@Slf4j
@Api(tags = {SwaggerTagsConstant.COMMON})
public class IResourceController {

    @PostMapping("/save")
    public String save() {
        //- TODO 添加菜单/按钮
        return null;
    }

    @GetMapping("/menu/list")
    public String menuList(){
        //- TODO 查询菜单列表
        return null;
    }

    @GetMapping("/button/list")
    public String buttonList(){
        //- TODO 查询按钮列表
        return null;
    }

    @GetMapping("/listAll")
    public String listAll(){
        //- TODO 查询所有的菜单和按钮
        return null;
    }


    @DeleteMapping("/delete/{id}")
    public String delete(){
        //- TODO 删除菜单/按钮
        return null;
    }

    @PutMapping("/update")
    public String update(){
        //- TODO 更新菜单/按钮
        return null;
    }

    @PutMapping("/sort")
    public String sort(){
        //- TODO 对菜单/按钮进行排序
        return null;
    }
}
