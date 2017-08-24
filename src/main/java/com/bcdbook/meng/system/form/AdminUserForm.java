package com.bcdbook.meng.system.form;

import com.bcdbook.meng.system.enums.CertificationStatusEnum;
import com.bcdbook.meng.system.enums.UserStatusEnum;
import com.bcdbook.meng.system.enums.UserTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author summer
 * @Date 2017/8/23 下午6:51
 */
@Data
@ApiModel
public class AdminUserForm {
    @NotNull(message = "用户id不能为空")
    @NotEmpty(message = "用户id不能为空")
    @ApiModelProperty(name = "id", value = "用户id", example = "huiberjasgag", required = true)
    private String id;//用户id

    @NotNull(message = "用户名不能为空")
    @NotEmpty(message = "用户名不能为空")
    @Size(min=4,max = 32,message = "用户名长度在1~32位之间")
    @ApiModelProperty(name = "username", value = "用户名", example = "summer", required = true)
    private String username;//用户名

//    @NotNull(message = "密码不能为空")
//    @NotEmpty(message = "密码不能为空")
//    @Size(min=4,max = 32,message = "密码在4~32位之间")
//    @ApiModelProperty(name = "userPassword", value = "密码", example = "123123", required = true)
//    private String userPassword;//密码

    @ApiModelProperty(name = "nickname", value = "昵称", example = "summer", required = false)
    private String nickname;//昵称

    @ApiModelProperty(name = "mobile", value = "手机号", example = "18866668888", required = false)
    private String mobile;//手机号

    @ApiModelProperty(name = "email", value = "邮箱", example = "lx@mengya360.com", required = false)
    private String email;//邮箱

//    private String openId;//微信关联的openId

    @ApiModelProperty(name = "userType", value = "用户类型", example = "1", required = false)
    private Integer userType = UserTypeEnum.ORDINARY_USER.getCode();//用户类型

    @NotNull(message = "用户状态不能为空")
    @Min(value = 0,message = "用户状态不正确")
    @ApiModelProperty(name = "userStatus", value = "用户状态", example = "1", required = false)
    private Integer userStatus = UserStatusEnum.NORMALITY.getCode();//用户状态(账号状态)

    @ApiModelProperty(name = "certificationStatus", value = "认证状态", example = "1", required = false)
    private Integer certificationStatus = CertificationStatusEnum.UNCERTIFIED.getCode();//认证状态

//    private Date createTime;//创建时间
//
//    private Date updateTime;//修改时间
}
