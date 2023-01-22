package com.suke.czx.modules.sys.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suke.czx.modules.sys.entity.SysMenuNew;
import com.suke.czx.modules.sys.vo.SysMenuNewVO;

import java.util.List;


/**
 * 菜单管理
 *
 * @author czx
 * @email object_czx@163.com
 */
public interface SysMenuNewService extends IService<SysMenuNew> {

	List<SysMenuNewVO> getUserMenu();
}
