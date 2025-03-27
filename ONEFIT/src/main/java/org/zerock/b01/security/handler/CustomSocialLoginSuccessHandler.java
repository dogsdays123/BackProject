package org.zerock.b01.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.SavedRequest;
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

        SavedRequest savedRequest = (SavedRequest) request.getSession().getAttribute("SPRING_SECURITY_SAVED_REQUEST");
        String redirectUrl = (savedRequest != null) ? savedRequest.getRedirectUrl() : "/main"; // 이전 URL이 있으면 그곳으로, 없으면 /main

        MemberSecurityDTO memberSecurityDTO = (MemberSecurityDTO) authentication.getPrincipal();

        String encodedPw = memberSecurityDTO.getAPsw();

        //소셜 로그인이고 회원의 패스워드가 1111

        if(memberSecurityDTO.isASocial() &&
                (memberSecurityDTO.getAPsw().equals("1111") ||
                        passwordEncoder.matches("1111", memberSecurityDTO.getAPsw())))
        {
            log.info("Should Change Password");
            log.info("Redirect to Member Modify");
            response.sendRedirect(redirectUrl);

        } else {
            log.info("false");
            response.sendRedirect("/login");
        }
        log.info("------------------------------------------");
    }
}
