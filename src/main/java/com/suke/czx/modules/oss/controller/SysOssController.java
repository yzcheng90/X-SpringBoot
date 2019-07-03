package com.suke.czx.modules.oss.controller;

import cn.hutool.core.map.MapUtil;
import cn.hutool.crypto.digest.DigestUtil;
import cn.hutool.crypto.digest.MD5;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.google.gson.Gson;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.ConfigConstant;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.R;
import com.suke.czx.common.validator.ValidatorUtils;
import com.suke.czx.common.validator.group.AliyunGroup;
import com.suke.czx.common.validator.group.MinioGroup;
import com.suke.czx.common.validator.group.QcloudGroup;
import com.suke.czx.common.validator.group.QiniuGroup;
import com.suke.czx.modules.apkversion.entity.ApkVersion;
import com.suke.czx.modules.oss.cloud.CloudStorageConfig;
import com.suke.czx.modules.oss.cloud.OSSFactory;
import com.suke.czx.modules.oss.entity.SysOss;
import com.suke.czx.modules.oss.service.SysOssService;
import com.suke.czx.modules.sys.service.SysConfigService;
import lombok.AllArgsConstructor;
import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
@RestController
@RequestMapping("sys/oss")
@AllArgsConstructor
public class SysOssController extends AbstractController {

	private final SysOssService sysOssService;
    private final SysConfigService sysConfigService;
    private final OSSFactory ossFactory;

    private final static String KEY = ConfigConstant.CLOUD_STORAGE_CONFIG_KEY;

    /**
     * 上传文件的临时目录
     **/
    private final String tempDir = "E:\\temp";

	/**
	 * 列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:oss:all")
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
    @RequestMapping("/config")
    @RequiresPermissions("sys:oss:all")
    public R config(){
        CloudStorageConfig config = sysConfigService.getConfigObject(KEY, CloudStorageConfig.class);

        return R.ok().put("config", config);
    }


	/**
	 * 保存云存储配置信息
	 */
	@RequestMapping("/saveConfig")
	@RequiresPermissions("sys:oss:all")
	public R saveConfig(@RequestBody CloudStorageConfig config){

		if(config.getType() == Constant.CloudService.QINIU.getValue()){
			//校验七牛数据
			ValidatorUtils.validateEntity(config, QiniuGroup.class);
		}else if(config.getType() == Constant.CloudService.ALIYUN.getValue()){
			//校验阿里云数据
			ValidatorUtils.validateEntity(config, AliyunGroup.class);
		}else if(config.getType() == Constant.CloudService.QCLOUD.getValue()){
			//校验腾讯云数据
			ValidatorUtils.validateEntity(config, QcloudGroup.class);
		}else if(config.getType() == Constant.CloudService.MINIO.getValue()){
			//校验minio数据
			ValidatorUtils.validateEntity(config, MinioGroup.class);
		}
        sysConfigService.updateValueByKey(KEY, new Gson().toJson(config));
		return R.ok();
	}
	

	/**
	 * 上传文件
	 */
	@RequestMapping("/upload")
	@RequiresPermissions("sys:oss:all")
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
	@RequestMapping("/upload/apk")
	@RequiresPermissions("sys:oss:all")
	public R uploadApk(@RequestParam("file") MultipartFile file) throws Exception {
		if (file.isEmpty()) {
			throw new RRException("上传文件不能为空");
		}

		String tempPath = tempDir+"/"+UUID.randomUUID()+".apk";
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
		tempFile.delete();
		return R.ok().put("apkVersion",apkVersion);
	}


	/**
	 * 删除
	 */
	@RequestMapping("/delete")
	@RequiresPermissions("sys:oss:all")
	public R delete(@RequestBody Long[] ids){
		sysOssService.removeById(ids);
		return R.ok();
	}

}
