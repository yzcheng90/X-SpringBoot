package com.suke.czx.modules.app.annotation;

import java.lang.annotation.*;

/**
 * app版本校验
 * @author czx
 * @email object_czx@163.com
 * @date 2017/9/23 14:30
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VersionCheck {
}
