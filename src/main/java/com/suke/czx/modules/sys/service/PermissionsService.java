package com.suke.czx.modules.sys.service;

import java.util.Set;

/**
 * shiro相关接口
 * @author czx
 * @email object_czx@163.com
 * @date 2017-06-06 8:49
 */
public interface PermissionsService {
    /**
     * 获取用户权限列表
     */
    Set<String> getUserPermissions(long userId);
}
