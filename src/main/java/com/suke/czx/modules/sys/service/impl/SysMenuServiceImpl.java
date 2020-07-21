package com.suke.czx.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.mapper.SysMenuMapper;
import com.suke.czx.modules.sys.entity.SysMenu;
import com.suke.czx.modules.sys.service.SysMenuService;
import com.suke.czx.modules.sys.vo.RouterEntity;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Lazy
@Service
@AllArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper,SysMenu> implements SysMenuService {

	private final SysMenuMapper sysMenuMapper;

	@Override
	public List<SysMenu> queryListParentId(Long parentId, List<Long> menuIdList) {
		List<SysMenu> menuList = queryListParentId(parentId);
		if(menuIdList == null){
			return menuList;
		}

		List<SysMenu> userMenuList = new ArrayList<>();
		for(SysMenu menu : menuList){
			if(menuIdList.contains(menu.getMenuId())){
				userMenuList.add(menu);
			}
		}
		return userMenuList;
	}

	@Override
	public List<SysMenu> queryListParentId(Long parentId) {
		QueryWrapper<SysMenu> queryWrapper = new QueryWrapper<>();
		queryWrapper
				.eq("parent_id",parentId)
				.orderByAsc("order_num");
		return sysMenuMapper.selectList(queryWrapper);
	}

	@Override
	public List<SysMenu> queryNotButtonList() {
		return sysMenuMapper.queryNotButtonList();
	}

	@Override
	public List<SysMenu> getUserMenuList(Long userId) {
		//系统管理员，拥有最高权限
		if(userId == Constant.SUPER_ADMIN){
			return getAllMenuList(null);
		}
		List<Long> menuIdList = sysMenuMapper.queryAllMenuId(userId);
		return getAllMenuList(menuIdList);
	}


	@Override
	public List<SysMenu> queryUserList(Long userId) {
		return sysMenuMapper.queryUserList(userId);
	}

	@Override
	public List<RouterEntity> getUserMenu(Long userId) {


		return getRouterList();
	}


	public List<RouterEntity> getRouterList(){
		return getRouterChildList(null,0);
	}

	public  List<RouterEntity> getRouterChildList(Long menuId,int index){
		List<RouterEntity> routerEntities = new ArrayList<>();
		QueryWrapper<SysMenu> queryWrapper = new QueryWrapper();
		if(menuId == null){
			queryWrapper.lambda().eq(SysMenu::getType, index);
		}else {
			queryWrapper.lambda().eq(SysMenu::getParentId, menuId).eq(SysMenu::getType, index);
		}
		List<SysMenu> sysMenus = sysMenuMapper.selectList(queryWrapper);
		if(CollUtil.isNotEmpty(sysMenus)){
			index ++;
			int finalIndex = index;
			sysMenus.forEach(sysMenu -> {
				RouterEntity entity = new RouterEntity();
				entity.setMenuId(sysMenu.getMenuId());
				entity.setName(sysMenu.getName());
				entity.setPath(sysMenu.getUrl());
				entity.setRedirect("/");
				entity.setComponent(sysMenu.getUrl());
				entity.setChildren(getRouterChildList(sysMenu.getMenuId(), finalIndex));
				routerEntities.add(entity);
			});
		}
		return routerEntities;
	}


	/**
	 * 获取所有菜单列表
	 */
	private List<SysMenu> getAllMenuList(List<Long> menuIdList){
		//查询根菜单列表
		List<SysMenu> menuList = queryListParentId(0L, menuIdList);
		//递归获取子菜单
		getMenuTreeList(menuList, menuIdList);
		return menuList;
	}

	/**
	 * 递归
	 */
	private List<SysMenu> getMenuTreeList(List<SysMenu> menuList, List<Long> menuIdList){
		List<SysMenu> subMenuList = new ArrayList<SysMenu>();
		for(SysMenu entity : menuList){
			if(entity.getType() == Constant.MenuType.CATALOG.getValue()){//目录
				entity.setList(getMenuTreeList(queryListParentId(entity.getMenuId(), menuIdList), menuIdList));
			}
			subMenuList.add(entity);
		}
		return subMenuList;
	}
}
