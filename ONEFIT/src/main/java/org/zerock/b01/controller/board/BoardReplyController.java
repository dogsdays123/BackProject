package org.zerock.b01.controller.board;

import io.swagger.v3.oas.annotations.Operation;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import jakarta.servlet.http.HttpServletRequest;
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.boardService.BoardReplyService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

import java.util.HashMap;
import java.util.Map;

@RestController
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
@RequestMapping("/replies_board")
=======
@RequestMapping("/replies")
>>>>>>> Stashed changes
=======
@RequestMapping("/replies")
>>>>>>> Stashed changes
=======
@RequestMapping("/replies")
>>>>>>> Stashed changes
@Log4j2
@RequiredArgsConstructor
public class BoardReplyController {

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
    private final BoardReplyService boardReplyService;

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
        } else {
            model.addAttribute("sidebar", false);
        }
        log.info("회원전역@@@@@@@@@" + all_memberDTO);
    }

    @Operation(description = "Replies POST")
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> registerBoardReply(@Valid @RequestBody BoardReplyDTO boardReplyDTO,
                                     BindingResult bindingResult) throws BindException {

        log.info("댓글 등록 ReplyDTO" +boardReplyDTO);

=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
    @Operation(description = "Replies POST")
    @PostMapping(value = "/",consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String,Long> register(@Valid @RequestBody BoardReplyDTO boardReplyDTO,
                                     BindingResult bindingResult) throws BindException {

        log.info(boardReplyDTO);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        if(bindingResult.hasErrors()) {
            throw new BindException(bindingResult);
        }

        Map<String,Long> resulMap = new HashMap<>();

<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
        Long replyId = boardReplyService.registerBoardReply(boardReplyDTO);

        resulMap.put("replyId", replyId);

        return resulMap;
    }

    @Operation(description = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/board_notice_list/{noticeId}")
    public PageResponseDTO<BoardReplyDTO> getNoticeReplyList(@PathVariable("noticeId") Long noticeId, PageRequestDTO pageRequestDTO) {

        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfNoticeBoardReply(noticeId, pageRequestDTO);
        log.info("ResponseDTO: " + responseDTO);
        return responseDTO;
    }

    @Operation(description = "GET 방식으로 특정 게시물의 댓글 목록")
    @GetMapping(value = "/board_qa_list/{qnaId}")
    public PageResponseDTO<BoardReplyDTO> getQnaReplyList(@PathVariable("qnaId") Long qnaId, PageRequestDTO pageRequestDTO) {

        PageResponseDTO<BoardReplyDTO> responseDTO = boardReplyService.getListOfQnaBoardReply(qnaId, pageRequestDTO);

        return responseDTO;
    }

    @Operation(description = "GET 방식으로 특정 댓글 조회")
    @GetMapping("/{replyId}")
    public BoardReplyDTO getBoardReplyDTO(@PathVariable("replyId") Long replyId) {
        BoardReplyDTO boardReplyDTO = boardReplyService.readBoardReply(replyId);
        return boardReplyDTO;
    }


    @Operation(description = "DELETE 방식으로 댓글 처리")
    @DeleteMapping("/{replyId}")
    public Map<String,Long> removeBoardReply(@PathVariable("replyId") Long replyId) {

        boardReplyService.removeBoardReply(replyId);

        Map<String,Long> resulMap = new HashMap<>();

        resulMap.put("replyId", replyId);

        return resulMap;
    }

    @Operation(description = "PUT 방식으로 댓글 처리")
    @PutMapping(value = "/{replyId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> modifyBoardReply(@PathVariable("replyId") Long replyId,
                                         @RequestBody BoardReplyDTO boardReplyDTO) {

        boardReplyDTO.setReplyId(replyId);

        boardReplyService.modifyBoardReply(boardReplyDTO);

        Map<String,Long> resulMap = new HashMap<>();

        resulMap.put("replyId", replyId);
=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
//        Long replyId = replyService.register(replyDTO);

        resulMap.put("replyId",111L);

//        resulMap.put("replyId", rno);
<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes

        return resulMap;
    }
}
