package com.suke.czx.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysRoleMenu;
import com.suke.czx.modules.sys.mapper.SysRoleMenuMapper;
import com.suke.czx.modules.sys.service.SysRoleMenuService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;



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
		sysRoleMenuMapper.delete(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId,roleId));
		if(CollUtil.isNotEmpty(menuIdList)){
			menuIdList.forEach(id->{
				SysRoleMenu menu = new SysRoleMenu();
				menu.setRoleId(roleId);
				menu.setMenuId(id);
				sysRoleMenuMapper.insert(menu);
			});
		}
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
