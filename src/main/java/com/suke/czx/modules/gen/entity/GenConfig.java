package com.suke.czx.modules.gen.entity;

import lombok.Data;

import java.util.List;

/**
 * Created by czx on 2019/4/28.
 */
@Data
public class GenConfig {
    private String mainPath;
    private String packagePath;
    private String moduleName;
    private String author;
    private String email;
    private List<String> genTable;
}
