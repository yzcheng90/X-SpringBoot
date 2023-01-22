package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @Description //TODO $
 * @Date 12:26
 * @Author yzcheng90@qq.com
 **/
@Data
@TableName("sys_menu_new")
@ApiModel(value = "菜单管理")
public class SysMenuNew implements Serializable {

    @TableId(value = "menu_id", type = IdType.AUTO)
    @ApiModelProperty(value = "菜单ID")
    public Long menuId;

    @ApiModelProperty(value = "父菜单ID，一级菜单为0")
    public Long parentId;

    public String path;
    public String name;
    public String component;
    public String redirect;
    public String title;
    public String isLink;
    public boolean isHide;
    public boolean isKeepAlive;
    public boolean isAffix;
    public boolean isIframe;
    public String icon;
    public String roles;
    public int orderSort;
    public boolean disabled;

}
