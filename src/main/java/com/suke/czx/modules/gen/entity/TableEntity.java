package com.suke.czx.modules.gen.entity;

import lombok.Data;

import java.util.List;

/**
 * 表数据
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月20日 上午12:02:55
 */
@Data
public class TableEntity {
    //表的名称
    private String tableName;
    //表的备注
    private String comments;
    //表的主键
    private ColumnEntity pk;
    //表的列名(不包含主键)
    private List<ColumnEntity> columns;

    //类名(第一个字母大写)，如：sys_user => SysUser
    private String className;
    //类名(第一个字母小写)，如：sys_user => sysUser
    private String classNameMin;
}
