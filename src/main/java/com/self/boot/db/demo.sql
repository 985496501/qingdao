-- 创建表的基本demo
create table test_demo
(
    id           bigint unsigned auto_increment comment 'id主键 自增' primary key,
    order_number varchar(32)         default ''                not null comment '订单编号',
    created_by   varchar(16)                                   not null comment '创建人',
    updated_by   varchar(16)         default ''                null comment '修改人',
    created_at   datetime            default current_timestamp not null comment '创建时间',
    updated_at   datetime            default current_timestamp not null on update current_timestamp comment '修改时间',
    is_deleted   tinyint(1) unsigned default 0                 not null comment '删除标记 1-删除 0-未删除',
    constraint uk_order_number unique (order_number)
) comment '测试环境的demo';

create index idx_created_at on test_demo (created_at);

-- 左边是自定义的字段 右边是阿里规范
-- 是否状态前面 is_xxx 表示 统一 0否1是
-- 常用的数据类型和字节数必须统一 这个就是 tinyint(1) unsigned
-- 状态 `status`: 状态之间是有顺序转变的 有关系的  `state`: 状态是相互独立的
-- user 的账户使用状态  `status` tinyint(1) unsigned 0-删除 1-正常 2-冻结 3-xxx
-- created_at == gmt_create
-- updated_at == gmt_modified
-- is_deleted == is_delete

-- https://blog.csdn.net/qq_41699100/article/details/86628814 阿里开发强制要求的11条索引创建规范，提高性能