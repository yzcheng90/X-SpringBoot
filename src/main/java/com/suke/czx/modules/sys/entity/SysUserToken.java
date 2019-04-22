package com.suke.czx.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;


/**
 * 系统用户Token
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserToken extends Model<SysUserRole> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//用户ID
	@TableId(value = "user_id", type = IdType.INPUT)
	private Long userId;
	//token
	private String token;
	//过期时间
	private Date expireTime;
	//更新时间
	private Date updateTime;
}
