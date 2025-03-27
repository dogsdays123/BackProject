package org.zerock.b01.controller.recruit;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
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
import org.zerock.b01.domain.member.Business_Member;
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
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/recruit")
@Log4j2
@RequiredArgsConstructor
public class RecruitController {



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

        //유저정보(일반Default)가 존재할 때
        if (all_memberDTO != null) {
            model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가
            //유저정보(일반, 개인) 전역에 갖고오기
            User_MemberDTO user_MemberDTO = member_Set_Type_Service.userRead(all_memberDTO.getAllId());
            Business_MemberDTO business_memberDTO = member_Set_Type_Service.BusinessRead(all_memberDTO.getAllId());


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

        model.addAttribute("checkId", false);
        model.addAttribute("checkEmail", false);

        List<All_MemberDTO> all_memberDTOList = all_memberService.readAllMember();
        model.addAttribute("all_memberDTOList", all_memberDTOList);
        log.info("모든회원@@@@@@@@@" + all_memberDTOList);
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
    public String registerGET(HttpServletResponse response, Model model) throws IOException {
        // 모델에서 "user_memberDTO"와 "business_memberDTO"를 가져옵니다
        User_MemberDTO user_MemberDTO = (User_MemberDTO) model.getAttribute("user_memberDTO");
        Business_MemberDTO business_MemberDTO = (Business_MemberDTO) model.getAttribute("business_memberDTO");
        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");

        log.info("**register Get.............**");
        log.info("User Member DTO: " + user_MemberDTO);
        log.info("**register Get.............**");
        log.info("All Member DTO: " + all_memberDTO);
        log.info("**register Get.............**");
        log.info("Business Member DTO: " + business_MemberDTO);
        log.info("**register Get.............**");

        if (all_memberDTO == null || !"business".equals(all_memberDTO.getMemberType())) {
            String script = "<script>alert('기업회원만 접근이 가능합니다.'); history.back();</script>";
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(script);
            response.getWriter().flush();
            return null;
        }

        log.info("business ID : " + business_MemberDTO.getBusinessId());
        // 필요한 로직을 추가하거나 뷰에 전달할 수 있습니다.
        model.addAttribute("user_memberDTO", user_MemberDTO);
        model.addAttribute("business_memberDTO", business_MemberDTO);

        return "recruit/register";
    }

    private Business_Member convertToBusinessMember(Business_MemberDTO business_MemberDTO) {
        if (business_MemberDTO == null) {
            return null;
        }

        Business_Member businessMember = new Business_Member();
        businessMember.setBusinessId(business_MemberDTO.getBusinessId());
        // 다른 필드도 여기에 추가 (필요한 경우)
        return businessMember;
    }


    @PostMapping("/register")
    public String registerPost (@Valid RecruitDTO recruitDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {

        User_MemberDTO user_MemberDTO = (User_MemberDTO) model.getAttribute("user_memberDTO");
        Business_MemberDTO business_MemberDTO = (Business_MemberDTO) model.getAttribute("business_memberDTO");
        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");

        log.info("**register Post.............**");
        log.info("User Member DTO: " + user_MemberDTO);
        log.info("**register Post.............**");
        log.info("All Member DTO: " + all_memberDTO);
        log.info("**register Post.............**");
        log.info("Business Member DTO: " + business_MemberDTO);
        log.info("**register Post.............**");

        if(bindingResult.hasErrors()) {
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/recruit/register";
        }
        if (business_MemberDTO != null) {
            // Business_MemberDTO -> Business_Member 변환 후 recruitDTO에 설정
            Business_Member businessMember = convertToBusinessMember(business_MemberDTO);
            recruitDTO.setBusiness_member(businessMember);
        }

        log.info(recruitDTO);

        Long recruitId = recruitService.register(recruitDTO);

        redirectAttributes.addFlashAttribute("result", recruitId);

        return "redirect:/recruit/list";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove")
    public String remove(RecruitDTO recruitDTO, Long recruitId, HttpServletResponse response,
                         PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes, Model model) throws IOException {

        Business_MemberDTO business_MemberDTO = (Business_MemberDTO) model.getAttribute("business_memberDTO");
        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");
        String link = pageRequestDTO.getLink();
        recruitDTO = recruitService.readOne(recruitId);
//        Long recruitId = recruitDTO.getRecruitId();

        if (all_memberDTO == null || !"business".equals(all_memberDTO.getMemberType())) {
            // 로그인된 사용자가 기업회원이 아닐 경우
            log.info("Access Denied: Not a business member.");
            String script = "<script>" +
                    "alert('기업 회원만 접근할 수 있습니다.');" +
                    "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
                    "</script>";
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(script);
            response.getWriter().flush();
            return null; // 혹은 return;
        }
        if (business_MemberDTO != null && recruitDTO != null && recruitDTO.getBusiness_member() != null) {
            if (!business_MemberDTO.getBusinessId().equals(recruitDTO.getBusiness_member().getBusinessId())) {
                log.info("Access Denied: Business ID mismatch.");
                String script = "<script>" +
                        "alert('해당 게시글 작성자만 접근할 수 있습니다.');" +
                        "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
                        "</script>";
                response.setContentType("text/html; charset=UTF-8");
                response.getWriter().write(script);
                response.getWriter().flush();
                return null;
            }
        } else {
            // business_MemberDTO나 recruitDTO.getBusiness_member()가 null일 경우
            log.info("Access Denied: 인증 정보 누락");
            String script = "<script>" +
                    "alert('접근 권한이 없습니다. 로그인 상태를 확인해주세요.');" +
                    "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
                    "</script>";
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(script);
            response.getWriter().flush();
            return null;
        }

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

    @GetMapping("/modify")
    public String modifyGet(Long recruitId, HttpServletResponse response, PageRequestDTO pageRequestDTO, Model model) throws IOException {
        Business_MemberDTO business_MemberDTO = (Business_MemberDTO) model.getAttribute("business_memberDTO");
        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");

        log.info("**Modify Get.............**");
        log.info("All Member DTO: " + all_memberDTO);
        log.info("**Modify Get.............**");
        log.info("Business Member DTO: " + business_MemberDTO);
        log.info("**Modify Get.............**");


        RecruitDTO recruitDTO = recruitService.readOne(recruitId);

//        log.info("Recruit Business ID : " + recruitDTO.getBusiness_member().getBusinessId());

        String link = pageRequestDTO.getLink();
        if (all_memberDTO == null || !"business".equals(all_memberDTO.getMemberType())) {
            // 로그인된 사용자가 기업회원이 아닐 경우
            log.info("Access Denied: Not a business member.");
            String script = "<script>" +
                    "alert('해당 게시글 작성자만 접근할 수 있습니다.');" +
                    "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
                    "</script>";
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(script);
            response.getWriter().flush();
            return null; // 혹은 return;
        }

        // 권한이 없는 경우 (modify, delete 등에서도 공통 사용 가능)
        if (!business_MemberDTO.getBusinessId().equals(recruitDTO.getBusiness_member().getBusinessId())) {
            log.info("Access Denied: Business ID mismatch.");
            String script = "<script>" +
                    "alert('해당 게시글 작성자만 접근할 수 있습니다.');" +
                    "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
                    "</script>";
            response.setContentType("text/html; charset=UTF-8");
            response.getWriter().write(script);
            response.getWriter().flush();
            return null;
        }

//        if (!business_MemberDTO.getBusinessId().equals(recruitDTO.getBusiness_member().getBusinessId())) {
//            log.info("Access Denied: Business ID mismatch.");
//            String script = "<script>" +
//                    "alert('해당 게시글 작성자만 접근할 수 있습니다.');" +
//                    "location.href='/recruit/read?recruitId=" + recruitId + "&" + link + "';" +
//                    "</script>";
//            response.setContentType("text/html; charset=UTF-8");
//            response.getWriter().write(script);
//            response.getWriter().flush();
//            return null; // 혹은 return;
//        }

        Business_MemberDTO businessMemberDTO = recruitService.readBusinessMember(recruitId);
        model.addAttribute("businessMemberDTO", businessMemberDTO);

        recruitDTO = recruitService.readOne(recruitId);
        model.addAttribute("dto", recruitDTO);
        log.info("Recruit ID: " + recruitDTO.getRecruitId());
        log.info("Business ID : " + business_MemberDTO.getBusinessId());
        log.info("RecruitDTO : " + recruitDTO);
        log.info("RecruitDTO Business ID : " + recruitDTO.getBusiness_member().getBusinessId());
        log.info("File Names: {}", recruitDTO.getFileNames());

        PageResponseDTO<RecruitListAllDTO> responseDTO = recruitService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);

        LocalDateTime deadline = recruitDTO.getReDeadline();  // 마감일 (LocalDateTime)
        LocalDateTime now = LocalDateTime.now();  // 현재 시간
        long daysLeft = ChronoUnit.DAYS.between(now, deadline);  // 남은 날짜 계산
        String dDayText = daysLeft > 0 ? "D-" + daysLeft : (daysLeft == 0 ? "D-Day" : "마감 종료"); // D-0이면 "오늘 마감", D-음수면 "마감 종료" 표시
        model.addAttribute("dDayText", dDayText);

        return "/recruit/modify";
    }

    @GetMapping("/read")
    public void read(Long recruitId, PageRequestDTO pageRequestDTO, Model model) {

        Business_MemberDTO business_MemberDTO = (Business_MemberDTO) model.getAttribute("business_memberDTO");
        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");

        log.info("**read Get.............**");
        log.info("All Member DTO: " + all_memberDTO);
        log.info("**read Get.............**");
        log.info("Business Member DTO: " + business_MemberDTO);
        log.info("**read Get.............**");


        Business_MemberDTO businessMemberDTO = recruitService.readBusinessMember(recruitId);
        log.info("*******************************");
        log.info("read Get........" + businessMemberDTO);
        model.addAttribute("businessMemberDTO", businessMemberDTO);

        RecruitDTO recruitDTO = recruitService.readOne(recruitId);
        model.addAttribute("dto", recruitDTO);
        log.info("Recruit ID: " + recruitDTO.getRecruitId());

//        Long businessId = recruitDTO.getBusiness_member() != null ? recruitDTO.getBusiness_member().getBusinessId() : null;
//        model.addAttribute("businessId", businessId);

        log.info("File Names: {}", recruitDTO.getFileNames());

        PageResponseDTO<RecruitListAllDTO> responseDTO = recruitService.list(pageRequestDTO);
        log.info(responseDTO);
        model.addAttribute("responseDTO", responseDTO);

        LocalDateTime deadline = recruitDTO.getReDeadline();  // 마감일 (LocalDateTime)
        LocalDateTime now = LocalDateTime.now();  // 현재 시간
        long daysLeft = ChronoUnit.DAYS.between(now, deadline);  // 남은 날짜 계산
        String dDayText = daysLeft > 0 ? "D-" + daysLeft : (daysLeft == 0 ? "D-Day" : "마감 종료"); // D-0이면 "오늘 마감", D-음수면 "마감 종료" 표시
        model.addAttribute("dDayText", dDayText);
    }

    @PostMapping("/read")
    public void read(Business_MemberDTO businessMemberDTO){

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



    @Value("C:\\upload")
    private String uploadPath;

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
