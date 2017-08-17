package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import com.bcdbook.meng.system.model.IResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author summer
 * @Date 2017/8/13 下午3:37
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class IResourceRepositoryTest {
    @Autowired
    private IResourceRepository iResourceRepository;

    @Test
    public void saveTest(){
        IResource iResource = new IResource();

        iResource.setName("资源名称");

        iResourceRepository.save(iResource);

        IResource iResource1 = new IResource();

        iResource1.setName("按钮");
        iResource1.setIResourceType(IResourceTypeEnum.BUTTON.getCode());

        iResourceRepository.save(iResource1);
    }

    @Test
    public void findTest(){
        IResource iResource = new IResource();
        iResource.setIResourceType(IResourceTypeEnum.BUTTON.getCode());

//        List<IResource> iResourceList = iResourceRepository.findOne(iResource);
    }
}