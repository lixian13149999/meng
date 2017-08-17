//package com.bcdbook.meng.system.form;
//
//import com.bcdbook.meng.common.constant.LevelConstant;
//import com.bcdbook.meng.system.enums.CertificationStatusEnum;
//import com.bcdbook.meng.system.enums.IsChildEnum;
//import com.bcdbook.meng.system.enums.UserStatusEnum;
//import com.bcdbook.meng.system.enums.UserTypeEnum;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Size;
//
///**
// * @Author summer
// * @Date 2017/8/16 下午8:11
// * 后台注册用户的表单对象
// */
//@Data
//@ApiModel
//public class SignupUserForm {
//
//    @ApiModelProperty(name = "username", value = "用户名", example = "admin", required = false)
//    private String parentId  = LevelConstant.TOP_LEVEL;//父级账号的id(如果不是子账户,则为topLevel)
//
//    /*
//     * 表单校验的相关注解
//     * 1. 用户名不能为空
//     * 2. 长度需要再4到32位之间
//     */
//    @NotNull(message = "用户名不能为空")
//    @Size(min=4,max = 32,message = "用户名长度在4到32位之间")
//    @ApiModelProperty(name = "username", value = "用户名", example = "admin", required = true)
//    private String username;//用户名
//
//    @NotNull(message = "密码不能为空")
//    @Size(min=4,max = 32,message = "密码长度在4到32位之间")
//    @ApiModelProperty(name = "userPassword", value = "密码", example = "123456", required = true)
//    private String userPassword;//密码
//
//    @ApiModelProperty(name = "nickname", value = "昵称", example = "summer", required = false)
//    private String nickname;//昵称
//
//    @ApiModelProperty(name = "mobile", value = "手机号", example = "18866668888", required = false)
//    private String mobile;//手机号
//
//    @ApiModelProperty(name = "email", value = "邮箱", example = "lx@mengya360.com", required = false)
//    private String email;//邮箱
//
//    @ApiModelProperty(name = "openId", value = "微信的唯一识别标识", example = "41b42351-0488-40ac-9654-c59c287e6ab0", required = false)
//    private String openId;//微信关联的openId
//
//    @ApiModelProperty(name = "userType", value = "用户类型", example = "1", required = false)
//    private Integer userType = UserTypeEnum.ORDINARY_USER.getCode();//用户类型
//
//    private Integer userStatus = UserStatusEnum.NORMALITY.getCode();//用户状态(账号状态)
//
//    private Integer certificationStatus = CertificationStatusEnum.UNCERTIFIED.getCode();//认证状态
//
//    private boolean isChild = IsChildEnum.FALSE.getStatus();//是否是子账户
//}