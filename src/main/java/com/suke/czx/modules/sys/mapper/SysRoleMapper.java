package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysRole;

import java.util.List;

/**
 * 角色管理
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:33:33
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 查询用户创建的角色ID列表
     */
    List<Long> queryRoleIdList(Long createUserId);

    /**
     * 查询用户的所有菜单ID
     */
    List<Long> queryAllMenuId(String userId);

}
