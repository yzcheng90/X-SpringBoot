package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 系统日志
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-08 10:40:56
 */
@Data
@ApiModel(value = "系统日志")
public class SysLog {

    @TableId(type = IdType.AUTO)
    public Long id;

    @ApiModelProperty(value = "用户名")
    public String username;

    @ApiModelProperty(value = "用户操作")
    public String operation;

    @ApiModelProperty(value = "请求方法")
    public String method;

    @ApiModelProperty(value = "请求参数")
    public String params;

    @ApiModelProperty(value = "执行时长(毫秒)")
    public Long time;

    @ApiModelProperty(value = "IP地址")
    public String ip;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    public Date createDate;
}
