package com.suke.czx.common.utils;

import com.suke.czx.modules.sys.entity.SysUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * Shiro工具类
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年11月12日 上午9:49:19
 */
@Component
public class ShiroUtils {

	public Session getSession() {
		return SecurityUtils.getSubject().getSession();
	}

	public Subject getSubject() {
		return SecurityUtils.getSubject();
	}

	public SysUser getUserEntity() {
		return (SysUser)SecurityUtils.getSubject().getPrincipal();
	}

	public Long getUserId() {
		return getUserEntity().getUserId();
	}
	
	public void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public boolean isLogin() {
		return SecurityUtils.getSubject().getPrincipal() != null;
	}
}
