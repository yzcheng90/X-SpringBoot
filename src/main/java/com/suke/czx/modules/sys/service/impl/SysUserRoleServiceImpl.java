package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysUserRole;
import com.suke.czx.modules.sys.mapper.SysUserRoleMapper;
import com.suke.czx.modules.sys.service.SysUserRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


/**
 * 用户与角色对应关系
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2019年4月17日
 */
@Service
@AllArgsConstructor
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

}
