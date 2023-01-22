package com.suke.czx.authentication;

import com.suke.czx.authentication.detail.CustomUserDetailsService;
import com.suke.czx.authentication.handler.CustomAuthenticationFailHandler;
import com.suke.czx.authentication.handler.CustomAuthenticationSuccessHandler;
import com.suke.czx.authentication.handler.CustomLogoutSuccessHandler;
import com.suke.czx.authentication.handler.TokenAuthenticationFailHandler;
import com.suke.czx.authentication.provider.CustomDaoAuthenticationProvider;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.config.AuthIgnoreConfig;
import com.suke.czx.interceptor.AuthenticationTokenFilter;
import com.suke.czx.interceptor.ValidateCodeFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

/**
 * @Description //TODO $
 * @Date 20:53
 * @Author yzcheng90@qq.com
 **/
@Slf4j
@EnableWebSecurity
public class SecurityConfigurer {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthIgnoreConfig authIgnoreConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        List<String> permitAll = authIgnoreConfig.getIgnoreUrls();
        permitAll.add("/error");
        permitAll.add("/v3/**");
        permitAll.add("/swagger-ui/**");
        permitAll.add("/swagger-resources/**");
        permitAll.add(Constant.TOKEN_ENTRY_POINT_URL);
        permitAll.add(Constant.TOKEN_LOGOUT_URL);
        String[] urls = permitAll.stream().distinct().toArray(String[]::new);

        // 基于 token，不需要 csrf
        http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.OPTIONS,"/**").permitAll();
        // 跨域配置
        http.cors().configurationSource(corsConfigurationSource());

        // 基于 token，不需要 session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        // 权限
        http.authorizeRequests(authorize ->
                // 开放权限
                authorize.antMatchers(urls).permitAll()
                // 其他地址的访问均需验证权限
                .anyRequest().authenticated());
        // 设置登录URL
        http.formLogin()
                .loginProcessingUrl(Constant.TOKEN_ENTRY_POINT_URL)
                .successHandler(authenticationSuccessHandler())
                .failureHandler(authenticationFailureHandler());
        // 设置退出URL
        http.logout()
                .logoutUrl(Constant.TOKEN_LOGOUT_URL)
                .logoutSuccessUrl("/sys/logout")
                .addLogoutHandler(logoutHandler());
        // 如果不用验证码，注释这个过滤器即可
        http.addFilterBefore(new ValidateCodeFilter(redisTemplate, authenticationFailureHandler()), UsernamePasswordAuthenticationFilter.class);
        // token 验证过滤器
        http.addFilterBefore(new AuthenticationTokenFilter(authenticationManager(), authIgnoreConfig,redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // 认证异常处理
        http.exceptionHandling().authenticationEntryPoint(new TokenAuthenticationFailHandler());
        // 用户管理service
        http.userDetailsService(userDetailsService());
        return http.build();
    }

    // 解决跨域
    public CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // 开放静态资源权限
        return web -> web.ignoring().antMatchers("/actuator/**", "/css/**", "/error");
    }

    @Bean
    public CustomDaoAuthenticationProvider authenticationProvider() {
        CustomDaoAuthenticationProvider authenticationProvider = new CustomDaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        authenticationProvider.setUserDetailsService(userDetailsService());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(authenticationProvider()));
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailHandler();
    }

    @Bean
    public LogoutHandler logoutHandler() {
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
