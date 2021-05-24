package com.suke.czx.modules.sys.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.sys.entity.SysConfig;
import com.suke.czx.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Api(value = "SysConfigController" ,tags = "系统参数信息")
public class SysConfigController extends AbstractController {
	private final SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:config:list')")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryWrapper<SysConfig> queryWrapper = new QueryWrapper<>();
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("remark",MapUtil.getStr(params,"key"));
		}
		IPage<SysConfig> sysConfigList = sysConfigService.page(mpPageConvert.<SysConfig>pageParamConvert(params),queryWrapper);

		return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
	}

	/**
	 * 配置信息
	 */
	@RequestMapping( value = "/info/{id}",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:config:info')")
	public R info(@PathVariable("id") Long id){
		SysConfig config = sysConfigService.getById(id);

		return R.ok().put("config", config);
	}


	@SysLog("保存配置")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:config:save')")
	public R save(@RequestBody SysConfig config){
		sysConfigService.save(config);
		return R.ok();
	}


	@SysLog("修改配置")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:config:update')")
	public R update(@RequestBody SysConfig config){
		sysConfigService.updateById(config);
		return R.ok();
	}


	@SysLog("删除配置")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:config:delete')")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
