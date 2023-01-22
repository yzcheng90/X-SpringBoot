package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 系统用户
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@TableName("sys_user")
@ApiModel(value = "系统用户")
public class SysUser implements Serializable {

    public static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    public Long userId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    public String username;

    @ApiModelProperty(value = "密码")
    public String password;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    public String email;

    @ApiModelProperty(value = "手机号")
    public String mobile;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    public Integer status;

    @TableField(exist = false)
    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    public Boolean userStatus;

    @ApiModelProperty(value = "创建者ID")
    public Long createUserId;

    @ApiModelProperty(value = "创建时间")
    public Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色ID")
    public List<Long> roleIdList;
}
