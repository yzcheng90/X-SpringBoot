package com.suke.czx.modules.oss.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.ConfigConstant;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.apk.entity.ApkVersion;
import com.suke.czx.modules.oss.cloud.CloudStorageConfig;
import com.suke.czx.modules.oss.cloud.OSSFactory;
import com.suke.czx.modules.oss.entity.SysOss;
import com.suke.czx.modules.oss.service.SysOssService;
import com.suke.czx.modules.sys.service.SysConfigService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * 文件上传
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 12:13:26
 */
@Slf4j
@RestController
@RequestMapping("sys/oss")
@AllArgsConstructor
@Api(value = "SysOssController" ,tags = "文件上传")
public class SysOssController extends AbstractController {

	private final SysOssService sysOssService;
    private final SysConfigService sysConfigService;
    private final OSSFactory ossFactory;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

	/**
	 * 列表
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:oss:all')")
	public R list(@RequestParam Map<String, Object> params){
		//查询列表数据
		QueryWrapper<SysOss> queryWrapper = new QueryWrapper<>();
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("remark",MapUtil.getStr(params,"key"));
		}
		IPage<SysOss> sysConfigList = sysOssService.page(mpPageConvert.<SysOss>pageParamConvert(params),queryWrapper);

		return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));

	}


    /**
     * 云存储配置信息
     */
    @RequestMapping(value = "/config",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:oss:all')")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);
        return R.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping(value = "/saveConfig",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:oss:all')")
	public R saveConfig(@RequestBody CloudStorageConfig config){
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
		return R.ok();
	}


	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:oss:all')")
	public R upload(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		//上传文件
		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
		String url = ossFactory.build().uploadSuffix(file.getBytes(), suffix);

		//保存文件信息
		SysOss ossEntity = new SysOss();
		ossEntity.setUrl(url);
		ossEntity.setCreateDate(new Date());
		sysOssService.save(ossEntity);

		return R.ok().put("url", url);
	}

	/**
	 * 上传文件
	 */
	@RequestMapping(value = "/upload/apk",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:oss:all')")
	public R uploadApk(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}
		String property = System.getProperty("user.dir");
		String tempPath = property+"/"+UUID.randomUUID()+".apk";
		File tempFile = new File(tempPath);
		if(tempFile.exists()){
			tempFile.mkdirs();
		}
		boolean isCreateSuccess = tempFile.createNewFile(); // 是否创建文件成功
		if(!isCreateSuccess){
			throw new RRException("文件创建失败！");
		}
		file.transferTo(tempFile);
		ApkFile apkFile = new ApkFile(tempFile);
		ApkMeta apkMeta = apkFile.getApkMeta();
		ApkVersion apkVersion = ApkVersion
				.builder()
				.versionCode(Math.toIntExact(apkMeta.getVersionCode()))
				.versionName(apkMeta.getVersionName())
				.appName(apkMeta.getLabel())
				.packageName(apkMeta.getPackageName())
				.fileName(file.getOriginalFilename())
				.md5Value(DigestUtil.md5Hex(tempFile))
				.fileSize(String.valueOf(tempFile.length()))
				.build();

		//上传文件
//		String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
//		String url = ossFactory.build().uploadSuffix(file.getBytes(), suffix);
//
//		//保存文件信息
//		SysOss ossEntity = new SysOss();
//		ossEntity.setUrl(url);
//		ossEntity.setCreateDate(new Date());
//		sysOssService.save(ossEntity);
//		apkVersion.setDownloadUrl(url);
		log.info("文件地址：{}",tempPath);
		tempFile.delete();
		return R.ok().put("apkVersion",apkVersion);
	}


	/**
	 * 删除
	 */
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:oss:all')")
	public R delete(@RequestBody Long[] ids){
		sysOssService.removeByIds(Arrays.asList(ids));
		return R.ok();
	}

}
