package com.suke.czx.authentication.handler;

import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.serialization.RedisTokenStoreSerializationStrategy;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 * @title: CustomLogoutSuccessHandler
 * @projectName x-springboot
 * @description: TODO
 * @date 2019/12/2415:12
 */
@Slf4j
@Component
public class CustomLogoutSuccessHandler implements LogoutHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisTokenStoreSerializationStrategy redisTokenStoreSerializationStrategy;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(Constant.TOKEN);
        redisTemplate.delete(Constant.AUTHENTICATION_TOKEN);
        byte[] authenticationKey = redisTokenStoreSerializationStrategy.serialize(token);
        RedisConnection conn = redisTemplate.getConnectionFactory().getConnection();
        try{
            conn.openPipeline();
            conn.get(authenticationKey);
            conn.del(authenticationKey);
            conn.closePipeline();
        }finally {
            conn.close();
        }
    }
}
