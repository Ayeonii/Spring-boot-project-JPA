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

import org.springframework.security.config.annotation.web.builders.WebSecurity;

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

        http.authorizeRequests()                                                    // 시큐리티 처리에 HttpServletRequest 이용
                .mvcMatchers("/", "/members/**", "/item/**", "/images/**")
                .permitAll()                                                        // permilAll을 통해 모든 사용자가 인증(로그인)없이 해당 경로에 접근할 수 있도록 설정.[메인, 회원관련url, 상품상세페이지, 상품이미지를 불러오는 경로]
                .mvcMatchers("/admin/**")
                .hasRole("ADMIN")                                                   // '/admin'으로 시작하는 경로는 해당 계정이 ADMIN Role일 경우에만 접근 가능하도록 설정.
                .anyRequest().authenticated();                                      // mvcMatchers를 통해 설정해준 경로를 제외한 나머지 경로들은 인증을 요구하도록 설정

        http.exceptionHandling()
                .authenticationEntryPoint(new CustomAuthenticationEntryPoint());    // 인증되지 않은 사용자가 리소스에 접근했을 때, 수행되는 핸들러 등록
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

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");     // static 디렉토리의 하위 파일은 인증을 무시하도록 설정.
    }

}
