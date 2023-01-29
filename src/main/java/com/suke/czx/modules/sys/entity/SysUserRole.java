package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户与角色对应关系
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@ApiModel(value = "用户与角色对应关系")
public class SysUserRole implements Serializable {

    @TableId
    public Long id;

    @ApiModelProperty(value = "用户ID")
    public String userId;

    @ApiModelProperty(value = "角色ID")
    public Long roleId;

}
