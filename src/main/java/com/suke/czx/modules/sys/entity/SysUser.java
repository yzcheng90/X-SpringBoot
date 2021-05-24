package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@ApiModel(value = "系统用户")
@EqualsAndHashCode(callSuper = true)
public class SysUser extends Model<SysUser> {

	@TableId(value = "user_id", type = IdType.AUTO)
	public Long userId;

	@ApiModelProperty(value = "用户名")
	@NotBlank(message="用户名不能为空")
	public String username;

	@ApiModelProperty(value = "密码")
	@NotBlank(message="密码不能为空")
	public String password;

	@ApiModelProperty(value = "邮箱")
	@NotBlank(message="邮箱不能为空")
	public String email;

	@ApiModelProperty(value = "手机号")
	public String mobile;

	@ApiModelProperty(value = "状态  0：禁用   1：正常")
	public Integer status;

	@ApiModelProperty(value = "创建者ID")
	public Long createUserId;

	@ApiModelProperty(value = "创建时间")
	public Date createTime;

	@TableField(exist = false)
	@ApiModelProperty(value = "角色ID")
	public List<Long> roleIdList;
}
