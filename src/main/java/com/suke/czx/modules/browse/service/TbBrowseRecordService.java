package com.suke.czx.modules.browse.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.browse.entity.TbBrowseRecord;

/**
 * 浏览记录
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2023-05-09 17:42:42
 */
public interface TbBrowseRecordService extends IService<TbBrowseRecord> {

    void saveInfo(TbBrowseRecord param);

    IPage<TbBrowseRecord> getPage(IPage<TbBrowseRecord> iPage, QueryWrapper<TbBrowseRecord> queryWrapper);

}