package com.suke.czx.modules.apkversion.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import java.io.Serializable;
import java.util.Date;


/**
 * APK版本管理
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2019-04-28 15:56:33
 */
@Data
@Builder
@TableName("tb_apk_version")
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class ApkVersion extends Model<ApkVersion> implements Serializable {
	private static final long serialVersionUID = 1L;

		//
		@TableId
		private Long id;
		//APP名
		private String appName;
		//更新内容
		private String updateContent;
		//版本码
		private Integer versionCode;
		//版本号
		private String versionName;
		//包名
		private String packageName;
		//下载地址
		private String downloadUrl;
		//文件名
		private String fileName;
		//MD5值
		private String md5Value;
		//文件大小
		private String fileSize;
		//创建时间
		private Date createTime;
		//修改时间
		private Date updateTime;
		//上传人
		private Long userId;
		//是否强制安装
		private Integer isForce;
		//是否可忽略该版本
		private Integer isIgnorable;
		//是否静默下载
		private Integer isSilent;
	
}
