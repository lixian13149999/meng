package com.bcdbook.meng.system.DTO;

import com.bcdbook.meng.common.util.serializer.Date2LongSerializer;
import com.bcdbook.meng.system.enums.CertificationStatusEnum;
import com.bcdbook.meng.system.enums.UserStatusEnum;
import com.bcdbook.meng.system.enums.UserTypeEnum;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.util.Date;

/**
 * @Author summer
 * @Date 2017/8/14 上午9:56
 */
@Data
public class UserDTO {

    private String id;//用户id

    private String username;//用户名

//    private String userPassword;//密码

    private String nickname;//昵称

    private String mobile;//手机号

    private String email;//邮箱

//    private String openId;//微信关联的openId


    private Integer userType = UserTypeEnum.ORDINARY_USER.getCode();//用户类型

    private Integer userStatus = UserStatusEnum.NORMALITY.getCode();//用户状态(账号状态)

    private Integer certificationStatus = CertificationStatusEnum.UNCERTIFIED.getCode();//认证状态


    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;//创建时间

    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;//修改时间


}
