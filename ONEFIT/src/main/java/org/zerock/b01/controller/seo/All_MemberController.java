package org.zerock.b01.controller.seo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.service.All_MemberService;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class All_MemberController {

    private final All_MemberService all_memberService;

    @ModelAttribute
    public void allMemberProfile(All_MemberDTO all_memberDTO, Model model, Authentication authentication) {
        // 인증 정보가 없다면 null 설정
        if (authentication == null) {
            log.info("###### 인증 정보 없음");
            model.addAttribute("all_memberDTO", null);
        } else {
            // 소셜 로그인인 경우 OAuth2AuthenticationToken 처리
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                OAuth2AuthenticatedPrincipal principal = token.getPrincipal();

                // OAuth2 인증을 통해 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(principal.getName());
                log.info("##### 소셜 로그인 사용자 정보: " + all_memberDTO);
            }
            // 일반 로그인인 경우 UsernamePasswordAuthenticationToken 처리
            else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
                String username = (String) token.getPrincipal(); // 일반 로그인에서 username 가져오기

                // 일반 로그인 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(username);
                log.info("##### 일반 로그인 사용자 정보: " + all_memberDTO);
            }

            model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가
        }
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_board")
    public void my_board() {
        log.info("my_board");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_default_page")
    public void my_default_page() {
        log.info("my_default_page");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_business_page")
    public void my_business_page() {
        log.info("my_business_page");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_info_page")
    public void my_info_page() {
        log.info("my_info_page");
    }
}
