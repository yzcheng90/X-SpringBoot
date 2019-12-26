package com.suke.czx.interceptor;

import cn.hutool.core.util.StrUtil;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.serialization.RedisTokenStoreSerializationStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Description //TODO $
 * @Date 22:23
 * @Author yzcheng90@qq.com
 **/
@Slf4j
public class AuthenticationTokenFilter extends BasicAuthenticationFilter {

    private RedisTemplate redisTemplate;
    private RedisTokenStoreSerializationStrategy serialization;

    public AuthenticationTokenFilter(AuthenticationManager authenticationManager,RedisTemplate template,RedisTokenStoreSerializationStrategy serialization) {
        super(authenticationManager);
        this.redisTemplate = template;
        this.serialization = serialization;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(Constant.TOKEN);
        if(StrUtil.isNotBlank(token)){
            byte[] key = serialization.serialize(token);
            byte[] value = redisTemplate.getConnectionFactory().getConnection().get(key);
            Authentication authentication = serialization.deserialize(value,Authentication.class);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }
}
