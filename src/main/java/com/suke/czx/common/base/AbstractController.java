package com.suke.czx.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.authentication.detail.CustomUserDetailsUser;
import com.suke.czx.common.utils.MPPageConvert;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

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

	protected CustomUserDetailsUser getUser() {
		Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(object != null){
			return (CustomUserDetailsUser) object;
		}
		return null;
	}

	@SneakyThrows
	protected Long getUserId() {
		return getUser() == null ? null :getUser().getUserId();
	}
}
