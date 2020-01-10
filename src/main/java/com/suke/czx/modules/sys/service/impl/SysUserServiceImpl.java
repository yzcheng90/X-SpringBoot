package com.suke.czx.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.entity.SysUserRole;
import com.suke.czx.modules.sys.mapper.SysUserMapper;
import com.suke.czx.modules.sys.mapper.SysUserRoleMapper;
import com.suke.czx.modules.sys.service.SysRoleService;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;



/**
 * 系统用户
 * 
 * @author czx
 * @email object_czx@163.com
 * @date 2016年9月18日 上午9:46:09
 */

@Lazy
@Service
@AllArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {
	private final SysUserMapper sysUserMapper;
	private final SysUserRoleMapper sysUserRoleMapper;
	private final SysRoleService sysRoleService;
	private final PasswordEncoder passwordEncoder;

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}


	@Override
	@Transactional
	public void saveUserRole(SysUser user) {
		user.setCreateTime(new Date());
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setSalt(salt);
		sysUserMapper.insert(user);
		//检查角色是否越权
		checkRole(user);
		sysUserRoleMapper.delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId,user.getUserId()));
		//保存用户与角色关系
		saveUserRoleList(user);
	}

	@Override
	@Transactional
	public void updateUserRole(SysUser user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		baseMapper.updateById(user);
		//检查角色是否越权
		checkRole(user);
		sysUserRoleMapper.delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId,user.getUserId()));
		//保存用户与角色关系
		saveUserRoleList(user);
	}


	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		SysUser sysUser = new SysUser();
		sysUser.setUserId(userId);
		sysUser.setPassword(newPassword);
		return sysUserMapper.updateById(sysUser);
	}

	public void saveUserRoleList(SysUser user){
		if(CollUtil.isNotEmpty(user.getRoleIdList())){
			user.getRoleIdList().forEach(roleId ->{
				SysUserRole userRole = new SysUserRole();
				userRole.setUserId(user.getUserId());
				userRole.setRoleId(roleId);
				sysUserRoleMapper.insert(userRole);
			});
		}
	}
	
	/**
	 * 检查角色是否越权
	 */
	private void checkRole(SysUser user){
		//如果不是超级管理员，则需要判断用户的角色是否自己创建
		if(user.getCreateUserId() == Constant.SUPER_ADMIN){
			return ;
		}
		
		//查询用户创建的角色列表
		List<Long> roleIdList = sysRoleService.queryRoleIdList(user.getCreateUserId());
		
		//判断是否越权
		if(!roleIdList.containsAll(user.getRoleIdList())){
			throw new RRException("新增用户所选角色，不是本人创建");
		}
	}
}
