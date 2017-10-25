package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import com.bcdbook.meng.system.model.IResource;
import com.bcdbook.meng.system.service.IResourceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/9/14 上午10:49
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IResourceServiceImplTest {

    @Autowired
    private IResourceService iResourceService;

    /*
     * 测试添加方法
     */
    @Test
    public void saveTest(){
        IResource iResource = new IResource();
        iResource.setParentId("7340d2f0-8543-47f8-8af5-36b01c5150d1");
        iResource.setName("删除");
        iResource.setSort(2);
        iResource.setIResourceType(IResourceTypeEnum.BUTTON.getCode());
        iResource.setIcon("icon-user");
        iResource.setUrl("javascript:void(0)");
        iResource.setDescription("按钮--栏目删除");

        iResourceService.save(iResource);
    }

    /*
     * 根据id查询一个资源对象
     */
    @Test
    public void findOneTest(){
        IResource iResource = iResourceService.findOne("99346af5-93a0-40c5-8f71-b2a7a71a7ed5");

        Assert.assertNotNull(iResource);
    }

    /*
     * 更新资源对象
     * 1. 资源类型不能修改
     * 2. 资源的父级不能为空
     * 3. 资源的父级不能是button类型
     */
    @Test
    public void updateTest(){
        IResource iResource = new IResource();
        iResource.setId("99346af5-93a0-40c5-8f71-b2a7a71a7ed5");
        iResource.setName("系统管理");
        iResource.setSort(1);
        iResource.setIResourceType(IResourceTypeEnum.MENU.getCode());
        iResource.setIcon("icon-tool");
        iResource.setUrl("javascript:void(0)");
        iResource.setDescription("一级菜单--系统管理");

        iResourceService.update(iResource);
    }

    /*
     * 对资源对象进行排序
     * 1. 不区分菜单还是按钮
     * 2. 不区分一级还是二级
     * 3. 直接根据id和顺序值设定其
     */
    @Test
    public void sortTest(){
        Map<String,Integer> sortMap = new HashMap<>();
        sortMap.put("5a6bc043-ecea-48f6-9d2b-77b62e520baa",1);
        sortMap.put("99346af5-93a0-40c5-8f71-b2a7a71a7ed5",2);

        iResourceService.sort(sortMap);
    }

    /*
     * 查询所有的资源集合
     * 1. 做了结构化处理
     */
    @Test
    public void listAllTest(){
        List<IResourceDTO> iResourceDTOList = iResourceService.listAll();

        Assert.assertNotNull(iResourceDTOList);
    }

    /*
     * 根据资源的id集合查询对应的资源对象
     * 1. 不进行结构化处理
     */
    @Test
    public void listIResourceByIdListTest(){
        List<String> iResourceIdList = new ArrayList<>();
        iResourceIdList.add("7340d2f0-8543-47f8-8af5-36b01c5150d1");//二级,栏目管理
        iResourceIdList.add("5a6bc043-ecea-48f6-9d2b-77b62e520baa");//一级,用户管理
        iResourceIdList.add("99346af5-93a0-40c5-8f71-b2a7a71a7ed5");//一级,系统管理
        iResourceIdList.add("5d0a05a7-484a-433c-8cf1-0d58d7ce8545");//二级,角色管理


        List<IResourceDTO> iResourceDTOList =  iResourceService.listIResourceByIdList(iResourceIdList);

        Assert.assertNotNull(iResourceDTOList);
    }

    /*
     * 根据角色的id集合,查询无结构的资源集合
     */
    @Test
    public void listUnparseIResourceByRoleIdListTest(){

        //TODO-需要等Role测试完成后再测关联关系的内容

    }

    /*
     * 1. 查询所有的资源集合
     * 2. 结构化处理
     * 3. 标注传入的资源的id集合对应的资源对象为选中状态
     */
    @Test
    public void listAllAndCheckedTest(){
        List<String> iResourceIdList = new ArrayList<>();
        iResourceIdList.add("7340d2f0-8543-47f8-8af5-36b01c5150d1");//二级,栏目管理
        iResourceIdList.add("5a6bc043-ecea-48f6-9d2b-77b62e520baa");//一级,用户管理
        iResourceIdList.add("99346af5-93a0-40c5-8f71-b2a7a71a7ed5");//一级,系统管理
//        iResourceIdList.add("5d0a05a7-484a-433c-8cf1-0d58d7ce8545");//二级,角色管理

        List<IResourceDTO> iResourceDTOList = iResourceService.listAllAndChecked(iResourceIdList);

        Assert.assertNotNull(iResourceDTOList);
    }

    /*
     * 根据角色id集合,查询其关联的资源的id集合
     * 1. 去除重复
     */
    @Test
    public void listIResourceIdByRoleIdListTest(){
        //TODO- 需要调整角色基础的内容后,再进行完善
    }

    /*
     * 根据角色的id集合,查询其关联的资源对象
     * 进行结构化处理
     */
    @Test
    public void listIResourceByRoleIdListTest(){
        //TODO- 需要调整角色基础的内容后,再进行完善
    }

}