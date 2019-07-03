package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.common.exception.RRException;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.mapper.SysUserMapper;
import com.suke.czx.modules.sys.mapper.SysUserRoleMapper;
import com.suke.czx.modules.sys.service.SysRoleService;
import com.suke.czx.modules.sys.service.SysUserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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

	@Override
	public List<String> queryAllPerms(Long userId) {
		return sysUserMapper.queryAllPerms(userId);
	}


	@Override
	@Transactional
	public void saveUserRole(SysUser user) {
		user.setCreateTime(new Date());
		//sha256加密
		String salt = RandomStringUtils.randomAlphanumeric(20);
		user.setPassword(new Sha256Hash(user.getPassword(), salt).toHex());
		user.setSalt(salt);
		sysUserMapper.insert(user);
		
		//检查角色是否越权
		checkRole(user);

		sysUserRoleMapper.deleteById(user.getUserId());

		//保存用户与角色关系
		saveUserRoleList(user);
	}

	@Override
	@Transactional
	public void updateUserRole(SysUser user) {
		if(StringUtils.isBlank(user.getPassword())){
			user.setPassword(null);
		}else{
			user.setPassword(new Sha256Hash(user.getPassword(), user.getSalt()).toHex());
		}
		baseMapper.updateById(user);
		
		//检查角色是否越权
		checkRole(user);

		sysUserRoleMapper.deleteById(user.getUserId());

		//保存用户与角色关系
		saveUserRoleList(user);
	}


	@Override
	public int updatePassword(Long userId, String password, String newPassword) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", userId);
		map.put("password", password);
		map.put("newPassword", newPassword);
		return sysUserMapper.updatePassword(map);
	}

	public void saveUserRoleList(SysUser user){
		if(user.getRoleIdList() != null && user.getRoleIdList().size() != 0){
			Map<String, Object> map = new HashMap<>();
			map.put("userId", user.getUserId());
			map.put("roleIdList", user.getRoleIdList());
			sysUserMapper.saveUserRole(map);
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
