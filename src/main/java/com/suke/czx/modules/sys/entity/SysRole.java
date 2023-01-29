package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.suke.zhjg.common.autofull.annotation.AutoFullFieldSQL;
import com.suke.zhjg.common.autofull.annotation.AutoFullListSQL;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 角色
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@ApiModel(value = "角色")
@TableName("sys_role")
public class SysRole implements Serializable {

    public static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID")
    @TableId(value = "role_id", type = IdType.AUTO)
    public Long roleId;

    @ApiModelProperty(value = "角色名称")
    @NotBlank(message = "角色名称不能为空")
    public String roleName;

    @ApiModelProperty(value = "备注")
    public String remark;

    @ApiModelProperty(value = "创建者ID")
    public String createUserId;

    @TableField(exist = false)
    @ApiModelProperty(value = "创建者名称")
    @AutoFullFieldSQL(sql = "select username as createUserName from sys_user where user_id = {createUserId}")
    public String createUserName;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date createTime;

    @ApiModelProperty(value = "菜单ID")
    @TableField(exist = false)
    @AutoFullListSQL(sql = "select menu_id as menuIdList from sys_role_menu where role_id = {roleId}")
    public List<Long> menuIdList;
}
