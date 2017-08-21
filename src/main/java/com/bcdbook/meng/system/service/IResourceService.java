package com.bcdbook.meng.system.service;

import com.bcdbook.meng.system.model.IResource;

import java.util.Map;

/**
 * @Author summer
 * @Date 2017/8/19 下午6:03
 */
public interface IResourceService {

    IResource findOne(String iResourceId);

    IResource save(IResource iResource);

    IResource update(IResource iResource);

    boolean sort(Map<String,Integer> iResourceMap);
}
