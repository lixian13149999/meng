package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.model.IResource;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author summer
 * @Date 2017/8/12 下午1:21
 */
public interface IResourceRepository extends JpaRepository<IResource,String> {

}
