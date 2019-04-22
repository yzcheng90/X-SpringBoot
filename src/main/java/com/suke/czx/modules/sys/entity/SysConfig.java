package com.suke.czx.modules.sys.entity;


import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * 系统配置信息
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年12月4日 下午6:43:36
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class SysConfig extends Model<SysConfig> {

	@TableId
	private Long id;

	@NotBlank(message="参数名不能为空")
	private String configKey;

	@NotBlank(message="参数值不能为空")
	private String configValue;

	private Integer configStatus;

	private String remark;

}
