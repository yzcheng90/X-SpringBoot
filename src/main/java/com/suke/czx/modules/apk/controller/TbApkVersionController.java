package com.suke.czx.modules.apk.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.modules.apk.entity.TbApkVersion;
import com.suke.czx.modules.apk.service.TbApkVersionService;
import com.suke.zhjg.common.autofull.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Map;


/**
 * APK版本管理
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2023-01-26 20:32:33
 */
@RestController
@AllArgsConstructor
@RequestMapping("/apk/version")
@Api(value = "TbApkVersionController", tags = "APK版本管理")
public class TbApkVersionController extends AbstractController {
    private final TbApkVersionService tbApkVersionService;

    /**
     * 列表
     */
    @ApiOperation(value = "APK版本管理列表")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        QueryWrapper<TbApkVersion> queryWrapper = new QueryWrapper<>();
        final String keyword = mpPageConvert.getKeyword(params);
        if (StrUtil.isNotEmpty(keyword)) {
            queryWrapper.lambda().and(func -> func.like(TbApkVersion::getUpdateContent,keyword));
        }
        queryWrapper.lambda().orderByDesc(TbApkVersion::getCreateTime);
        IPage<TbApkVersion> listPage = tbApkVersionService.page(mpPageConvert.<TbApkVersion>pageParamConvert(params), queryWrapper);
        return R.ok().setData(listPage);
    }


    /**
     * 新增APK版本管理
     */
    @ApiOperation(value = "新增APK版本管理数据")
    @SysLog("新增APK版本管理数据")
    @PostMapping("/save")
    public R save(@RequestBody TbApkVersion param) {
        param.setCreateTime(new Date());
        param.setUserId(getUserId());
        tbApkVersionService.save(param);
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation(value = "删除APK版本管理数据")
    @SysLog("删除APK版本管理数据")
    @PostMapping("/delete")
    public R delete(@RequestBody TbApkVersion param) {
        if(param.getId() == null){
            return R.error("ID为空");
        }
        tbApkVersionService.removeById(param.getId());
        return R.ok();
    }

}
