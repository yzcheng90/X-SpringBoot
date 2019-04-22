package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.sys.entity.SysUserToken;

/**
 * 用户Token
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2017-03-23 15:22:07
 */
public interface SysUserTokenService extends IService<SysUserToken> {

	/**
	 * 生成token
	 * @param userId  用户ID
	 */
	R createToken(long userId);

	/**
	 * 退出，修改token值
	 * @param userId  用户ID
	 */
	void logout(long userId);

}
