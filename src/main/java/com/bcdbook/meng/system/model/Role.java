package com.bcdbook.meng.system.model;

import com.bcdbook.meng.common.util.serializer.Date2LongSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @Author summer
 * @Date 2017/8/12 下午6:26
 * 角色实体类
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
@Table(name = "sys_role")
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
public class Role implements Serializable {
    /*
     * @Id注释指定表的主键，它可以有多种生成方式
	 */
    @Id
    /*
	 * id 的生成方式
	 */
    @GenericGenerator(name = "PKUUID", strategy = "uuid2")
	/*
	 * @GeneratedValue注释定义了标识字段生成方式
	 * TABLE：容器指定用底层的数据表确保唯一；
	 * SEQUENCE：使用数据库德SEQUENCE列莱保证唯一（Oracle数据库通过序列来生成唯一ID）；
	 * IDENTITY：使用数据库的IDENTITY列莱保证唯一；
	 * AUTO：由容器挑选一个合适的方式来保证唯一；
	 * NONE：容器不负责主键的生成，由程序来完成。
	 */
    @GeneratedValue(generator = "PKUUID")
    /*
	 * @Column注释定义了将成员属性映射到关系表中的哪一列和该列的结构信息
	 * name：映射的列名。如：映射tbl_user表的name列，可以在name属性的上面或getName方法上面加入；
	 * unique：是否唯一；
	 * nullable：是否允许为空；
	 * length：对于字符型列，length属性指定列的最大字符长度；
	 * insertable：是否允许插入；
	 * updatable：是否允许更新；
	 * columnDefinition：定义建表时创建此列的DDL；
	 * secondaryTable：从表名。如果此列不建在主表上（默认是主表），该属性定义该列所在从表的名字。
	 */
    @Column(columnDefinition = "varchar(64) COMMENT '主键id'")
    private String id;//角色id

//    @ApiModelProperty(name = "userId", value = "用户id", example = "rtyvdvaeqgcfga", required = false)
//    @Column(columnDefinition = "varchar(64) COMMENT '用户id,用来标注这个角色是属于谁的(一级用户的子角色),默认属于平台'")
//    private String userId = LevelConstant.TOP_LEVEL;//用户id,用来标注这个角色是属于谁的(一级用户的子角色),默认属于平台

    @NotNull(message = "角色名不能为空")
    @NotEmpty(message = "角色名不能为空")
    @Size(min=1,max = 64,message = "角色名称长度在1~64之间")
    @ApiModelProperty(name = "name", value = "角色名称", example = "销售人员", required = true)
    @Column(nullable = false, columnDefinition = "varchar(32) COMMENT '角色名'")
    private String name;//角色名

    @ApiModelProperty(name = "intro", value = "简介", example = "这是销售人员的角色", required = false)
    @Column(columnDefinition = "varchar(64) COMMENT '简介'")
    private String intro;//简介

    @NotNull(message = "排序值不能为空")
    @Min(value = 0,message = "排序值不能小于0")
    @ApiModelProperty(name = "sort", value = "排序值", example = "3", required = true)
    @Column(nullable = false, columnDefinition = "tinyint(3) DEFAULT 0 COMMENT '排序值'")
    private Integer sort = 1;//排序值(表示当前角色的顺序)

    @JsonSerialize(using = Date2LongSerializer.class)
    @Column(nullable = false, columnDefinition = "timestamp DEFAULT current_timestamp COMMENT '创建时间'")
    private Date createTime;//创建时间

    @JsonSerialize(using = Date2LongSerializer.class)
    @Column(nullable = false, columnDefinition = "timestamp DEFAULT current_timestamp on update current_timestamp COMMENT '修改时间'")
    private Date updateTime;//修改时间
}
