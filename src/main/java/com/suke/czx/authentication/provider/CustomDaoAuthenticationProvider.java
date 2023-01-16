package com.suke.czx.authentication.provider;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author czx
 * @title: CustomDaoAuthenticationProvider
 * @projectName zhjg
 * @description: TODO
 * @date 2020/5/1515:26
 */
public class CustomDaoAuthenticationProvider extends DaoAuthenticationProvider {

    @Override
    public void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) {
        if (authentication.getCredentials() == null) {
            throw new BadCredentialsException("认证错误");
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!this.getPasswordEncoder().matches(presentedPassword, userDetails.getPassword())) {
                throw new BadCredentialsException("帐号或密码错误");
            }
        }
    }
}
