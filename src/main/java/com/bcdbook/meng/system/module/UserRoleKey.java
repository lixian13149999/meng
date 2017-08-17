package com.bcdbook.meng.system.module;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @Author summer
 * @Date 2017/8/12 下午10:49
 */
@Data
@EqualsAndHashCode
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleKey implements Serializable {
    private String userId;
    private String roleId;

}
