package com.suke.czx.modules.sys.vo;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Description //TODO $
 * @Date 12:26
 * @Author yzcheng90@qq.com
 **/
@Data
@ApiModel(value = "菜单管理")
public class SysMenuNewVO implements Serializable {

    public Long menuId;
    public Long parentId;
    public String path;
    public String name;
    public String menuType;
    public String component;
    public String redirect;
    public String title;
    public int orderSort;
    public boolean isLink;

    public RouterMetaVO meta;

    public List<SysMenuNewVO> children;

}
