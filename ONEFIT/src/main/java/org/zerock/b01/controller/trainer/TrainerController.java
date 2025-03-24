package org.zerock.b01.controller.trainer;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.trainerService.TrainerService;

@Controller
@RequestMapping("/trainer")
@Log4j2
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;

    @ModelAttribute
    public void Profile(All_MemberDTO all_memberDTO, Model model, Authentication authentication, HttpServletRequest request) {
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
        String currentUrl = request.getRequestURI();
        // URL에 따라서 분기
        if (currentUrl.contains("/member")) {
            model.addAttribute("sidebar", true);
        } else{
            model.addAttribute("sidebar", false);
        }
        log.info("회원전역@@@@@@@@@" + all_memberDTO);
    }

    @GetMapping("/trainer_list")
    public void trainer_list() {
        log.info("trainer_list");
    }

    @GetMapping("/trainer_register")
    public void trainer_register_GET() {
        log.info("trainer_register_GET");
    }

    @PostMapping("/trainer_register")
    public String trainer_register_POST(@Valid TrainerDTO trainerDTO, BindingResult bindingResult) {
        log.info("trainer_register_POST");

        if (bindingResult.hasErrors()) {
            log.info("trainer_register_POST_ERROR");
            log.info(bindingResult.getAllErrors());
            return "redirect:/trainer/trainer_register";
        }

        log.info(trainerDTO);

        Long trainer_id = trainerService.registerTrainer(trainerDTO);
        return "redirect:/trainer/trainer_list";
    }

    @Operation
    @GetMapping({"/trainer_view", "/trainer_modify"})
    public void trainer_view(Long tid, PageRequestDTO pageRequestDTO, Model model) {
        log.info("trainer_view or trainer_modify");
        log.info(tid);
        TrainerViewDTO trainerViewDTO = trainerService.viewOne(tid);

        log.info(trainerViewDTO);
        model.addAttribute("dto", trainerViewDTO);
    }

    @PostMapping("/trainer_modify")
    public String trainer_modify_POST(@Valid TrainerDTO trainerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("trainer_modify_POST");

        if (bindingResult.hasErrors()) {
            log.info("trainer_modify_POST_ERROR");
            log.info(bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tid", trainerDTO.getTrainerId());
            return "redirect:/trainer/trainer_modify";
        }

        log.info(trainerDTO);

        Long trainer_id = trainerService.registerTrainer(trainerDTO);
        redirectAttributes.addAttribute("tid", trainerDTO.getTrainerId());
        return "redirect:/trainer/trainer_view";
    }
}
