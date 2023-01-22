package com.suke.czx.common.utils;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author czx
 * @Description //TODO Page 数据转换
 * @Date 17:13 2019/4/18
 **/
@Component
public class MPPageConvert {

    public String getKeyword(Map<String, Object> param){
        final String keyword = MapUtil.getStr(param, "keyword");
        return keyword;
    }

    /**
     * @Author czx
     * @Description //TODO 前台传过来的参数转换为MyBatis Plus的Page
     * @Date 17:14 2019/4/18
     * @Param [param]
     * @return com.baomidou.mybatisplus.core.metadata.IPage<T>
     **/
    public <T> IPage<T> pageParamConvert(Map<String, Object> param){
        int currPage = 1;
        int limit = 10;
        if(MapUtil.getInt(param,"current") != null){
            currPage = MapUtil.getInt(param,"current");
        }
        if(MapUtil.getInt(param,"limit") != null){
            limit = MapUtil.getInt(param,"limit");
        }
        IPage<T> page = new Page<>(currPage,limit);
        return page;
    }

    /**
     * @Author czx
     * @Description //TODO 将MyBatis Plus 的Page 转换为前台能用的Page
     * @Date 17:14 2019/4/18
     * @Param [page]
     * @return java.util.HashMap
     **/
    public HashMap pageValueConvert(IPage<?> page){
        HashMap<Object,Object> pageData = new HashMap<>();
        pageData.put("list",page.getRecords());
        pageData.put("totalCount",page.getTotal());
        pageData.put("limit",page.getSize());
        pageData.put("current",page.getCurrent());
        pageData.put("totalPage",page.getPages());
        return pageData;
    }
}
