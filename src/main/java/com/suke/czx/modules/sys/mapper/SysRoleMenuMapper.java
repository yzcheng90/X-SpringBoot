package com.suke.czx.modules.sys.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suke.czx.modules.sys.entity.SysRoleMenu;

import java.util.Map;

/**
 * 角色与菜单对应关系
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:33:46
 */
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {

	void saveUserMenu(Map<String, Object> map);
}
