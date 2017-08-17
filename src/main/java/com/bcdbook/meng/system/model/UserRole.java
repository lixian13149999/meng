package com.bcdbook.meng.system.model;

import com.bcdbook.meng.system.module.UserRoleKey;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author summer
 * @Date 2017/8/12 下午10:07
 * 用户/角色中间表对应的实体类
 * 由于本项目中的关联关系均使用伪关联(不建立真正的关联关系)
 * 所以,中间表需要有对应的实体类来映射
 */
/*
 * 表明这是一个实体类
 */
@Entity
/*
 * 映射到数据库表的相关注解信息,
 * 此处仅指定了映射到数据库的哪张表
 */
@DynamicUpdate
@Table(name = "sys_user_role")
/*
 * lombok框架中的注解
 * 加入此注解相当于加入了Getter,Setter,ToString
 * 此外还有lombok框架还有如下注解
 * @Getter,@Setter,@ToString,@EqualsAndHashCode,@Slf4j等
 */
@Data
/*
 * 空参构造
 */
@NoArgsConstructor
/*
 * 全参构造
 */
@AllArgsConstructor
public class UserRole implements Serializable {

    @Id
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name="user_id", columnDefinition = "varchar(64) COMMENT '用户id'")),
            @AttributeOverride(name = "roleId", column = @Column(name="role_id", columnDefinition = "varchar(64) COMMENT '角色id'"))
    })
    private UserRoleKey userRoleKey;
}
