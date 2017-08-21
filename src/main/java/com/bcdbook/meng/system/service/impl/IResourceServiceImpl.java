package com.bcdbook.meng.system.service.impl;

import com.bcdbook.meng.common.constant.LevelConstant;
import com.bcdbook.meng.common.enums.ResultEnum;
import com.bcdbook.meng.common.exception.CommonException;
import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import com.bcdbook.meng.system.model.IResource;
import com.bcdbook.meng.system.repository.IResourceRepository;
import com.bcdbook.meng.system.service.IResourceService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/19 下午6:04
 */
@Service
@Log4j
public class IResourceServiceImpl implements IResourceService {

    @Autowired
    private IResourceRepository iResourceRepository;

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
}
