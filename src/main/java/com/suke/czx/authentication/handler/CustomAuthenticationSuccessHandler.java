package com.suke.czx.authentication.handler;

import cn.hutool.core.util.CharsetUtil;
import cn.hutool.crypto.SecureUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.authentication.detail.CustomUserDetailsUser;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * @Description //TODO $
 * @Date 21:06
 * @Author yzcheng90@qq.com
 **/
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        String token;
        Long userId = 0l;
        if(authentication.getPrincipal() instanceof CustomUserDetailsUser){
            CustomUserDetailsUser userDetailsUser = (CustomUserDetailsUser) authentication.getPrincipal();
            token = SecureUtil.md5(userDetailsUser.getUsername() + System.currentTimeMillis());
            userId = userDetailsUser.getUserId();
        }else {
            token = SecureUtil.md5(String.valueOf(System.currentTimeMillis()));
        }
        redisTemplate.opsForValue().set(Constant.AUTHENTICATION_TOKEN + token,token,Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set(token,userId,Constant.TOKEN_EXPIRE, TimeUnit.SECONDS);

        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(R.ok().put(Constant.TOKEN,token)));
    }
}
