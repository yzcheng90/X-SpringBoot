package com.suke.czx.modules.browse.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.browse.entity.TbBrowseRecord;
import com.suke.czx.modules.browse.mapper.TbBrowseRecordMapper;
import com.suke.czx.modules.browse.service.TbBrowseRecordService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


/**
 * 浏览记录
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2023-05-09 17:42:42
 */
@Service
@AllArgsConstructor
public class TbBrowseRecordServiceImpl extends ServiceImpl<TbBrowseRecordMapper, TbBrowseRecord> implements TbBrowseRecordService {

    @Async
    @Override
    public void saveInfo(TbBrowseRecord param) {
        baseMapper.insert(param);
    }

    @Override
    public IPage<TbBrowseRecord> getPage(IPage<TbBrowseRecord> iPage, QueryWrapper<TbBrowseRecord> queryWrapper) {
        return baseMapper.selectPage(iPage, queryWrapper);
    }
}