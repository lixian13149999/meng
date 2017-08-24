package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.service.CommonRedisService;
import com.bcdbook.meng.common.util.ResultUtil;
import com.bcdbook.meng.common.util.SortFormUtil;
import com.bcdbook.meng.system.model.Role;
import com.bcdbook.meng.system.service.RoleService;
import com.bcdbook.meng.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/16 下午8:54
 */
@Controller
@RequestMapping("/role")
@Slf4j
@Api(tags = {SwaggerTagsConstant.ROLE})
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommonRedisService commonRedisService;

    @PostMapping("/save")
    @ResponseBody
    @ApiOperation(value = "添加/修改角色", notes = "添加/修改角色的方法")
    public Result save(@Valid Role role, BindingResult bindingResult) {
        /**
         * 参数合法性校验
         */
        if(bindingResult.hasErrors()){
            log.error("[添加角色] 参数不正确, role = {}, errorMessage = {}"
                    ,role
                    ,bindingResult.getFieldError().getDefaultMessage());

            throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }
        Role dbRole = null;
        if(!StringUtils.isEmpty(role.getId())){
            dbRole = roleService.findOne(role.getId());
            role.setCreateTime(dbRole.getCreateTime());
        }

        Role result = roleService.save(role);

        return ResultUtil.success(result);
    }

    @GetMapping("/list")
    @ResponseBody
    @ApiOperation(value = "查询角色集合", notes = "查询所有角色")
    public Result list(){
        List<Role> roleList = roleService.findAllOrderBySort();

        return ResultUtil.success(roleList);
    }

    @DeleteMapping("/delete/{roleId}")
    @ResponseBody
    @ApiOperation(value = "删除角色", notes = "删除角色的方法")
    public Result delete(@PathVariable String roleId){
        if(StringUtils.isEmpty(roleId)){
            return ResultUtil.error(ResultEnum.PARAM_IS_EMPTY);
        }
        List<String> userIdList = userService.listUserIdByRoleId(roleId);
        if(userIdList!=null
                &&!userIdList.isEmpty()){
            return ResultUtil.error(ResultEnum.DATA_IS_USED.getCode(),"当前角色正被使用");
        }
        roleService.delete(roleId);

        return ResultUtil.success();
    }

    @PostMapping("/sort")
    @ResponseBody
    @ApiOperation(value = "排序", notes = "对角色进行排序")
    @ApiImplicitParam(name = "sortForm", value = "角色排序值,结构为[{id:sort},{id:sort}],不需要key值" ,required = true,example = "[{'2f640b82-239b-45e7-9999-65c63ab45e51':3},{'1a16f35a-4c9e-457a-9761-539ef2acbaad':1}]")
    public Result sort(@RequestParam("sortForm") String sortForm){
        /**
         * 获取id和顺序值的字符串
         * 如果传入的排序值为空,则直接返回成功
         */
        if(StringUtils.isEmpty(sortForm)){
            return ResultUtil.success();
        }

        //解析传入的排序字符串,并封装成map集合
        Map<String,Integer> sortFormMaps = SortFormUtil.parseSortForm(sortForm);

        //执行排序操作
        boolean result = roleService.sort(sortFormMaps);

        return result ? ResultUtil.success():ResultUtil.error(ResultEnum.ERROR);
    }
}
