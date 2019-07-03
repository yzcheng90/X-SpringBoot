package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.mapper.SysRoleMenuMapper;
import com.suke.czx.modules.sys.entity.SysRoleMenu;
import com.suke.czx.modules.sys.service.SysRoleMenuService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



/**
 * 角色与菜单对应关系
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:44:35
 */
@Service
@AllArgsConstructor
public class SysRoleMenuServiceImpl extends ServiceImpl<SysRoleMenuMapper,SysRoleMenu> implements SysRoleMenuService {

	private final SysRoleMenuMapper sysRoleMenuMapper;

	@Override
	@Transactional
	public void saveOrUpdate(Long roleId, List<Long> menuIdList) {
		//先删除角色与菜单关系
		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId,roleId));

		if(menuIdList.size() == 0){
			return ;
		}

		//保存角色与菜单关系
		Map<String, Object> map = new HashMap<>();
		map.put("roleId", roleId);
		map.put("menuIdList", menuIdList);
		sysRoleMenuMapper.saveUserMenu(map);
	}

	@Override
	public List<Long> queryMenuIdList(Long roleId) {
		return sysRoleMenuMapper
				.selectList(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId,roleId))
				.stream()
				.map(SysRoleMenu::getMenuId)
				.collect(Collectors.toList());
	}

}
