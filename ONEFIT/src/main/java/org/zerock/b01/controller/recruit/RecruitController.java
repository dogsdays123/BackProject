package org.zerock.b01.controller.recruit;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.recruitDTO.RecruitImageDTO;
import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.recruitService.RecruitService;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/recruit")
@Log4j2
@RequiredArgsConstructor
public class RecruitController {

    @Value("C:\\upload")
    private String uploadPath;

    private final RecruitService recruitService;

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
    //회원정보 가져오기


    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("recruit_list Get...............");

//        PageResponseDTO<RecruitDTO> responseDTO = recruitService.list(pageRequestDTO);
        PageResponseDTO<RecruitDTO> responseDTO = recruitService.list1(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);


    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost (@Valid RecruitDTO recruitDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("recruit_register Post...............");

        if(bindingResult.hasErrors()) {
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/recruit/register";
        }

        log.info(recruitDTO);

        Long recruitId = recruitService.register(recruitDTO);

        redirectAttributes.addFlashAttribute("result", recruitId);

        return "redirect:/recruit/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long recruitId, PageRequestDTO pageRequestDTO, Business_MemberDTO business_memberDTO, Model model, All_MemberDTO all_memberDTO) {
        log.info("recruit_read Get...............");

        RecruitDTO recruitDTO = recruitService.readOne(recruitId);

        log.info("File Names: {}", recruitDTO.getFileNames());

        PageResponseDTO<RecruitListAllDTO> responseDTO = recruitService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

        LocalDateTime deadline = recruitDTO.getReDeadline();  // 마감일 (LocalDateTime)
        LocalDateTime now = LocalDateTime.now();  // 현재 시간

        long daysLeft = ChronoUnit.DAYS.between(now, deadline);  // 남은 날짜 계산

        // D-0이면 "오늘 마감", D-음수면 "마감 종료" 표시
        String dDayText = daysLeft > 0 ? "D-" + daysLeft : (daysLeft == 0 ? "D-Day" : "마감 종료");

        model.addAttribute("dDayText", dDayText);
        log.info("Recruit ID: " + recruitDTO.getRecruitId());
        model.addAttribute("businessMemberDTO", business_memberDTO);
        log.info("BusinessMemberDTO: " + business_memberDTO);
        log.info("AllMemberDTO: " + all_memberDTO);
        model.addAttribute("dto", recruitDTO);
    }

//    @PostMapping("/remove")
//    public String remove(Long recruitId, RedirectAttributes redirectAttributes) {
//
//        log.info("remove post .. " + recruitId);
//        recruitService.remove(recruitId);
//
//        redirectAttributes.addFlashAttribute("result", "removed");
//        return "redirect:/recruit/list";
//    }
    @PostMapping("/remove")
    public String remove(RecruitDTO recruitDTO, RedirectAttributes redirectAttributes) {
        Long recruitId = recruitDTO.getRecruitId();
        log.info("remove post..............." + recruitDTO);
        recruitService.remove(recruitId);

        log.info(recruitDTO.getFileNames());
        List<String> fileNames = recruitDTO.getFileNames();
        if(fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/recruit/list";
    }

    public void removeFiles(List<String> files) {
        for (String fileName : files) {
            // 파일 경로를 이용해 리소스 객체 생성
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

                // 만약 이미지 파일이라면 섬네일도 삭제
                if (contentType.startsWith("image")) {
                    File thumbnailFile = new File(uploadPath + File.separator + "s_" + fileName);
                    thumbnailFile.delete();
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, RecruitDTO recruitDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("recruit_modify post...............");

        if(bindingResult.hasErrors()) {
            log.info("has errors");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("recruitId", recruitDTO.getRecruitId());

            return "redirect:/recruit/modify?"+link;
        }

        recruitService.modify(recruitDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("recruitId", recruitDTO.getRecruitId());

        return "redirect:/recruit/read";
    }



}
