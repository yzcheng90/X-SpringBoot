package com.suke.czx.modules.gen.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class InfoRmationSchema implements Serializable {

    private String tableName;
    private String engine;
    private String tableComment;
    private String createTime;
}
