package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 角色与菜单对应关系
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@TableName("sys_role_menu")
@ApiModel(value = "角色与菜单对应关系")
public class SysRoleMenu  implements Serializable {

	@TableId(type = IdType.AUTO)
	public Long id;

	@ApiModelProperty(value = "角色ID")
	public Long roleId;

	@ApiModelProperty(value = "菜单ID")
	public Long menuId;

}
