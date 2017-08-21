package com.bcdbook.meng.system.form;

import com.bcdbook.meng.common.constant.LevelConstant;
import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @Author summer
 * @Date 2017/8/19 下午5:16
 */
@Data
@ApiModel
public class IResourceForm {

    @ApiModelProperty(name = "id", value = "资源id", example = "asgeiowhoig",required = false)
    private String id;

//    @NotNull(message = "父级id不能为空不能为空")
    @ApiModelProperty(name = "parentId", value = "父级id", example = "asgeiowhoig",required = false)
    private String parentId = LevelConstant.TOP_LEVEL;//父级的id(如果不是子账户,则为TOP_LEVEL)

    @NotNull(message = "资源名不能为空")
    @NotEmpty(message = "资源名不能为空")
    @ApiModelProperty(name = "name", value = "资源名", example = "用户管理", required = true)
    private String name;//资源名称

    @ApiModelProperty(name = "description", value = "简介", example = "这是用来管理用的", required = false)
    private String description;//详细描述

    @NotNull(message = "跳转地址不能为空")
    @NotEmpty(message = "跳转地址不能为空")
    @Size(min=1,max = 128,message = "连接地址长度在1到128位之间")
    @ApiModelProperty(name = "url", value = "跳转地址", example = "/user/add", required = true)
    private String url;//连接地址(跳转到的地址)

    @ApiModelProperty(name = "icon", value = "图标", example = "edit", required = false)
    private String icon;//图标(阿里图标库的标识)

    @NotNull(message = "排序值不能为空")
    @Min(value = 0,message = "排序值不能小于0")
    @ApiModelProperty(name = "sort", value = "排序值", example = "3", required = true)
    private Integer sort = 0;//排序值

    @NotNull(message = "资源类型不能为空")
    @Min(value = 0,message = "资源类型不能小于0")
    @ApiModelProperty(name = "iResourceType", value = "资源类型", example = "2", required = true)
    private Integer iResourceType = IResourceTypeEnum.MENU.getCode();

//    @ApiModelProperty(name = "createTime", value = "创建时间", example = "12398798345", required = false)
//    private Date createTime;//创建时间
//
//    @ApiModelProperty(name = "updateTime", value = "更新时间", example = "12398798345", required = false)
//    private Date updateTime;//修改时间
}
