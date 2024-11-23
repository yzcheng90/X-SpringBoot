package com.suke.czx.modules.browse.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.suke.czx.common.annotation.ShardingTable;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


/**
 * 浏览记录
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2023-05-09 17:42:42
 */
@Data
@ShardingTable
@TableName("tb_browse_record")
public class TbBrowseRecord implements Serializable {
    public static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    @Schema(description = "记录ID")
    @JsonProperty(value = "recordId")
    public String recordId;

    @Schema(description = "用户ID")
    @JsonProperty(value = "userId")
    public String userId;

    @Schema(description = "观看状态")
    @JsonProperty(value = "watchStatus")
    public String watchStatus;

    @Schema(description = "用户请求ip")
    @JsonProperty(value = "requestIp")
    public String requestIp;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JsonProperty(value = "createTime")
    public Date createTime;


}