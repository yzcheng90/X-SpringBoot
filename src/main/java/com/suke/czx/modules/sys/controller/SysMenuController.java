package com.suke.czx.modules.sys.controller;

import com.suke.czx.common.annotation.AuthIgnore;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.Constant.MenuType;
import com.suke.czx.common.utils.R;
import com.suke.czx.modules.sys.entity.SysMenu;
import com.suke.czx.modules.sys.service.PermissionsService;
import com.suke.czx.modules.sys.service.SysMenuService;
import com.suke.czx.modules.sys.vo.RouterEntity;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

/**
 * 系统菜单
 *
 * @author czx
 * @email object_czx@163.com
 */
@Slf4j
@RestController
@RequestMapping("/sys/menu")
@AllArgsConstructor
@Api(value = "SysMenuController" ,tags = "系统菜单")
public class SysMenuController extends AbstractController {
	private final SysMenuService sysMenuService;
	private final PermissionsService permissionsService;

	/**
	 * 导航菜单
	 */
	@RequestMapping(value = "/nav",method = RequestMethod.POST)
	public R nav(){
		List<SysMenu> menuList = sysMenuService.getUserMenuList(getUserId());
		Set<String> permissions = permissionsService.getUserPermissions(getUserId());
		return R.ok().put("menuList", menuList).put("permissions", permissions);
	}

	@AuthIgnore
	@RequestMapping(value = "/getRouter",method = RequestMethod.GET)
	public R getRouter(){
		List<RouterEntity> userMenu = sysMenuService.getUserMenu(1l);
		log.info("========{}",userMenu);
		return R.ok().put("data",userMenu);
	}

	/**
	 * 所有菜单列表
	 */
	@RequestMapping(value = "/list",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:menu:list')")
	public List<SysMenu> list(){
		List<SysMenu> menuList = sysMenuService.list();

		return menuList;
	}

	/**
	 * 选择菜单(添加、修改菜单)
	 */
	@RequestMapping(value = "/select",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:menu:select')")
	public R select(){
		//查询列表数据
		List<SysMenu> menuList = sysMenuService.queryNotButtonList();

		//添加顶级菜单
		SysMenu root = new SysMenu();
		root.setMenuId(0L);
		root.setName("一级菜单");
		root.setParentId(-1L);
		root.setOpen(true);
		menuList.add(root);

		return R.ok().put("menuList", menuList);
	}

	/**
	 * 菜单信息
	 */
	@RequestMapping(value = "/info/{menuId}",method = RequestMethod.GET)
	@PreAuthorize("hasRole('sys:menu:info')")
	public R info(@PathVariable("menuId") Long menuId){
		SysMenu menu = sysMenuService.getById(menuId);
		return R.ok().put("menu", menu);
	}

	/**
	 * 保存
	 */
	@SysLog("保存菜单")
	@RequestMapping(value = "/save",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:menu:save')")
	public R save(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.save(menu);

		return R.ok();
	}

	/**
	 * 修改
	 */
	@SysLog("修改菜单")
	@RequestMapping(value = "/update",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:menu:update')")
	public R update(@RequestBody SysMenu menu){
		//数据校验
		verifyForm(menu);

		sysMenuService.updateById(menu);

		return R.ok();
	}

	/**
	 * 删除
	 */
	@SysLog("删除菜单")
	@RequestMapping(value = "/delete",method = RequestMethod.POST)
	@PreAuthorize("hasRole('sys:menu:delete')")
	public R delete(long menuId){
		//判断是否有子菜单或按钮
		List<SysMenu> menuList = sysMenuService.queryListParentId(menuId);
		if(menuList.size() > 0){
			return R.error("请先删除子菜单或按钮");
		}

		sysMenuService.removeById(menuId);

		return R.ok();
	}

	/**
	 * 验证参数是否正确
	 */
	private void verifyForm(SysMenu menu){
		if(StringUtils.isBlank(menu.getName())){
			throw new RRException("菜单名称不能为空");
		}

		if(menu.getParentId() == null){
			throw new RRException("上级菜单不能为空");
		}

		//菜单
		if(menu.getType() == MenuType.MENU.getValue()){
			if(StringUtils.isBlank(menu.getUrl())){
				throw new RRException("菜单URL不能为空");
			}
		}

		//上级菜单类型
		int parentType = MenuType.CATALOG.getValue();
		if(menu.getParentId() != 0){
			SysMenu parentMenu = sysMenuService.getById(menu.getParentId());
			parentType = parentMenu.getType();
		}

		//目录、菜单
		if(menu.getType() == MenuType.CATALOG.getValue() ||
				menu.getType() == MenuType.MENU.getValue()){
			if(parentType != MenuType.CATALOG.getValue()){
				throw new RRException("上级菜单只能为目录类型");
			}
			return ;
		}

		//按钮
		if(menu.getType() == MenuType.BUTTON.getValue()){
			if(parentType != MenuType.MENU.getValue()){
				throw new RRException("上级菜单只能为菜单类型");
			}
			return ;
		}
	}
}
