package com.suke.czx.authentication.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.zhjg.common.autofull.util.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description //TODO $
 * @Date 23:11
 * @Author yzcheng90@qq.com
 **/
public class TokenAuthenticationFailHandler implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter writer = response.getWriter();
        writer.write(objectMapper.writeValueAsString(R.error(HttpStatus.UNAUTHORIZED.value(),authException.getMessage())));
    }
}
