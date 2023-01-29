package com.suke.czx.common.annotation;

import java.lang.annotation.*;

/**
 * 忽略XSS过滤
 * @author czx
 * @email yzcheng90@qq.com
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface XssIgnore {

}
