package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 *
 * @author czx
 * @email object_czx@163.com
 */
@Data
@Builder
@ApiModel(value = "系统配置信息")
@EqualsAndHashCode(callSuper = true)
public class SysConfig extends Model<SysConfig> {

	@TableId
	public Long id;

	@ApiModelProperty(value = "key")
	@NotBlank(message="参数名不能为空")
	public String configKey;

	@ApiModelProperty(value = "value")
	@NotBlank(message="参数值不能为空")
	public String configValue;

	@ApiModelProperty(value = "状态 0：隐藏 1：显示")
	public Integer configStatus;

	@ApiModelProperty(value = "备注")
	public String remark;

}
