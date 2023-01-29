package com.suke.czx.common.utils;

import com.suke.czx.authentication.detail.CustomUserDetailsUser;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description //TODO $
 * @Date 10:20
 * @Author yzcheng90@qq.com
 **/
@UtilityClass
public class UserUtil {

    public CustomUserDetailsUser getUser() {
        Object object = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(object != null){
            return (CustomUserDetailsUser) object;
        }
        return null;
    }

    @SneakyThrows
    public String getUserId() {
        return getUser() == null ? null :getUser().getUserId();
    }

}
