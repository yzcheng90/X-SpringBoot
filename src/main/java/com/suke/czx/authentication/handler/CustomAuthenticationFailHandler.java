package com.suke.czx.authentication.handler;

import cn.hutool.core.util.CharsetUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.suke.czx.common.event.LoginLogEvent;
import com.suke.czx.common.utils.IPUtils;
import com.suke.czx.common.utils.SpringContextUtils;
import com.suke.czx.modules.sys.entity.SysLoginLog;
import com.suke.zhjg.common.autofull.util.R;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @Description //TODO $
 * @Date 21:05
 * @Author yzcheng90@qq.com
 **/
@Slf4j
@Component
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception){

        final String username = request.getParameter("username");
        SysLoginLog loginLog = new SysLoginLog();
        loginLog.setOptionIp(IPUtils.getIpAddr(request));
        loginLog.setOptionName("用户登录失败");
        loginLog.setOptionTerminal(request.getHeader("User-Agent"));
        loginLog.setUsername(username);
        SpringContextUtils.publishEvent(new LoginLogEvent(loginLog));

        response.setCharacterEncoding(CharsetUtil.UTF_8);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        PrintWriter printWriter = response.getWriter();
        printWriter.append(objectMapper.writeValueAsString(R.error(exception.getMessage())));
    }
}
