package com.suke.czx.modules.gen.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.modules.gen.entity.ColumnEntity;
import com.suke.czx.modules.gen.entity.InfoRmationSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysGenMapper extends BaseMapper<InfoRmationSchema> {

    IPage<InfoRmationSchema> queryTableList(IPage page, @Param("ew") QueryWrapper<InfoRmationSchema> entityWrapper);

    InfoRmationSchema queryTableList(@Param("ew") QueryWrapper<InfoRmationSchema> entityWrapper);

    List<ColumnEntity> queryColumns(@Param("ew") QueryWrapper<ColumnEntity> entityWrapper);
}
