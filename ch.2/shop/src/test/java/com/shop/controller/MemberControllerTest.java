package com.shop.controller;

import com.shop.dto.MemberFormDto;
import com.shop.entity.Member;
import com.shop.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@AutoConfigureMockMvc //MockMvc 테스트를 위해 @AutoConfigureMockMvc 어노테이션 선언
@Transactional
@TestPropertySource(locations="classpath:application-test.properties")
class MemberControllerTest {
    @Autowired
    private MemberService memberService;

    @Autowired
    private MockMvc mockMvc; // MockMvc 클래스를 이용해 실제 객체와 비슷하지만 테스트에 필요한 기능만 가지는 가짜 객체.
                             // MockMvc를 이용하면 웹 브라우저에서 요청하는 것처럼 테스트할 수 있음.
    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(String email, String password) { //로그인 예제 진행을 위해, 로그인 전 회원을 등록하는 메소드
        MemberFormDto memberFormDto = new MemberFormDto();
        memberFormDto.setEmail(email);
        memberFormDto.setName("홍길동");
        memberFormDto.setAddress("서울시 마포구 합정동");
        memberFormDto.setPassword(password);
        Member member = Member.createMember(memberFormDto, passwordEncoder);
        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("로그인 성공 테스트")
    public void loginSuccessTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin()
                        .userParameter("email")
                        .loginProcessingUrl("/members/login")               // 회원가입 메소드를 실행 후, 가입된 회원 정보로 로그인이 되는지 테스트 진행.
                        .user(email).password(password))                    // userParameter()를 이용하여 이메일을 아이디로 세팅하고, 로그인 URL에 요청.
                .andExpect(SecurityMockMvcResultMatchers.authenticated());  // 로그인이 성공하여 인증되었다면, 테스트코드 통과.
    }

    @Test
    @DisplayName("로그인 실패 테스트")
    public void loginFailTest() throws Exception {
        String email = "test@email.com";
        String password = "1234";
        this.createMember(email, password);
        mockMvc.perform(formLogin().userParameter("email")
                        .loginProcessingUrl("/members/login")
                        .user(email)
                        .password("12345"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}