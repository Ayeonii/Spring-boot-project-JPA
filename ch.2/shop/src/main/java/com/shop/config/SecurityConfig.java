package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*
4.1 스프링 시큐리티 소개
* 애플리케이션의 보안에서 중요한 두 가지 영역은 ‘인증’과 ‘인가’입니다.
* 인증 : 사용자가 해당 작업을 수행할 수 있는 주체인지 확인. ex) 로그인 : 인증절차
* 인가 : 인증과정 이후에, 해당 사용자가 접근하는 URL에 인가된 회원(권한이 있는 회원)인지를 검사.
* */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    }

    @Bean
    public PasswordEncoder passwordEncoder() { //비번을 DB에 그대로 저장하면, 해킹당할 경우 그대로 정보 노출. -> 암호화
        return new BCryptPasswordEncoder();
    }
}
