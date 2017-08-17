package com.bcdbook.meng.tools.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @Author summer
 * @Date 2017/8/15 上午8:27
 * 日志实体类
 */
/*
 * 表明这是一个实体类
 */
@Entity
/*
 * 映射到数据库表的相关注解信息,
 * 此处仅指定了映射到数据库的哪张表
 */
@Table(name = "tools_log")
@Data
//@AllArgsConstructor//全参构造
//@NoArgsConstructor//空参构造
public class Log {
    public Log() {
    }

    public Log(Integer logType, String username, String method, String requestParams, String requestIp, String requestUri, String exception, Integer responseStatus, String ajaxType) {
        this.logType = logType;
        this.username = username;
        this.method = method;
        this.requestParams = requestParams;
        this.requestIp = requestIp;
        this.requestUri = requestUri;
        this.exception = exception;
        this.responseStatus = responseStatus;
        this.ajaxType = ajaxType;
    }

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
    private String id;//主键


    @Column(nullable = false, columnDefinition = "tinyint(2) DEFAULT '1' COMMENT '日志类型 默认为1 正常日志'")
    private Integer logType;//日志类型 1操作日志，2异常日志

    @Column(columnDefinition = "varchar(64) COMMENT '操作人'")
    private String username;//操作人

    @Column(nullable = false, columnDefinition = "varchar(64) COMMENT '请求方式'")
    private String method;//请求方式

    @Column(columnDefinition = "longtext COMMENT '请求参数'")
    private String requestParams;//请求参数

    @Column(columnDefinition = "varchar(64) COMMENT '访问者ip'")
    private String requestIp;//访问者ip

    @Column(nullable = false, columnDefinition = "varchar(128) COMMENT '请求uri'")
    private String requestUri;//请求uri


    @Column(columnDefinition = "varchar(64) COMMENT '异常'")
    private String exception;//异常

    @Column(columnDefinition = "int(9) DEFAULT '1' COMMENT '服务器相应的状态码'")
    private Integer responseStatus;//响应状态

    @Column(columnDefinition = "varchar(64) COMMENT '请求方式(是否是ajax方式请求)'")
    private String ajaxType;//请求方式(是否是ajax方式请求)


    @Column(nullable = false, columnDefinition = "timestamp DEFAULT current_timestamp COMMENT '创建时间'")
    private Long createTime;//创建时间

}
