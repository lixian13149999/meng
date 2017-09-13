package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.common.constant.LevelConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.converter.IResource2IResourceDTOConverter;
import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import com.bcdbook.meng.system.model.IResource;
import com.bcdbook.meng.system.model.RoleIResource;
import com.bcdbook.meng.system.repository.IResourceRepository;
import com.bcdbook.meng.system.repository.RoleIResourceRepository;
import com.bcdbook.meng.system.service.IResourceService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author summer
 * @Date 2017/8/19 下午6:04
 */
@Service
@Log4j
public class IResourceServiceImpl implements IResourceService {

    @Autowired
    private IResourceRepository iResourceRepository;

    @Autowired
    private RoleIResourceRepository roleIResourceRepository;

    /**
     * @author summer
     * @date 2017/9/13 下午8:38
     * @param iResourceId
     * @return com.bcdbook.meng.system.model.IResource
     * @description 根据资源集合id查询资源对象
     */
    @Override
    public IResource findOne(String iResourceId) {

        return StringUtils.isEmpty(iResourceId)? null : iResourceRepository.findOne(iResourceId);
    }


    /**
     * @author summer
     * @date 2017/8/21 上午9:02
     * @param iResource
     * @return com.bcdbook.meng.system.model.IResource
     * @description 保存资源的方法
     */
    @Override
    @Transactional
    public IResource save(IResource iResource) {

        //验证参数的合法性
        if (iResource == null) {
            log.error("[添加资源] 传入的资源对象为空");
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }

        //获取父级id
        String parentId = iResource.getParentId();
        //当其是父级(parentId不为空,且部位顶级默认值时)
        if (!StringUtils.isEmpty(parentId) && !parentId.equals(LevelConstant.TOP_LEVEL)) {
            //查询是否有此资源
            IResource parentIResource = iResourceRepository.findOne(parentId);
            //如果没有此资源
            if (parentIResource == null) {
                log.error("[添加资源] 将要绑定的父级资源为空");
                throw new CommonException(ResultEnum.FOREIGN_DATA_IS_EMPTY);
            }

            //如果父级资源为按钮,则不允许有下级
            if (parentIResource.getIResourceType() == IResourceTypeEnum.BUTTON.getCode()) {
                log.error("[添加资源] 资源不允许绑定在button下");
                throw new CommonException(ResultEnum.FOREIGN_DATA_ERROR);
            }
        }

        return iResourceRepository.save(iResource);
    }

    /**
     * @author summer
     * @date 2017/8/21 上午9:14
     * @param iResource
     * @return com.bcdbook.meng.system.model.IResource
     * @description 修改资源的方法
     * 1. 资源类型不允许发生变化
     * 2. 资源级别发生变化时,父级资源不允许为空
     * 3. 父级资源不允许是按钮类型
     */
    @Override
    @Transactional
    public IResource update(IResource iResource) {
        //参数合法性验证
        if (iResource == null) {
            log.error("[更新资源] 传入的资源对象为空");
            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
        }

        //获取传入的资源id
        String iResourceId = iResource.getId();
        if (StringUtils.isEmpty(iResourceId)){
            log.error("[更新资源] 将要更新的资源id为空");
            throw new CommonException(ResultEnum.ID_IS_EMPTY);
        }
        /**
         * 类型发生改变时的处理
         * 不允许类型发生改变
         */
        IResource sourceIResource = iResourceRepository.findOne(iResourceId);
        //如果资源类型不相同,则不能执行修改
        if(iResource.getIResourceType()!=sourceIResource.getIResourceType()){
            log.error("[更新资源] 资源类型不允许修改");
            throw new CommonException(ResultEnum.PARAM_ERROR);
        }

        /**
         * 父级发生改变时的处理
         */
//        //如果父级资源为空,则抛出异常
//        if(StringUtils.isEmpty(iResource.getParentId())
//                ||StringUtils.isEmpty(sourceIResource.getParentId())){
//            throw new CommonException(ResultEnum.PARAM_IS_EMPTY);
//        }

        //如果父级资源发生改变
        if(!iResource.getParentId().equals(sourceIResource.getParentId())){
            IResource newParent = iResourceRepository.findOne(iResource.getParentId());
            //如果新的父级对象为空,
            if(newParent==null){
                log.error("[更新资源] 将要绑定的父级资源为空");
                throw new CommonException(ResultEnum.FOREIGN_DATA_IS_EMPTY);
            }
            //如果父级是按钮
            //不允许父级是按钮
            if(newParent.getIResourceType()==IResourceTypeEnum.BUTTON.getCode()){
                log.error("[更新资源] 父级资源不允许是按钮");
                throw new CommonException(ResultEnum.FOREIGN_DATA_ERROR);
            }
        }

        iResource.setCreateTime(sourceIResource.getCreateTime());
        //BeanUtils.copyProperties(iResource,sourceIResource);

        //执行更新操作,并保持更新后的值
        return iResourceRepository.save(iResource);
    }

    /**
     * @author summer
     * @date 2017/8/21 下午3:14
     * @param iResourceMap
     * @return boolean
     * @description 对资源进行排序
     */
    @Override
    @Transactional
    public boolean sort(Map<String, Integer> iResourceMap) {
        //参数合法性校验, 如果传入的对象为空,则直接返回true
        if(iResourceMap==null){
            return true;
        }

        //循环传入的map集合,获取对应的值和顺序值
        for (String iResourcId: iResourceMap.keySet()) {
            if(StringUtils.isEmpty(iResourcId)){
                log.error("[排序资源] 资源id为空,无法执行排序操作");
                throw new CommonException(ResultEnum.PARAM_ERROR);
            }
            //根据资源id,获取资源对象
            IResource sourceIResource = iResourceRepository.findOne(iResourcId);
            //如果获取的对象为空,则跳过本次循环
            if(sourceIResource==null){
                log.error("[排序资源] 需要排序的资源对象为空");
                throw new CommonException(ResultEnum.FOREIGN_DATA_IS_EMPTY);
            }
            //设置最新的顺序值
            sourceIResource.setSort(iResourceMap.get(iResourcId));
            //保存排序
            iResourceRepository.save(sourceIResource);
        }

        return true;
    }

    /**
     * @author summer
     * @date 2017/8/21 下午8:20
     * @param
     * @return java.util.List<com.bcdbook.meng.system.model.IResource>
     * @description 查询所有的资源集合,做结构化处理
     */
    @Override
    public List<IResourceDTO> listAll() {
        //1.查询出所有的资源对象
        List<IResource> sourceIResourceList = iResourceRepository.findAll();
        if(sourceIResourceList==null||sourceIResourceList.size()<=0){
            return null;
        }

        //创建IResourceDTO集合, 用于copy源数据
        List<IResourceDTO> iResourceDTOList = IResource2IResourceDTOConverter.convert(sourceIResourceList);
//        for (IResource iresource:sourceIResourceList) {
//            IResourceDTO iResourceDTO = new IResourceDTO();
//            BeanUtils.copyProperties(iresource,iResourceDTO);
//            iResourceDTOList.add(iResourceDTO);
//        }

        //对资源对象进行封装,以获取有结构的资源集合
        List<IResourceDTO> topLevelMenus = parseList(iResourceDTOList);

        return topLevelMenus;
    }

    /**
     * @author summer
     * @date 2017/8/22 下午1:45
     * @param iResourceIdList
     * @return java.util.List<com.bcdbook.meng.system.DTO.IResourceDTO>
     * @description 根据资源的id集合,获取相关资源对象(菜单),未序列化
     */
    @Override
    public List<IResourceDTO> listIResourceByIdList(List<String> iResourceIdList) {
        if(iResourceIdList==null||iResourceIdList.size()<=0){
            return null;
        }
        //查询菜单类型的资源集合
        List<IResource> iResourceList = iResourceRepository.findByIResourceTypeAndIdIn(IResourceTypeEnum.MENU.getCode(),iResourceIdList);
        if(iResourceList==null||iResourceList.size()<=0){
            return null;
        }

        List<IResourceDTO> iResourceDTOList = IResource2IResourceDTOConverter.convert(iResourceList);

        return parseList(iResourceDTOList);
    }

    /**
     * @author summer
     * @date 2017/8/22 下午2:23
     * @param iResourceIdList 为已经拥有的资源id集合
     * @return java.util.List<com.bcdbook.meng.system.DTO.IResourceDTO>
     * @description 查询出所有的资源对象,并标注出传入id集合的资源对象
     */
    @Override
    public List<IResourceDTO> listAllAndChecked(List<String> iResourceIdList) {
        //参数合法性验证
        if(iResourceIdList==null||iResourceIdList.size()<=0){
            return null;
        }
        //获取所有的资源对象
        List<IResource> allIResourceList = iResourceRepository.findAll();
        //获取在当前数据集合中的资源对象
        List<IResource> iResourceList = iResourceRepository.findByIdIn(iResourceIdList);
        if(allIResourceList==null||allIResourceList.size()<=0){
            return null;
        }

        //集合数据类型转换
        List<IResourceDTO> allIResourceDTOList = IResource2IResourceDTOConverter.convert(allIResourceList);
        List<IResourceDTO> iResourceDTOList = IResource2IResourceDTOConverter.convert(iResourceList);

        //执行标注
        for (IResourceDTO fullIResource:allIResourceDTOList) {
            for (IResourceDTO possessIResource : iResourceDTOList) {
                if(fullIResource.getId().equals(possessIResource.getId())){
                    fullIResource.setPossess(true);
                }
            }
        }

        //执行父子级序列化封装
        List<IResourceDTO> parseList = parseList(allIResourceDTOList);

        return parseList;
    }

    /**
     * @author summer
     * @date 2017/9/13 下午8:43
     * @param rolesIdList
     * @return java.util.List<java.lang.String>
     * @description 根据角色的id集合,查询出其含有的资源的id集合,并去重
     */
    @Override
    public List<String> listIResourceIdByRoleIdList(List<String> rolesIdList) {
        //参数合法性校验
        if(rolesIdList==null||rolesIdList.size()<=0){
            return null;
        }
        //根据角色的id集合,查询出对应的资源的id集合
        List<RoleIResource> roleIResourceList = roleIResourceRepository.findByRoleIResourceKey_RoleIdIn(rolesIdList);
        List<String> iResourceIdList = roleIResourceList
                .stream()
                .map(e -> e.getRoleIResourceKey().getIResourceId())
                .collect(Collectors.toList());

        //去重
        List<String> outIResourceIdList = new ArrayList<String>(new HashSet<String>(iResourceIdList));

        return outIResourceIdList;
    }

    /**
     * @author summer
     * @date 2017/9/13 下午9:04
     * @param roleIdList
     * @return java.util.List<com.bcdbook.meng.system.DTO.IResourceDTO>
     * @description 根据角色的id集合,查询其对应的资源集合,并进行序列化封装
     */
    @Override
    public List<IResourceDTO> listIResourceByRoleIdList(List<String> roleIdList) {
        //参数合法性校验
        if(roleIdList==null||roleIdList.size()<=0){
            return null;
        }

        //获取资源的id集合
        List<String> iresourceIdList = listIResourceIdByRoleIdList(roleIdList);

        //根据资源id集合,查询对应的资源对象
        List<IResourceDTO> iResourceDTOList = (iresourceIdList==null||iresourceIdList.size()<=0)
                ? null
                : listIResourceByIdList(iresourceIdList);

        //对查询出的资源对象进行序列化处理
        List<IResourceDTO> iResourceDTOListParsed = (iResourceDTOList==null||iResourceDTOList.size()<=0)
                ? null
                : parseList(iResourceDTOList);

        return iResourceDTOListParsed;
    }

    /**
     * @author summer
     * @date 2017/8/22 上午10:13
     * @param sourceIResourceDTOList
     * @return java.util.List<com.bcdbook.meng.system.DTO.IResourceDTO>
     * @description
     */
    private List<IResourceDTO> parseList(List<IResourceDTO> sourceIResourceDTOList) {
        
        if(sourceIResourceDTOList==null||sourceIResourceDTOList.size()==0){
            return null;
        }

        //对所有的资源对象进行排序
        List<IResourceDTO> allIResources = sourceIResourceDTOList
                .stream()
                .sorted((r1, r2) -> r1.getSort().compareTo(r2.getSort()))
                .collect(Collectors.toList());

        //2.对资源对象分类封装,并转换数据类型
        List<IResourceDTO> topLevelMenus = new ArrayList<>();
        List<IResourceDTO> secondLeveMenus = new ArrayList<>();
        List<IResourceDTO> buttons = new ArrayList<>();

        /**
         * 循环所有的资源对象
         * 对资源进行分类
         */
        for (IResourceDTO iResourceDTO : allIResources) {
            //如果是菜单
            if (IResourceTypeEnum.MENU.getCode() == iResourceDTO.getIResourceType()) {
                //如果是一级
                if (LevelConstant.TOP_LEVEL.equals(iResourceDTO.getParentId())) {
                    topLevelMenus.add(iResourceDTO);//添加资源到一级菜单集合
                } else {
                    secondLeveMenus.add(iResourceDTO);//添加资源到二级菜单集合
                }
            } else if (IResourceTypeEnum.BUTTON.getCode() == iResourceDTO.getIResourceType()) {
                buttons.add(iResourceDTO);//添加资源到button集合
            }
        }

        //如果没有二级菜单,
        /**
         * 添加二级资源的button
         */
        for (IResourceDTO secondMenu : secondLeveMenus) {
            List<IResourceDTO> secondButtons = new ArrayList<>();
            //如果buttons为空,则直接忽略
            for (IResourceDTO button : buttons) {
                //如果此button是二级菜单的附属按钮
                if (button.getParentId().equals(secondMenu.getId())) {
                    secondButtons.add(button);
                }
            }
            secondMenu.setButtons(secondButtons);
        }


        /**
         * 添加一级资源的子资源
         */
        for (IResourceDTO topMenu : topLevelMenus) {
            List<IResourceDTO> secondMenus = new ArrayList<>();
            for (IResourceDTO secondMenu : secondLeveMenus) {
                //如果此button是二级菜单的附属按钮
                if (secondMenu.getParentId().equals(topMenu.getId())) {
                    secondMenus.add(secondMenu);
                }
            }
            topMenu.setMenus(secondMenus);
        }

        return topLevelMenus;
    }

}
