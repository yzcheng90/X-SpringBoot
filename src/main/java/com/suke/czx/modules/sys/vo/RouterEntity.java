package com.suke.czx.modules.sys.vo;

import lombok.Data;

import java.util.List;

/**
 * @author czx
 * @title: RouterEntity
 * @projectName x-springboot
 * @description: TODO
 * @date 2020/7/1016:25
 */
@Data
public class RouterEntity {

    private Long menuId;
    private String path;
    private String component;
    private String redirect;
    private String name;
    private List<RouterEntity> children;
}
