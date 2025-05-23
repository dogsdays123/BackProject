package org.zerock.b01.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageRequestDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageResponseDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.boardService.NoticeBoardService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.recruitService.RecruitService;
import org.zerock.b01.service.trainerService.TrainerService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;
    private final TrainerService trainerService;
    private final NoticeBoardService noticeBoardService;

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

    private final RecruitService recruitService;

    @GetMapping("/main")
    public void main(Long recruitId, RecruitDTO recruitDTO,All_MemberDTO all_memberDTO,Authentication authentication,TrainerPageRequestDTO trainerPageRequestDTO, PageRequestDTO pageRequestDTO, Model model) {

        if (authentication == null) {
            log.info("MAIN ###### 인증 정보 없음");
            model.addAttribute("all_memberDTO", null);
        } else {
            // 소셜 로그인인 경우 OAuth2AuthenticationToken 처리
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                OAuth2AuthenticatedPrincipal principal = token.getPrincipal();

                // OAuth2 인증을 통해 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(principal.getName());
                log.info("MAIN ##### 소셜 로그인 사용자 정보: " + all_memberDTO);
            }
            // 일반 로그인인 경우 UsernamePasswordAuthenticationToken 처리
            else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                // token.getPrincipal()이 MemberSecurityDTO 타입이라면, 이를 MemberSecurityDTO로 캐스팅
                MemberSecurityDTO principal = (MemberSecurityDTO) token.getPrincipal();
                String username = principal.getAllId(); // MemberSecurityDTO에서 사용자 이름 가져오기

                // 일반 로그인 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(username);
                log.info("MAIN ##### 일반 로그인 사용자 정보: " + all_memberDTO);
            }
        }
        model.addAttribute("all_memberDTO", all_memberDTO);

        PageResponseDTO<RecruitDTO> responseDTO = recruitService.list1(pageRequestDTO);
        List<RecruitDTO> limitedList = Optional.ofNullable(responseDTO.getDtoList())
                .orElse(Collections.emptyList()) // null이면 빈 리스트 반환
                .stream()
                .limit(8)  // 처음 8개 항목만 가져옴
                .collect(Collectors.toList());


// 슬라이드를 4개씩 묶기
        int groupSize = 4;
        List<List<RecruitDTO>> slides = new ArrayList<>();

        for (int i = 0; i < limitedList.size(); i += groupSize) {
            int end = Math.min(i + groupSize, limitedList.size());
            slides.add(limitedList.subList(i, end));
        }

        model.addAttribute("slides", slides);

        TrainerPageResponseDTO<TrainerViewDTO> trainerPageResponseDTO = trainerService.list(trainerPageRequestDTO);
        model.addAttribute("trainerPage", trainerPageResponseDTO);
        log.info("$$$$$$: " + trainerPageResponseDTO);

        PageResponseDTO<BoardListReplyCountDTO> responseBoardDTO =
                noticeBoardService.listWithNoticeReplyCount(pageRequestDTO);
        model.addAttribute("responseBoardDTO", responseBoardDTO);

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

    @PostMapping("/checkId")
    @ResponseBody
    public Map<String, Object> checkId(@RequestParam("allId") String allId, Model model) {
        Map<String, Object> response = new HashMap<>();

        // 아이디 중복 여부 체크
        if (all_memberService.readOne(allId) != null) {
            response.put("isAvailable", false); // 아이디가 이미 존재하는 경우
            model.addAttribute("checkId", false);
        } else {
            response.put("isAvailable", true);  // 아이디가 사용 가능한 경우
            model.addAttribute("checkId", true);
        }

        log.info("Id체크" + allId);

        return response; // JSON 형식으로 반환
    }

    @PostMapping("/checkEmail")
    @ResponseBody
    public Map<String, Object> checkEmail(@RequestParam("email") String email, Model model) {
        Map<String, Object> response = new HashMap<>();

        // 아이디 중복 여부 체크
        if (all_memberService.readOneForEmail(email) != null) {
            response.put("isAvailable", false); // 아이디가 이미 존재하는 경우
            model.addAttribute("checkEmail", false);
        } else {
            response.put("isAvailable", true);  // 아이디가 사용 가능한 경우
            model.addAttribute("checkEmail", true);
        }

        log.info("Id체크" + email);

        return response; // JSON 형식으로 반환
    }
}
