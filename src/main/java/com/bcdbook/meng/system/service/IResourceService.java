package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.model.IResource;

import java.util.List;
import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/19 下午6:03
 */
public interface IResourceService {

    /**
     * 根据资源id查询单个资源对象
     * @param iResourceId
     * @return
     */
    IResource findOne(String iResourceId);

    /**
     * 保存资源对象
     * @param iResource
     * @return
     */
    IResource save(IResource iResource);

    /**
     * 更新资源对象
     * @param iResource
     * @return
     */
    IResource update(IResource iResource);

    /**
     * 对资源对象进行排序
     * @param iResourceMap
     * @return
     */
    boolean sort(Map<String,Integer> iResourceMap);

    /**
     * 查询所有的资源对象,并进行父子集序列化处理
     * @return
     */
    List<IResourceDTO> listAll();

    /**
     * 根据资源的id集合,查询相关资源,不进行父子集序列化处理
     * @param iResourceIdList
     * @return
     */
    List<IResourceDTO> listIResourceByIdList(List<String> iResourceIdList);

    /**
     * 查询所有的资源集合,并根据传入的资源的id集合,标注其为选中状态
     * 同时进行父子集序列化封装
     * @param iResourceIdList
     * @return
     */
    List<IResourceDTO> listAllAndChecked(List<String> iResourceIdList);

    /**
     * 根据角色的id集合,查询所有的资源的id集合
     * @param roleList
     * @return
     */
    List<String> listIResourceIdByRoleIdList(List<String> roleList);

    /**
     * 根据角色的id集合,查询其所拥有的资源,并进行序列化封装
     * @param roleList
     * @return
     */
    List<IResourceDTO> listIResourceByRoleIdList(List<String> roleIdList);
}
