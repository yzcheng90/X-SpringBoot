package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;


/**
 * 登录日志
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@ApiModel(value = "登录日志")
@TableName("tb_login_log")
public class SysLoginLog {

    @TableId(value = "log_id", type = IdType.ASSIGN_UUID)
    public String logId;

    @ApiModelProperty(value = "用户名")
    public String username;

    @ApiModelProperty(value = "用户操作")
    public String optionName;

    @ApiModelProperty(value = "操作终端")
    public String optionTerminal;

    @ApiModelProperty(value = "IP地址")
    public String optionIp;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @ApiModelProperty(value = "创建时间")
    public Date optionTime;
}
