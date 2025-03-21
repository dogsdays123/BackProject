package org.zerock.b01.controller.member;

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
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class All_MemberController {

    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;

    @ModelAttribute
    public void Profile(All_MemberDTO all_memberDTO, Model model, Authentication authentication) {
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
        }

        //유저정보(일반, 개인) 전역에 갖고오기
        User_MemberDTO user_MemberDTO = member_Set_Type_Service.userRead(all_memberDTO.getAllId());
        Business_MemberDTO business_memberDTO = member_Set_Type_Service.BusinessRead(all_memberDTO.getAllId());

        //유저정보(일반Default)가 존재할 때
        if (all_memberDTO != null) {
            model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가

            //유저정보(개인User)가 있을 때
            if (user_MemberDTO != null) {
                model.addAttribute("user_memberDTO", user_MemberDTO);
                model.addAttribute("memberTypeAgree", "user");
                log.info("개인회원전역@@@@@@@@@" + user_MemberDTO);

                //유저정보(기업Business)가 있을 때
            } else if (business_memberDTO != null) {
                model.addAttribute("business_memberDTO", business_memberDTO);
                model.addAttribute("memberTypeAgree", "business");
                log.info("기업회원전역@@@@@@@@@" + business_memberDTO);

                //유저정보(개인User)가 없을 때
            } else {
                model.addAttribute("memberTypeAgree", "default");
                log.info("일반회원정보없음@@@@@@@@@");
            }

            //유저정보(일반Default)가 없을 때
        } else {
            model.addAttribute("all_memberDTO", null);
        }
        model.addAttribute("sidebar", true);
        log.info("회원전역@@@@@@@@@" + all_memberDTO);
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify")
    public String modifyPOST(All_MemberDTO all_memberDTO, User_MemberDTO userMemberDTO, RedirectAttributes redirectAttributes) {
        log.info("modify post........");
        log.info("allId@@@@" + all_memberDTO.getAllId());
        all_memberService.modify(all_memberDTO);
        return "redirect:/member/my_default_page";
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
    @GetMapping("/my_user_page")
    public void my_user_page() {
        log.info("my_user_page");
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

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/finishedChange")
    public void finishedChange() {
        log.info("finishedChange");
    }

    //타입 부여start
    // 유저
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/set_type")
    public void getSet_type() {
        log.info("set_type get");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set_type")
    public String PostSet_type(@Valid User_MemberDTO user_MemberDTO, String allId
            , RedirectAttributes redirectAttributes
                               , Model model
            , BindingResult bindingResult) throws BindException {
        log.info("set_type post.......");
        user_MemberDTO.setAllId(allId);
        log.info("user_Member@@@@" + user_MemberDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        //확인 및 적용
        Long a = member_Set_Type_Service.UserRegister(user_MemberDTO);
        log.info("user_Member Id @@@@" + a);

        //피니시 창 반영
        return "redirect:/member/finishedChange";
    }

    // 기업
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/set_type_b")
    public void getSet_type_b() {
        log.info("set_type_b get");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/set_type_b")
    public String PostSet_type_b(@Valid Business_MemberDTO business_memberDTO, String allId
            , RedirectAttributes redirectAttributes
                                 , Model model
            , BindingResult bindingResult) throws BindException {
        log.info("set_type_b post.......");
        business_memberDTO.setAllId(allId);
        log.info("user_Member@@@@" + business_memberDTO);

        if (bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Long a = member_Set_Type_Service.BusinessRegister(business_memberDTO);
        log.info("business_Member Id @@@@" + a);

        return "redirect:/member/finishedChange";
    }
    //타입 부여end

}
