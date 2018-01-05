package com.suke.czx.modules.app.annotation;

import java.lang.annotation.*;

/**
 * api接口，忽略Token验证
 * @author czx
 * @email yzcheng90@qq.com
 * @date 2017-03-23 15:44
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthIgnore {

}
