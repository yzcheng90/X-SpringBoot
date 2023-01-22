package com.suke.czx.modules.gen.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.common.utils.GenUtils;
import com.suke.czx.modules.gen.entity.ColumnEntity;
import com.suke.czx.modules.gen.entity.InfoRmationSchema;
import com.suke.czx.modules.gen.entity.MakerConfigEntity;
import com.suke.czx.modules.gen.mapper.SysGenMapper;
import com.suke.czx.modules.gen.service.SysGenService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class SysGenServiceImpl extends ServiceImpl<SysGenMapper, InfoRmationSchema> implements SysGenService {

    private final SysGenMapper sysGenMapper;
    private final GenUtils genUtils;

    @Override
    public IPage<InfoRmationSchema> queryTableList(IPage page, QueryWrapper<InfoRmationSchema> entityWrapper) {
        return sysGenMapper.queryTableList(page, entityWrapper);
    }

    @Override
    public void generatorCode(MakerConfigEntity config) {
        //查询表信息
        InfoRmationSchema table = sysGenMapper.queryTableList(new QueryWrapper<InfoRmationSchema>().eq("tableName", config.getTableName()));
        //查询列信息
        List<ColumnEntity> columns = sysGenMapper.queryColumns(new QueryWrapper<ColumnEntity>().eq("tableName", config.getTableName()));
        //生成代码
        genUtils.maker(config, table, columns);
    }
}
