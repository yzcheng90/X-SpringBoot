package com.suke.czx.modules.oss.cloud;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang.StringUtils;
import java.io.InputStream;
import java.util.UUID;

/**
 * 云存储(支持七牛、阿里云、腾讯云、又拍云)
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-25 14:58
 */
public abstract class CloudStorageService {
    /** 云存储配置信息 */
    CloudStorageConfig config;

    public String newFileName(){
        return  UUID.randomUUID().toString().replaceAll("-", "");
    }
    public String newFileName(String suffix){
        return  UUID.randomUUID().toString().replaceAll("-", "") + "." + suffix;
    }

    /**
     * 文件路径
     * @param prefix 前缀
     * @param suffix 后缀
     * @return 返回上传路径
     */
    public String getPath(String prefix, String suffix) {

        //文件路径
        String path = DateUtil.today() + "/" + newFileName();

        if(StringUtils.isNotBlank(prefix)){
            path = prefix + "/" + path;
        }

        return path + suffix;
    }

    /**
     * 文件上传
     * @param data    文件字节数组
     * @param path    文件路径，包含文件名
     * @return        返回http地址
     */
    public abstract String upload(byte[] data, String path);

    /**
     * 文件上传
     * @param data     文件字节数组
     * @param suffix   后缀
     * @return         返回http地址
     */
    public abstract String uploadSuffix(byte[] data, String suffix);

    /**
     * 文件上传
     * @param inputStream   字节流
     * @param path          文件路径，包含文件名
     * @return              返回http地址
     */
    public abstract String upload(InputStream inputStream, String path);

    /**
     * 文件上传
     * @param inputStream  字节流
     * @param suffix       后缀
     * @return             返回http地址
     */
    public abstract String uploadSuffix(InputStream inputStream, String suffix);

}
