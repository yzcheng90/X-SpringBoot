package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysUser;


/**
 * 系统用户
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:43:39
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(String userId, String password, String newPassword);

    void saveUserRole(SysUser user);

    void updateUserRole(SysUser user);

}
