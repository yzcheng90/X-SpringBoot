package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.suke.zhjg.common.autofull.annotation.AutoFullEmpty;
import com.suke.zhjg.common.autofull.annotation.AutoFullListSQL;
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

    @TableId(value = "user_id", type = IdType.ASSIGN_UUID)
    public String userId;

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    public String username;

    @ApiModelProperty(value = "姓名")
    public String name;

    @AutoFullEmpty
    @ApiModelProperty(value = "密码")
    public String password;

    @ApiModelProperty(value = "邮箱")
    @NotBlank(message = "邮箱不能为空")
    public String email;

    @ApiModelProperty(value = "手机号")
    public String mobile;

    @ApiModelProperty(value = "头像")
    public String photo;

    @ApiModelProperty(value = "状态  0：禁用   1：正常")
    public Integer status;

    @ApiModelProperty(value = "创建者ID")
    public String createUserId;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime;

    @TableField(exist = false)
    @ApiModelProperty(value = "角色ID")
    @AutoFullListSQL(sql = "select role_id as roleIdList from sys_user_role where user_id = {userId}")
    public List<Long> roleIdList;
}
