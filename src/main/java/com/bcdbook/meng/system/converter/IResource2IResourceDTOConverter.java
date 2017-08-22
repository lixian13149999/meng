package com.bcdbook.meng.system.converter;

import com.bcdbook.meng.system.DTO.IResourceDTO;
import com.bcdbook.meng.system.model.IResource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by 廖师兄
 * 2017-06-18 23:41
 */
@Slf4j
public class IResource2IResourceDTOConverter {

    public static IResourceDTO convert(IResource iResource) {

        IResourceDTO iResourceDTO = new IResourceDTO();
        BeanUtils.copyProperties(iResource, iResourceDTO);
        return iResourceDTO;
    }

    public static List<IResourceDTO> convert(List<IResource> iResourceList) {
        return iResourceList.stream().map(e ->
                convert(e)
        ).collect(Collectors.toList());
    }
}
