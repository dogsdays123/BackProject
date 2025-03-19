package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

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

                // token.getPrincipal()이 MemberSecurityDTO 타입이라면, 이를 MemberSecurityDTO로 캐스팅
                MemberSecurityDTO principal = (MemberSecurityDTO) token.getPrincipal();
                String username = principal.getAllId(); // MemberSecurityDTO에서 사용자 이름 가져오기

                // 일반 로그인 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(username);
                log.info("##### 일반 로그인 사용자 정보: " + all_memberDTO);
            }

            if(all_memberDTO != null) {
                model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가
            } else{
                model.addAttribute("all_memberDTO", null);
            }
            model.addAttribute("sidebar", false);
        }
    }

    @GetMapping("/main")
    public void main(){
        log.info("main");
    }

    @GetMapping("/login")
    public void login() {
        log.info("login");
    }

    @PostMapping("/join")
    public String joinPOST(All_MemberDTO all_memberDTO, RedirectAttributes redirectAttributes) {
        log.info("join post........");
        log.info(all_memberDTO);

        try {
            all_memberService.join(all_memberDTO);
        } catch (All_MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "allId");
            return "redirect:/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/login";
    }
}
