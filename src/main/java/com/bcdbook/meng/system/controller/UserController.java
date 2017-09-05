package com.bcdbook.meng.system.controller;

import com.bcdbook.meng.common.constant.SwaggerTagsConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.common.result.Result;
import com.bcdbook.meng.common.util.MD5Util;
import com.bcdbook.meng.common.util.ResultUtil;
import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.converter.User2UserDTOConverter;
import com.bcdbook.meng.system.enums.CertificationStatusEnum;
import com.bcdbook.meng.system.enums.UserStatusEnum;
import com.bcdbook.meng.system.enums.UserTypeEnum;
import com.bcdbook.meng.system.form.AdminUserForm;
import com.bcdbook.meng.system.model.User;
import com.bcdbook.meng.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PostMapping("/add")
    @ResponseBody
    @ApiOperation(value = "注册(用户名+密码)"
            , notes = "管理员添加管理员的方法")
    public Result add(@RequestParam String username, @RequestParam String password, @RequestParam Integer userType) {
        if(StringUtils.isEmpty(username)
                ||StringUtils.isEmpty(password)){
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY.getCode(),"用户名或密码不能为空");
        }
        if(userType==0){
            userType = UserTypeEnum.ADMIN.getCode();
        }

        User user = new User();
        user.setUsername(username);
        user.setUserPassword(MD5Util.getMD5Code(password));
        user.setNickname(username);
        user.setUserType(userType);

        UserDTO result = userService.save(user);

        return ResultUtil.success(result);
    }

    @PostMapping("/edit/admin")
    @ResponseBody
    @ApiOperation(value = "修改用户信息"
            , notes = "修改用户信息的方法,只提供给管理员")
    public Result edit(@Valid AdminUserForm adminUserForm, BindingResult bindingResult){
        /**
         * 参数合法性校验
         */
        if(bindingResult.hasErrors()){
            log.error("[用户注册] 添加用户参数不正确, adminUserForm = {}, errorMessage = {}"
                    ,adminUserForm
                    ,bindingResult.getFieldError().getDefaultMessage());

            throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),bindingResult.getFieldError().getDefaultMessage());
        }

        //查询数据库中已有的用户对象
        User dbUser = userService.findOne(adminUserForm.getId());
        if(dbUser==null){
            throw new CommonException(ResultEnum.FOREIGN_DATA_IS_EMPTY);
        }

        /**
         * 验证唯一性参数
         */
        if(!StringUtils.isEmpty(adminUserForm.getUsername())
                &&!adminUserForm.getUsername().equals(dbUser.getUsername())){
            /*
             * 检查用户名是否被占用
             */
            User newDBUser =  userService.findByUsername(adminUserForm.getUsername());
            if(newDBUser!=null){
                return ResultUtil.error(ResultEnum.DATA_IS_EXIST.getCode(),"用户名已经被使用");
            }
        }
        if(!StringUtils.isEmpty(adminUserForm.getMobile())
                &&!adminUserForm.getMobile().equals(dbUser.getMobile())){
            User newDBUser = userService.findByMobile(adminUserForm.getMobile());
            if(newDBUser!=null){
                return ResultUtil.error(ResultEnum.DATA_IS_EXIST.getCode(),"手机号已经被使用");
            }
        }
        if(!StringUtils.isEmpty(adminUserForm.getEmail())
                &&!adminUserForm.getEmail().equals(dbUser.getEmail())){
            User newDBUser = userService.findByEmail(adminUserForm.getEmail());
            if(newDBUser!=null){
                return ResultUtil.error(ResultEnum.DATA_IS_EXIST.getCode(),"邮箱已经被使用");
            }
        }
        /*
         * 用户状态检测
         */
        if(0==adminUserForm.getUserType()){
            adminUserForm.setUserType(UserTypeEnum.ORDINARY_USER.getCode());
        }
        if(0==adminUserForm.getUserStatus()){
            adminUserForm.setUserStatus(UserStatusEnum.NORMALITY.getCode());
        }
        if(0==adminUserForm.getCertificationStatus()){
            adminUserForm.setCertificationStatus(CertificationStatusEnum.UNCERTIFIED.getCode());
        }

        BeanUtils.copyProperties(adminUserForm,dbUser);

        UserDTO result = userService.save(dbUser);

        return ResultUtil.success(result);

    }

    /**
     * @author summer
     * @date 2017/8/24 下午8:08
     * @param userType
     * @param page
     * @param size
     * @return com.bcdbook.meng.common.result.Result
     * @description
     */
    @GetMapping("/list/{userType}")
    @ResponseBody
    @ApiOperation(value = "查询用户列表"
            , notes = "根据传入的用户类型,页码及每页的数据量,查询用户的page集合")
    public Result list(@PathVariable Integer userType,
                       @RequestParam(value = "page", defaultValue = "1") Integer page,
                       @RequestParam(value = "size", defaultValue = "10") Integer size){

        if(0==userType){
            throw new CommonException(ResultEnum.PARAM_ERROR);
        }
        PageRequest pageRequest = new PageRequest(page - 1, size,new Sort(Sort.Direction.DESC,"updateTime"));
        Page<UserDTO> userDTOList = userService.listUserByUserType(userType,pageRequest);

        return ResultUtil.success(userDTOList);
    }

    @GetMapping("/get/{userId}")
    @ResponseBody
    @ApiOperation(value = "查询单个用户"
            , notes = "根据用户id,查询单个用户")
    public UserDTO getUser(@PathVariable String userId){
        if(StringUtils.isEmpty(userId)){
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }
        return User2UserDTOConverter.convert(userService.findOne(userId));
    }

    @PostMapping("/changePassword")
    public String changePassword(){
        //- TODO 修改密码
        return null;
    }

    @PostMapping("/resetPassword")
    @ResponseBody
    @ApiOperation(value = "重置密码"
            , notes = "根据用户id,重置用户的密码")
    public String resetPassword(@PathVariable String userId) {
        //- TODO 重置密码
        return null;
    }


    @GetMapping
    public String test(){
        return "/system/user-list";
    }
}
