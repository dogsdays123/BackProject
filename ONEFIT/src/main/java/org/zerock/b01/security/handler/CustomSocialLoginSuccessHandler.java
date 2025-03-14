package org.zerock.b01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.zerock.b01.security.dto.MemberSecurityDTO;

import java.io.IOException;

@Log4j2
@RequiredArgsConstructor
public class CustomSocialLoginSuccessHandler implements AuthenticationSuccessHandler {
    private final PasswordEncoder passwordEncoder;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException{
        log.info("------------------------------------------");
        log.info("CustomLoginSuccessHandler onAuthenticationSuccess ..............");
        log.info(authentication.getPrincipal());


        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String encodedPw = memberSecurityDTO.getA_psw();

        //소셜 로그인이고 회원의 패스워드가 1111

        if(memberSecurityDTO.isA_social() &&
                (memberSecurityDTO.getA_psw().equals("1111") ||
                        passwordEncoder.matches("1111", memberSecurityDTO.getA_psw())))
        {
            log.info("Should Change Password");
            log.info("Redirect to Member Modify");
            response.sendRedirect("/main"); //이 부분 진도 안나가서 임의로 수정
            //원래는 /member/modify 이거임

        } else {
            response.sendRedirect("/main");
        }
    }
}
