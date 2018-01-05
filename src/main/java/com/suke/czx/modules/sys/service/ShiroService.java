package com.suke.czx.modules.sys.service;

import com.suke.czx.modules.sys.entity.SysUserTokenEntity;
import com.suke.czx.modules.sys.entity.SysUserEntity;

import java.util.Set;

/**
 * shiro相关接口
 * @author czx
 * @email object_czx@163.com
 * @date 2017-06-06 8:49
 */
public interface ShiroService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);

    SysUserTokenEntity queryByToken(String token);

    /**
     * 根据用户ID，查询用户
     * @param userId
     */
    SysUserEntity queryUser(Long userId);
}
