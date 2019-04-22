package com.suke.czx.modules.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.modules.sys.mapper.SysUserMapper;
import com.suke.czx.modules.sys.mapper.SysUserTokenMapper;
import com.suke.czx.modules.sys.entity.SysMenu;
import com.suke.czx.modules.sys.entity.SysUser;
import com.suke.czx.modules.sys.entity.SysUserToken;
import com.suke.czx.modules.sys.service.ShiroService;
import com.suke.czx.modules.sys.service.SysMenuService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.*;


@Lazy
@Service
@AllArgsConstructor
public class ShiroServiceImpl implements ShiroService {

    private final SysMenuService sysMenuService;
    private final SysUserMapper sysUserMapper;
    private final SysUserTokenMapper sysUserTokenMapper;

    @Override
    public Set<String> getUserPermissions(long userId) {
        List<String> permsList;

        //系统管理员，拥有最高权限
        if(userId == Constant.SUPER_ADMIN){
            List<SysMenu> menuList = sysMenuService.list();
            permsList = new ArrayList<>(menuList.size());
            for(SysMenu menu : menuList){
                permsList.add(menu.getPerms());
            }
        }else{
            permsList = sysUserMapper.queryAllPerms(userId);
        }
        //用户权限列表
        Set<String> permsSet = new HashSet<>();
        for(String perms : permsList){
            if(StringUtils.isBlank(perms)){
                continue;
            }
            permsSet.addAll(Arrays.asList(perms.trim().split(",")));
        }
        return permsSet;
    }

    @Override
    public SysUserToken queryByToken(String token) {
        QueryWrapper<SysUserToken> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("token",token);
        return sysUserTokenMapper.selectOne(queryWrapper);
    }

    @Override
    public SysUser queryUser(Long userId) {
        return sysUserMapper.selectById(userId);
    }
}
