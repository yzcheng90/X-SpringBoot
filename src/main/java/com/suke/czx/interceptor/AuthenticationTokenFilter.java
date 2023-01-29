package com.suke.czx.interceptor;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.authentication.detail.CustomUserDetailsService;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.common.utils.SpringContextUtils;
import com.suke.czx.config.AuthIgnoreConfig;
import com.suke.zhjg.common.autofull.util.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private AuthIgnoreConfig authIgnoreConfig;
    private ObjectMapper objectMapper = new ObjectMapper();

    public AuthenticationTokenFilter(AuthenticationManager authenticationManager, AuthIgnoreConfig authIgnoreConfig,RedisTemplate template) {
        super(authenticationManager);
        this.redisTemplate = template;
        this.authIgnoreConfig = authIgnoreConfig;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(Constant.TOKEN);
        if (StrUtil.isBlank(token) || StrUtil.equals(token, "null")) {
            token = request.getParameter(Constant.TOKEN);
        }

        if (StrUtil.isNotBlank(token) && !StrUtil.equals(token, "null")) {
            final String requestURI = request.getRequestURI();
            if(!authIgnoreConfig.isContains(requestURI)){
                Object userInfo = redisTemplate.opsForValue().get(Constant.AUTHENTICATION_TOKEN + token);
                if (ObjectUtil.isNull(userInfo)) {
                    writer(response, "无效token");
                    return;
                }
                String user[] = userInfo.toString().split(",");
                if (user == null || user.length != 2) {
                    writer(response, "无效token");
                    return;
                }

                String userId = user[0];
                CustomUserDetailsService customUserDetailsService = SpringContextUtils.getBean(CustomUserDetailsService.class);
                UserDetails userDetails = customUserDetailsService.loadUserByUserId(userId);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }


    @SneakyThrows
    public void writer(HttpServletResponse response, String msg) {
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().write(objectMapper.writeValueAsString(R.error(HttpServletResponse.SC_UNAUTHORIZED, msg)));
    }
}
