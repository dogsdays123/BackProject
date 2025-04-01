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
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.boardService.QnaBoardService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;

@Controller
@Log4j2
@RequestMapping("/zboard")
@RequiredArgsConstructor
public class QnaBoardController {

    @Value("C:\\upload\\board") //import 시에 springframework으로 시작하는 value
    private String boardUploadPath;

    private final QnaBoardService qnaBoardService;

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


    @GetMapping("/board_qa_list")
    public void listQna(PageRequestDTO pageRequestDTO, Model model) {

//        PageResponseDTO<QnaBoardDTO> responseDTO = qnaBoardService.listQna(pageRequestDTO);

        PageResponseDTO<BoardListReplyCountDTO> responseDTO =
                qnaBoardService.listWithQnaReplyCount(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board_qa_register")
    public void registerQanGET() {

    }

    @PostMapping("/board_qa_register")
    public String registerQnaPost(@Valid QnaBoardDTO qnaBoardDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        log.info("board_qa_register Post");

        if (bindingResult.hasErrors()) {
            log.info("has errors....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/zboard/board_qa_register";
        }

        log.info(qnaBoardDTO);

        Long qnaId = qnaBoardService.registerQna(qnaBoardDTO);

        redirectAttributes.addFlashAttribute("result", qnaId);

        return "redirect:/zboard/board_qa_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/board_qa_read","/board_qa_modify"})
    public void readQna(Long qnaId, PageRequestDTO pageRequestDTO, Model model) {

        // 조회수 증가
        qnaBoardService.increaseQnaHits(qnaId);  // 조회수 증가 메서드 호출

        QnaBoardDTO qnaBoardDTO = qnaBoardService.readQnaOne(qnaId);

        log.info(qnaBoardDTO);

        model.addAttribute("dto", qnaBoardDTO);

    }

    @PreAuthorize("principal.username == #qnaBoardDTO.allMember.allId")
    @PostMapping("/board_qa_modify")
    public String modifyQna(PageRequestDTO pageRequestDTO, @Valid QnaBoardDTO qnaBoardDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board_qna_modify Post" + qnaBoardDTO);

        if (bindingResult.hasErrors()) {

            log.info("has errors....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("qnaId", qnaBoardDTO.getQnaId());

            return "redirect:/zboard/board_qa_modify?" + link;
        }

        qnaBoardService.modifyQna(qnaBoardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("qnaId", qnaBoardDTO.getQnaId());

        return "redirect:/zboard/board_qa_read";

    }

    @PreAuthorize("principal.username == #qnaBoardDTO.allMember.allId")
    @PostMapping("/board_qa_remove")
    public String removeQna(QnaBoardDTO qnaBoardDTO, RedirectAttributes redirectAttributes) {

        Long qnaId = qnaBoardDTO.getQnaId();

        log.info("board_qa_remove Post" + qnaId);

        qnaBoardService.removeQna(qnaId);

        log.info(qnaBoardDTO.getFileNames());
        //삭제 파일리스트 가져오기
        List<String> fileNames = qnaBoardDTO.getFileNames();
        //파일명이 존재하면 삭제처리
        if (fileNames != null && fileNames.size() > 0) {
            removeQnaFiles(fileNames);
        }

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/zboard/board_qa_list";
    }

    public void removeQnaFiles(List<String> files) {

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

    @PostMapping("/{qnaId}/increaseQnaHits")
    public ResponseEntity<String> increaseNoticeHits(@PathVariable Long qnaId) {
        try {
            qnaBoardService.increaseQnaHits(qnaId);
            return ResponseEntity.ok("조회수가 증가되었습니다.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("공지사항을 찾을 수 없습니다.");
        }
    }
}
