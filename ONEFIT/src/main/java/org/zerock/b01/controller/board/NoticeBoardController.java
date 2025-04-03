package org.zerock.b01.controller.board;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
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
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.boardService.NoticeBoardService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/zboard")
@RequiredArgsConstructor
public class NoticeBoardController {

    @Value("C:\\upload\\board") //import 시에 springframework으로 시작하는 value
    private String boardUploadPath;

    private final NoticeBoardService noticeBoardService;

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


    @GetMapping("/board_notice_list")
    public void listNotice(@AuthenticationPrincipal User user, PageRequestDTO pageRequestDTO, Model model) {

//        PageResponseDTO<NoticeBoardDTO> responseDTO = noticeBoardService.listNotice(pageRequestDTO);

        PageResponseDTO<BoardListReplyCountDTO> responseDTO =
                noticeBoardService.listWithNoticeReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

        // 로그인한 사용자 ID를 모델에 추가
        if (user != null) {
            model.addAttribute("loginUserId", user.getUsername());
        }

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board_notice_register")
    public void registerNoticeGET() {

    }

    @PostMapping("/board_notice_register")
    public String registerNoticePost(@Valid NoticeBoardDTO noticeBoardDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes, Authentication authentication) {

        log.info("board_notice_register Post");

        MemberSecurityDTO principal = (MemberSecurityDTO) authentication.getPrincipal();
        String loggedInUserId = principal.getAllId(); // 로그인한 사용자 ID

        // 특정 아이디만 등록 가능하도록 설정
        String allowedUserId = "kim"; // 등록을 허용할 특정 ID
        if (!loggedInUserId.equals(allowedUserId)) {
            log.info("등록 권한이 없는 사용자 접근 차단: " + loggedInUserId);
            redirectAttributes.addFlashAttribute("error", "해당 계정은 공지사항을 등록할 수 없습니다.");
            return "redirect:/zboard/board_notice_list";
        }



        if (bindingResult.hasErrors()) {
            log.info("has errors....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/zboard/board_notice_register";
        }

        log.info(noticeBoardDTO);

        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);

        redirectAttributes.addFlashAttribute("result", noticeId);

        return "redirect:/zboard/board_notice_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board_notice_read")
    public void readNotice(Long noticeId, PageRequestDTO pageRequestDTO, Model model) {

        // 조회수 증가
        noticeBoardService.increaseNoticeHits(noticeId);  // 조회수 증가 메서드 호출


        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readNoticeOne(noticeId);

        log.info(noticeBoardDTO);

        model.addAttribute("dto", noticeBoardDTO);

    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board_notice_modify")
    public void readNoticeModify(Long noticeId, PageRequestDTO pageRequestDTO, Model model) {

        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readNoticeOne(noticeId);

        log.info(noticeBoardDTO);

        model.addAttribute("dto", noticeBoardDTO);

    }

    @PreAuthorize("principal.username == #noticeBoardDTO.allMember.allId")
    @PostMapping("/board_notice_modify")
    public String modifyNotice(@Valid NoticeBoardDTO noticeBoardDTO, BindingResult bindingResult,
                               PageRequestDTO pageRequestDTO,RedirectAttributes redirectAttributes) {

        log.info("board_notice_modify Post" + noticeBoardDTO);

        if (bindingResult.hasErrors()) {

            log.info("has errors....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("noticeId", noticeBoardDTO.getNoticeId());

            return "redirect:/zboard/board_notice_modify?"+link;
        }

        noticeBoardService.modifyNotice(noticeBoardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("noticeId", noticeBoardDTO.getNoticeId());

        return "redirect:/zboard/board_notice_read";

    }

    @PreAuthorize("principal.username == #noticeBoardDTO.allMember.allId")
    @PostMapping("/board_notice_remove")
    public String removeNotice(NoticeBoardDTO noticeBoardDTO, RedirectAttributes redirectAttributes) {

        Long noticeId = noticeBoardDTO.getNoticeId();

        log.info("board_notice_remove Post" + noticeId);

        noticeBoardService.removeNotice(noticeId);

        log.info(noticeBoardDTO.getFileNames());
        //삭제 파일리스트 가져오기
        List<String> fileNames = noticeBoardDTO.getFileNames();
        //파일명이 존재하면 삭제처리
        if (fileNames != null && fileNames.size() > 0) {
            removeNoticeFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/zboard/board_notice_list";
    }

    public void removeNoticeFiles(List<String> files) {

        for (String fileName : files) {

            Resource resource = new FileSystemResource(boardUploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());

                resource.getFile().delete();

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @PostMapping("/{noticeId}/increaseNoticeHits")
    public ResponseEntity<String> increaseNoticeHits(@PathVariable Long noticeId) {
        try {
            noticeBoardService.increaseNoticeHits(noticeId);
            return ResponseEntity.ok("조회수가 증가되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("공지사항을 찾을 수 없습니다.");
        }
    }
}
