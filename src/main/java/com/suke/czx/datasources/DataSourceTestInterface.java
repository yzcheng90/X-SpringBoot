package com.suke.czx.datasources;

import com.suke.czx.modules.user.entity.UserEntity;

/**
 * Created by czx on 2018/3/15.
 */
public interface DataSourceTestInterface {

    UserEntity queryObject(Long userId);

    UserEntity queryObject2(Long userId);
}
