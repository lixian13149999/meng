package com.bcdbook.meng.system.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author summer
 * @Date 2017/8/14 上午9:57
 * 用于登录的用户表单对象
 */
@Data
@ApiModel
public class LoginUserForm {

    /*
     * 表单校验的相关注解
     * 1. 用户名不能为空
     * 2. 长度需要再4到32位之间
     */
    @NotNull(message = "用户名不能为空")
    @Size(min=4,max = 32,message = "用户名长度在4到32位之间")
    @ApiModelProperty(name = "username", value = "用户名", example = "admin", required = true)
    private String username;//用户名

    @NotNull(message = "密码不能为空")
    @Size(min=4,max = 32,message = "密码长度在4到32位之间")
    @ApiModelProperty(name = "password", value = "密码", example = "123456", required = true)
    private String password;//密码

    @ApiModelProperty(name = "keepOnline", value = "是否保持登录", example = "false", required = false)
    private Boolean keepOnline;//是否保持登录

}
