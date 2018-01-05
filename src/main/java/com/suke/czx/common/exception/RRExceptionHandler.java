package com.suke.czx.common.exception;

import com.suke.czx.common.utils.AppBaseResult;
import org.apache.shiro.authz.AuthorizationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理器
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年10月27日 下午10:16:19
 */
@RestControllerAdvice
public class RRExceptionHandler extends AppBaseResult {
	private Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 自定义异常
	 */
	@ExceptionHandler(RRException.class)
	public AppBaseResult handleRRException(RRException e){
		AppBaseResult appBaseResult = new AppBaseResult();
		appBaseResult.setCode(e.getCode());
		appBaseResult.setMessage(e.getMessage());
		return appBaseResult;
	}

	@ExceptionHandler(DuplicateKeyException.class)
	public AppBaseResult handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return AppBaseResult.error("数据库中已存在该记录");
	}

	@ExceptionHandler(AuthorizationException.class)
	public AppBaseResult handleAuthorizationException(AuthorizationException e){
		logger.error(e.getMessage(), e);
		return AppBaseResult.error("没有权限，请联系管理员授权");
	}

	@ExceptionHandler(Exception.class)
	public AppBaseResult handleException(Exception e){
		logger.error(e.getMessage(), e);
		return AppBaseResult.error();
	}
}
