package com.bcdbook.meng.system.repository;

import com.bcdbook.meng.system.enums.IResourceTypeEnum;
import com.bcdbook.meng.system.model.IResource;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

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

    /**
     * 根据id集合查询资源对象
     */
    @Test
    public void findByIResourceIdInTest(){
        List<String> ids = Arrays.asList("5c701ac3-e698-40ea-981c-9dd97597cad3","e40a06bd-3ea8-4fbd-a53e-b3da26d3bad8","1d149222-51d4-429f-8ee9-fb7722d8e53e");

        List<IResource> iResourceList = iResourceRepository.findByIdIn(ids);
        log.info("[根据id集合查询资源对象  size={}]",iResourceList.size());
        Assert.assertNotNull(iResourceList);
    }

    /**
     * 根据id集合,和资源类型查询资源对象
     */
    @Test
    public void findByIResourceTypeAndIdInTest(){
        List<String> ids = Arrays.asList("5c701ac3-e698-40ea-981c-9dd97597cad3", "e40a06bd-3ea8-4fbd-a53e-b3da26d3bad8", "1d149222-51d4-429f-8ee9-fb7722d8e53e");

        List<IResource> iResourceList = iResourceRepository.findByIResourceTypeAndIdIn(IResourceTypeEnum.MENU.getCode(),ids);

        log.info("[根据id集合查询资源对象  size={}]",iResourceList.size());
        Assert.assertNotNull(iResourceList);
    }
}