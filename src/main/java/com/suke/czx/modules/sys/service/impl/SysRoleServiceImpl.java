package com.suke.czx.modules.sys.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suke.czx.modules.sys.entity.SysRole;
import com.suke.czx.modules.sys.entity.SysRoleMenu;
import com.suke.czx.modules.sys.mapper.SysRoleMapper;
import com.suke.czx.modules.sys.service.SysRoleMenuService;
import com.suke.czx.modules.sys.service.SysRoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 角色
 *
 * @author czx
 * @email object_czx@163.com
 * @date 2019年4月17日
 */
@Service
@AllArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuService sysRoleMenuService;

    @Override
    @Transactional
    public void saveRoleMenu(SysRole role) {
        role.setCreateTime(new Date());
        sysRoleMapper.insert(role);
        if (CollUtil.isNotEmpty(role.getMenuIdList())) {
            List<SysRoleMenu> sysRoleMenus = role.getMenuIdList().stream().map(id -> {
                SysRoleMenu menu = new SysRoleMenu();
                menu.setMenuId(id);
                menu.setRoleId(role.getRoleId());
                return menu;
            }).collect(Collectors.toList());
            sysRoleMenuService.saveBatch(sysRoleMenus);
        }
    }

    @Override
    @Transactional
    public void updateRoleMenu(SysRole role) {
        sysRoleMapper.updateById(role);
        if (CollUtil.isNotEmpty(role.getMenuIdList())) {
            sysRoleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, role.getRoleId()));
            List<SysRoleMenu> sysRoleMenus = role.getMenuIdList().stream().map(id -> {
                SysRoleMenu menu = new SysRoleMenu();
                menu.setMenuId(id);
                menu.setRoleId(role.getRoleId());
                return menu;
            }).collect(Collectors.toList());
            sysRoleMenuService.saveBatch(sysRoleMenus);
        }
    }

    @Override
    public List<Long> queryRoleIdList(Long createUserId) {
        return sysRoleMapper.queryRoleIdList(createUserId);
    }


    @Override
    public void deleteBath(Long id) {
        baseMapper.deleteById(id);
        sysRoleMenuService.remove(Wrappers.<SysRoleMenu>query().lambda().eq(SysRoleMenu::getRoleId, id));
    }

}
