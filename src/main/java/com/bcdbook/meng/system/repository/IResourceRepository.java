package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.IResource;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
public interface IResourceRepository extends JpaRepository<IResource,String> {
    //根据资源的id集合获取资源集合
    List<IResource> findByIdIn(List<String> iResourceIdList);

    List<IResource> findByIResourceTypeAndIdIn(Integer iResourceType,List<String> iResourceIdList);
}
