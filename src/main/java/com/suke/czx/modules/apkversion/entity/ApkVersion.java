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
public class ApkVersion extends Model<ApkVersion> implements Serializable {
    //
    @TableId
    public Long id;
    //APP名
    public String appName;
    //更新内容
    public String updateContent;
    //版本码
    public Integer versionCode;
    //版本号
    public String versionName;
    //包名
    public String packageName;
    //下载地址
    public String downloadUrl;
    //文件名
    public String fileName;
    //MD5值
    public String md5Value;
    //文件大小
    public String fileSize;
    //创建时间
    public Date createTime;
    //修改时间
    public Date updateTime;
    //上传人
    public Long userId;
    //是否强制安装
    public Integer isForce;
    //是否可忽略该版本
    public Integer isIgnorable;
    //是否静默下载
    public Integer isSilent;

}
