package com.suke.czx.modules.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.sys.entity.SysConfig;
import com.suke.czx.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * 系统参数信息
 *
 * @author czx
 * @email object_czx@163.com
 */
@RestController
@RequestMapping("/sys/config")
@AllArgsConstructor
@Api(value = "SysConfigController", tags = "系统参数信息")
public class SysConfigController extends AbstractController {
    private final SysConfigService sysConfigService;

    /**
     * 所有配置列表
     */
    @GetMapping(value = "/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
        final String keyword = mpPageConvert.getKeyword(params);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper
                    .lambda()
                    .like(SysConfig::getRemark, keyword);
        }
        IPage<SysConfig> listPage = sysConfigService.page(mpPageConvert.<SysConfig>pageParamConvert(params), queryWrapper);
        return R.ok().setPage(listPage);
    }

    @SysLog("保存配置")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R save(@RequestBody SysConfig config) {
        sysConfigService.save(config);
        return R.ok();
    }


    @SysLog("修改配置")
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(@RequestBody SysConfig config) {
        sysConfigService.updateById(config);
        return R.ok();
    }


    @SysLog("删除配置")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delete(@RequestBody Long[] ids) {
        sysConfigService.removeByIds(Arrays.asList(ids));
        return R.ok();
    }

}
