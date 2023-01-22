package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
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
    public Long createUserId;

    @ApiModelProperty(value = "创建时间")
    public Date createTime;

    @ApiModelProperty(value = "菜单ID")
    @TableField(exist = false)
    public List<Long> menuIdList;
}
