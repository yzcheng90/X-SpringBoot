package com.suke.czx.modules.app.controller.appUpdate;

import java.util.HashMap;
import com.google.gson.Gson;
import com.suke.czx.common.utils.*;
import java.util.List;
import org.slf4j.LoggerFactory;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import com.suke.czx.modules.app.service.appUpdate.AppUpdateService;
import javax.annotation.Resource;


/**
 * APP版本管理
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2018-01-05 15:28:57
 */
@Api(value = "API - AppUpdateController ", description = "APP版本管理")
@RestController
@RequestMapping("/app")
public class AppUpdateController {

    private org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Resource(name = "appUpdateService")
	private AppUpdateService appUpdateService;
	
	/**
	 * 列表
	 */
    @ApiOperation(value="列表", notes="列表")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appUpdate/list")
	public AppBaseResult list(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppUpdateController 列表",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
		//查询列表数据
        Query query = new Query(params);
        query.isPaging(true);
		List<HashMap<String,Object>> appUpdateList = appUpdateService.queryList(query);
		PageUtils pageUtil = new PageUtils(appUpdateList, query.getTotle(), query.getLimit(), query.getPage());
        return AppBaseResult.success().setEncryptData(pageUtil);
	}
	
	
	/**
	 * 信息
	 */
    @ApiOperation(value="信息", notes="信息")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appUpdate/info")
	public AppBaseResult info(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppUpdateController 信息",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
        HashMap<String,Object> data = appUpdateService.queryObject(params);
        return AppBaseResult.success().setEncryptData(data);
	}
	
	/**
	 * 保存
	 */
    @ApiOperation(value="保存", notes="保存")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appUpdate/save")
	public AppBaseResult save(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppUpdateController 保存",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
		appUpdateService.saveInfo(params);
        return AppBaseResult.success();
	}
	
	/**
	 * 修改
	 */
    @ApiOperation(value="修改", notes="修改")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appUpdate/update")
	public AppBaseResult update(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppUpdateController 修改",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
		appUpdateService.updateInfo(params);
        return AppBaseResult.success();
	}
	
	/**
	 * 删除
	 */
    @ApiOperation(value="删除", notes="删除")
    @ApiImplicitParams({@ApiImplicitParam(name = "token", value = "token", required = true,dataType = "string", paramType = "query", defaultValue = "")})
	@PostMapping("/appUpdate/delete")
	public AppBaseResult delete(@RequestBody AppBaseResult appBaseResult)throws Exception{
        logger.info("AppUpdateController 修改",appBaseResult.decryptData());
        HashMap<String,Object> params = new Gson().fromJson(appBaseResult.decryptData(),HashMap.class);
		appUpdateService.deleteInfo(params);
        return AppBaseResult.success();
	}
	
}
