package com.suke.czx.modules.gen.entity;

import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class InfoRmationSchema extends Model<InfoRmationSchema> implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;
    private String engine;
    private String tableComment;
    private String createTime;
}
