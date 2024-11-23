package com.suke.czx.modules.browse.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.AuthIgnore;
import com.suke.czx.common.annotation.ResourceAuth;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.IPUtils;
import com.suke.czx.common.utils.R;
import com.suke.czx.config.ShardingTableConfig;
import com.suke.czx.modules.browse.entity.TbBrowseRecord;
import com.suke.czx.modules.browse.service.TbBrowseRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * 浏览记录
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2023-05-09 17:42:42
 */
@RestController
@AllArgsConstructor
@RequestMapping("/browse/record")
@Tag(name = "TbAdvertiserRecordController", description = "浏览记录")
public class TbBrowseRecordController extends AbstractController {
    private final TbBrowseRecordService tbAdvertiserRecordService;


    @AuthIgnore
    @GetMapping("/list")
    //@ResourceAuth(value = "浏览记录列表", module = "浏览记录")
    public R list(@RequestParam Map<String, Object> params) {

        //查询列表数据
        QueryWrapper<TbBrowseRecord> queryWrapper = new QueryWrapper<>();
        final String keyword = mpPageConvert.getKeyword(params);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.and(func -> {
                func.like("t.name", keyword).or().like("t.username", keyword);
            });
        }

        String startDate = MapUtil.getStr(params, "startDate");
        String endDate = MapUtil.getStr(params, "endDate");
        if (StrUtil.isNotEmpty(startDate) && StrUtil.isNotEmpty(endDate)) {
            queryWrapper.ge("create_time", startDate + " 00:00:00")
                    .le("create_time", endDate + " 23:59:59");
        }
        queryWrapper.lambda().orderByDesc(TbBrowseRecord::getCreateTime);
        // 设置当前查询哪个表，如果不设置就默认查询最新分表
        ShardingTableConfig.setTableName("tb_browse_record");
        IPage<TbBrowseRecord> listPage = tbAdvertiserRecordService.getPage(mpPageConvert.<TbBrowseRecord>pageParamConvert(params), queryWrapper);
        return R.ok().setData(listPage);
    }


    @SysLog("新增浏览记录数据")
    @PostMapping("/save")
    @ResourceAuth(value = "新增浏览记录数据", module = "浏览记录")
    public R save(@RequestBody TbBrowseRecord param, HttpServletRequest request) {
        if (StrUtil.isEmpty(param.getWatchStatus())) {
            return R.error("观看状态为空");
        }

        param.setCreateTime(new Date());
        String ipAddr = IPUtils.getIpAddr(request);
        param.setRequestIp(ipAddr);
        tbAdvertiserRecordService.saveInfo(param);
        return R.ok();
    }


    @SysLog("删除浏览记录数据")
    @PostMapping("/delete")
    @ResourceAuth(value = "删除浏览记录数据", module = "浏览记录")
    public R delete(@RequestBody TbBrowseRecord param) {
        tbAdvertiserRecordService.removeById(param.getRecordId());
        return R.ok();
    }

}