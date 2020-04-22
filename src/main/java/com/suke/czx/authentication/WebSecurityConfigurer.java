package com.suke.czx.authentication;

import com.suke.czx.authentication.detail.CustomUserDetailsService;
import com.suke.czx.authentication.handler.CustomAuthenticationFailHandler;
import com.suke.czx.authentication.handler.CustomAuthenticationSuccessHandler;
import com.suke.czx.authentication.handler.CustomLogoutSuccessHandler;
import com.suke.czx.authentication.handler.TokenAuthenticationFailHandler;
import com.suke.czx.common.utils.Constant;
import com.suke.czx.config.AuthIgnoreConfig;
import com.suke.czx.interceptor.AuthenticationTokenFilter;
import com.suke.czx.interceptor.ValidateCodeFilter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import java.util.List;

/**
 * @Description //TODO $
 * @Date 20:53
 * @Author yzcheng90@qq.com
 **/
@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AuthIgnoreConfig authIgnoreConfig;

    @SneakyThrows
    @Override
    protected void configure(HttpSecurity http) {
        List<String> permitAll = authIgnoreConfig.getIgnoreUrls();
        permitAll.add("/actuator/**");
        permitAll.add("/error");
        permitAll.add("/v2/**");
        permitAll.add(Constant.TOKEN_ENTRY_POINT_URL);
        String[] urls = permitAll.stream().distinct().toArray(String[]::new);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry = http.authorizeRequests();
        registry.antMatchers(urls).permitAll().anyRequest().authenticated().and().csrf().disable();
        http
            // 基于token，所以不需要session
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .headers().frameOptions().disable()
            .and()
            .formLogin()
            .loginProcessingUrl(Constant.TOKEN_ENTRY_POINT_URL)
            .successHandler(authenticationSuccessHandler())
            .failureHandler(authenticationFailureHandler())
            .and()
            .logout()
            .logoutUrl(Constant.TOKEN_LOGOUT_URL)
            .addLogoutHandler(logoutHandler())
            .logoutSuccessUrl("/sys/logout")
            .permitAll()
            .and()
            .exceptionHandling()
            .authenticationEntryPoint(new TokenAuthenticationFailHandler())
            .and()
             // 如果不用验证码，注释这个过滤器即可
            .addFilterBefore(new ValidateCodeFilter(redisTemplate,authenticationFailureHandler()),UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(new AuthenticationTokenFilter(authenticationManagerBean(),redisTemplate,customUserDetailsService), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
            .userDetailsService(customUserDetailsService)
            .passwordEncoder(passwordEncoder());
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(){
        return new CustomAuthenticationFailHandler();
    }

    @Bean
    public LogoutHandler logoutHandler(){
        return new CustomLogoutSuccessHandler();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(){
        return new CustomAuthenticationSuccessHandler();
    }

    @Bean
    @Override
    @SneakyThrows
    public AuthenticationManager authenticationManagerBean() {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/css/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
