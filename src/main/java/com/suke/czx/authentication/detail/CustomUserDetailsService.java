package com.suke.czx.authentication.detail;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @Description //TODO $
 * @Date 21:09
 * @Author yzcheng90@qq.com
 **/
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

//    @Autowired
//    private PermissionsService permissionsService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);
    }

    private UserDetails getDetail(SysUser sysUser) {
//        Set<String> permissions = permissionsService.getUserPermissions(sysUser.getUserId());
        Set<String> permissions = new HashSet<>();
        String[] roles = new String[0];
        if (CollUtil.isNotEmpty(permissions)) {
            roles = permissions.stream().map(role -> "ROLE_" + role).toArray(String[]::new);
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        CustomUserDetailsUser customUserDetailsUser = new CustomUserDetailsUser(sysUser.getUserId(), sysUser.getUsername(), sysUser.getPassword(), authorities);
        return customUserDetailsUser;
    }
}
