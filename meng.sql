drop table if exists sys_i_resource
drop table if exists sys_role
drop table if exists sys_user
drop table if exists sys_user_role
create table sys_i_resource (
    id varchar(64) COMMENT '主键id' not null,
    create_time timestamp DEFAULT current_timestamp COMMENT '创建时间' not null,
    description varchar(64) COMMENT '详细描述',
    i_resource_type tinyint(2) DEFAULT 1 COMMENT '资源的类型' not null,
    icon varchar(16) COMMENT '图标(阿里图标库的标识)',
    name varchar(32) COMMENT '资源名称' not null,
    parent_id varchar(64) COMMENT '父级账号的id(如果不是子账户, 则为TOP_LEVEL)',
    sort tinyint(3) DEFAULT 0 COMMENT '排序值' not null,
    update_time timestamp DEFAULT current_timestamp on update current_timestamp COMMENT '修改时间' not null,
    url varchar(128) COMMENT '连接地址',
    primary key (id)
)
create table sys_role (
    id varchar(64) COMMENT '主键id' not null,
    create_time timestamp DEFAULT current_timestamp COMMENT '创建时间' not null,
    intro varchar(64) COMMENT '简介',
    name varchar(32) COMMENT '角色名' not null,
    sort tinyint(3) DEFAULT 0 COMMENT '排序值' not null,
    update_time timestamp DEFAULT current_timestamp on update current_timestamp COMMENT '修改时间' not null,
    primary key (id)
)
create table sys_user (
    id varchar(64) COMMENT '主键id' not null,
    certification_status tinyint(2) DEFAULT '1' COMMENT '认证状态 默认1 未认证' not null,
    create_time timestamp DEFAULT current_timestamp COMMENT '创建时间' not null,
    email varchar(64) COMMENT '邮箱',
    is_child boolean  DEFAULT false COMMENT '是否是子账户 默认false 不是' not null,
    mobile varchar(32) COMMENT '手机号',
    nickname varchar(32) COMMENT '昵称',
    open_id varchar(64) COMMENT '微信关联的openId',
    parent_id varchar(64) COMMENT '父级账号的id(如果不是子账户, 则为TOP_LEVEL)',
    update_time timestamp DEFAULT current_timestamp on update current_timestamp COMMENT '修改时间' not null,
    user_password varchar(64) COMMENT '密码' not null,
    user_status tinyint(2) DEFAULT '1' COMMENT '用户状态 默认1 正常' not null,
    user_type tinyint(2) DEFAULT '1' COMMENT '用户类型 默认1 普通用户' not null,
    username varchar(64) COMMENT '用户名' not null,
    primary key (id)
)
create table sys_user_role (
    role_id varchar(64) COMMENT '角色id' not null,
    user_id varchar(64) COMMENT '用户id' not null,
    primary key (role_id, user_id)
)
alter table sys_role
    add constraint UK_bqy406dtsr7j7d6fawi1ckyn1 unique (name)
alter table sys_user
    add constraint UK_51bvuyvihefoh4kp5syh2jpi4 unique (username)