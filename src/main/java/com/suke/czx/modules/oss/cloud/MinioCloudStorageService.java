package com.suke.czx.modules.oss.cloud;

import com.suke.czx.common.exception.RRException;
import io.minio.MinioClient;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @Author czx
 * @Description //TODO minio 文件存储 具体其他操作看官网 https://docs.min.io/
 **/
public class MinioCloudStorageService extends CloudStorageService{

    private MinioClient client;

    /**
     * 外链过期时间，有两种方法
     * 1、可以通过命令设置共享域（桶）为 public 就可以永久外链了
     * 2、可以通过代码层返回文件流
     */
    private Integer expires = 7 * 3600;

    public MinioCloudStorageService(CloudStorageConfig config){
        this.config = config;
        //初始化
        init();
    }

    @SneakyThrows
    private void init(){
        client = new MinioClient(config.getMinioUrl(), config.getMinioAccessKey(), config.getMinioSecretKey());
    }

    @Override
    public String upload(byte[] data, String path) {
        return upload(new ByteArrayInputStream(data),path);
    }

    @Override
    public String uploadSuffix(byte[] data, String suffix) {
        return upload(data,newFileName(suffix));
    }

    @SneakyThrows
    @Override
    public String upload(InputStream inputStream, String path) {
        try {
            client.putObject(config.getMinioBucketName(), path, inputStream, inputStream.available(), "application/octet-stream");
        } catch (Exception e) {
            throw new RRException("上传文件失败", e);
        }
        return client.presignedGetObject(config.getMinioBucketName(), path, expires);
    }

    @Override
    public String uploadSuffix(InputStream inputStream, String suffix) {
        return upload(inputStream,newFileName(suffix));
    }

}
