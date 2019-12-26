package com.suke.czx.common.utils;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @Description //TODO $
 * @Date 21:29
 * @Author yzcheng90@qq.com
 **/
@Slf4j
@UtilityClass
public class MainUtils {


    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String en = encoder.encode("admin");
        log.info("=======en:{}",en);
        en = encoder.encode("admin");
        log.info("=======en:{}",en);
        en = encoder.encode("admin");
        log.info("=======en:{}",en);
    }
}
