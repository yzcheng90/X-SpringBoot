package com.suke.czx.modules.sys.controller;

import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.suke.czx.common.annotation.SysLog;
import com.suke.czx.common.base.AbstractController;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.R;
import com.suke.czx.common.validator.Assert;
import com.suke.czx.common.validator.ValidatorUtils;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.entity.SysUserRole;
import com.suke.czx.modules.sys.service.SysUserRoleService;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.ArrayUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年10月31日 上午10:40:10
 */

@RestController
@RequestMapping("/sys/user")
@AllArgsConstructor
public class SysUserController extends AbstractController {

	private final SysUserService sysUserService;
	private final SysUserRoleService sysUserRoleService;
	
	/**
	 * 所有用户列表
	 */
	@RequestMapping("/list")
	@RequiresPermissions("sys:user:list")
	public R list(@RequestParam Map<String, Object> params){
		//只有超级管理员，才能查看所有管理员列表
		if(getUserId() != Constant.SUPER_ADMIN){
			params.put("createUserId", getUserId());
		}

		//查询列表数据
		QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
		if(MapUtil.getStr(params,"key") != null){
			queryWrapper
					.like("username",MapUtil.getStr(params,"key"))
					.or()
					.like("mobile",MapUtil.getStr(params,"key"));
		}
		IPage<SysUser> sysConfigList = sysUserService.page(mpPageConvert.<SysUser>pageParamConvert(params),queryWrapper);

		return R.ok().put("page", mpPageConvert.pageValueConvert(sysConfigList));
	}
	
	/**
	 * 获取登录的用户信息
	 */
	@RequestMapping("/info")
	public R info(){
		return R.ok().put("user", getUser());
	}
	
	/**
	 * 修改登录用户密码
	 */
	@SysLog("修改密码")
	@RequestMapping("/password")
	public R password(String password, String newPassword){
		Assert.isBlank(newPassword, "新密码不为能空");
		
		//sha256加密
		password = new Sha256Hash(password, getUser().getSalt()).toHex();
		//sha256加密
		newPassword = new Sha256Hash(newPassword, getUser().getSalt()).toHex();
				
		//更新密码
		int count = sysUserService.updatePassword(getUserId(), password, newPassword);
		if(count == 0){
			return R.error("原密码不正确");
		}
		
		return R.ok();
	}
	
	/**
	 * 用户信息
	 */
	@RequestMapping("/info/{userId}")
	@RequiresPermissions("sys:user:info")
	public R info(@PathVariable("userId") Long userId){
		SysUser user = sysUserService.getById(userId);

		//获取用户所属的角色列表
		List<Long> roleIdList = sysUserRoleService.list(
				        new QueryWrapper<SysUserRole>()
                        .lambda()
                        .eq(SysUserRole::getUserId,userId)
		        ).stream()
                .map(sysUserRole ->sysUserRole.getRoleId())
                .collect(Collectors.toList());

		user.setRoleIdList(roleIdList);
		return R.ok().put("user", user);
	}
	
	/**
	 * 保存用户
	 */
	@SysLog("保存用户")
	@RequestMapping("/save")
	@RequiresPermissions("sys:user:save")
	public R save(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user);
		
		user.setCreateUserId(getUserId());
		sysUserService.saveUserRole(user);
		
		return R.ok();
	}
	
	/**
	 * 修改用户
	 */
	@SysLog("修改用户")
	@RequestMapping("/update")
	@RequiresPermissions("sys:user:update")
	public R update(@RequestBody SysUser user){
		ValidatorUtils.validateEntity(user);
		
		user.setCreateUserId(getUserId());
		sysUserService.updateUserRole(user);
		
		return R.ok();
	}
	
	/**
	 * 删除用户
	 */
	@SysLog("删除用户")
	@RequestMapping("/delete")
	@RequiresPermissions("sys:user:delete")
	public R delete(@RequestBody Long[] userIds){
		if(ArrayUtils.contains(userIds, 1L)){
			return R.error("系统管理员不能删除");
		}
		
		if(ArrayUtils.contains(userIds, getUserId())){
			return R.error("当前用户不能删除");
		}
		sysUserService.removeByIds(Arrays.asList(userIds));
		return R.ok();
	}
}
