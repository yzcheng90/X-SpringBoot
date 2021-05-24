package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 菜单管理
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@ApiModel(value = "菜单管理")
@EqualsAndHashCode(callSuper = true)
public class SysMenu extends Model<SysMenu> implements Serializable {

	@TableId(value = "menu_id", type = IdType.AUTO)
	@ApiModelProperty(value = "菜单ID")
	public Long menuId;

	@ApiModelProperty(value = "父菜单ID，一级菜单为0")
	public Long parentId;

	@ApiModelProperty(value = "父菜单名称")
	@TableField(exist = false)
	public String parentName;

	@ApiModelProperty(value = "菜单名称")
	public String name;

	@ApiModelProperty(value = "菜单URL")
	public String url;

	@ApiModelProperty(value = "授权(多个用逗号分隔，如：user:list,user:create)")
	public String perms;

	@ApiModelProperty(value = "类型 0：目录 1：菜单 2：按钮")
	public Integer type;

	@ApiModelProperty(value = "菜单图标")
	public String icon;

	@ApiModelProperty(value = "排序")
	public Integer orderNum;

	@ApiModelProperty(value = "ztree属性")
	@TableField(exist = false)
	public Boolean open;

	@ApiModelProperty(value = "子菜单")
	@TableField(exist = false)
	public List<?> list;
}
