package com.bcdbook.meng.system.DTO;

import lombok.Data;

import java.util.List;


/**
 * @Author summer
 * @Date 2017/8/21 下午8:22
 */
@Data
public class IResourceDTO {

    private String id;//资源id

    private String parentId;//父级的id(如果不是子账户,则为TOP_LEVEL)

    private String name;//资源名称

    private String description;//详细描述

    private String url;//连接地址(跳转到的地址)

    private String icon;//图标(阿里图标库的标识)

    private Integer sort = 0;//排序值

    private Integer iResourceType;//资源类型

    private boolean active;//是否是激活状态(对于一级菜单则是是否展开)

    private boolean possess;//是否拥有此权限

    List<IResourceDTO> menus;//子栏目集合

    List<IResourceDTO> buttons;//子按钮集合

}
