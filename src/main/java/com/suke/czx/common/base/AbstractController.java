package com.suke.czx.common.base;

import cn.hutool.core.map.MapUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.common.utils.MPPageConvert;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.HashMap;

/**
 * Controller公共组件
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年11月9日 下午9:42:26
 */

public abstract class AbstractController {
	@Autowired
	protected MPPageConvert mpPageConvert;

	protected ObjectMapper objectMapper = new ObjectMapper();

	protected Object getUser() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		强转有问题...原因有待研究
//		if(object != null){
//			return (CustomUserDetailsUser) object;
//		}
		return object;
	}

	@SneakyThrows
	protected Long getUserId() {
		if(getUser() != null){
			String userStr = objectMapper.writeValueAsString(getUser());
			HashMap<String,Object> userDetailsUser = objectMapper.readValue(userStr,HashMap.class);
			return MapUtil.getLong(userDetailsUser,"userId");
		}
		return null;
	}
}
