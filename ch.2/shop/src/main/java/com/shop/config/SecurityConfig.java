package com.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.
        EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shop.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/*
4.1 스프링 시큐리티 소개
* 애플리케이션의 보안에서 중요한 두 가지 영역은 ‘인증’과 ‘인가’입니다.
* 인증 : 사용자가 해당 작업을 수행할 수 있는 주체인지 확인. ex) 로그인 : 인증절차
* 인가 : 인증과정 이후에, 해당 사용자가 접근하는 URL에 인가된 회원(권한이 있는 회원)인지를 검사.
* */

@Configuration
@EnableWebSecurity
public class SecurityConfig extends  WebSecurityConfigurerAdapter {

    @Autowired
    MemberService memberService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                .loginPage("/members/login")        // 로그인페이지 url tjfwjd
                .defaultSuccessUrl("/")             // 로그인 성공 시 이동할 url
                .usernameParameter("email")         // 로그인 시 사용할 파라미터 이름
                .failureUrl("/members/login/error") // 로그인 실패 시 이동할 url
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/members/logout")) //로그아웃 url
                .logoutSuccessUrl("/");             // 로그아웃 성공 시 이동할 url
    }

    @Bean
    public PasswordEncoder passwordEncoder() { //비번을 DB에 그대로 저장하면, 해킹당할 경우 그대로 정보 노출. -> 암호화
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //Sring Security에서 인증은 AuthenticationManager를 통해 이루어짐.
        //userDetailService를 구현하고 있는 객체로 memberService를 지정해주며, 비밀번호 암호화를 위해 passwordEncoder를 지정함.
        auth.userDetailsService(memberService)
                .passwordEncoder(passwordEncoder());

    }
}
