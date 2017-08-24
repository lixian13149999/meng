package com.bcdbook.meng.system.converter;

import com.bcdbook.meng.system.DTO.UserDTO;
import com.bcdbook.meng.system.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * Created by 廖师兄
 * 2017-06-18 23:41
 */
@Slf4j
public class User2UserDTOConverter {

    public static UserDTO convert(User user) {

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        return userDTO;
    }

    public static User reconvert(UserDTO userDTO){
        User user = new User();
        BeanUtils.copyProperties(userDTO,user);
        return user;
    }
}
