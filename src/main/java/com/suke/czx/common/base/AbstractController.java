package com.suke.czx.common.base;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.authentication.detail.CustomUserDetailsUser;
import com.suke.czx.common.utils.MPPageConvert;
import com.suke.czx.common.utils.UserUtil;
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

	@Autowired
	public ObjectMapper objectMapper;

	protected CustomUserDetailsUser getUser() {
		return UserUtil.getUser();
	}

	@SneakyThrows
	protected String getUserId() {
		return UserUtil.getUserId();
	}
}
