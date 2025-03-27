//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.domain.board.Notice_Board;
//import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
//import org.zerock.b01.service.boardService.BoardReplyService;
//
//@SpringBootTest
//@Log4j2
//public class BoardReplyServiceTests {
//
//    @Autowired
//    private BoardReplyService boardReplyService;
//
//    @Test
//    public void testRegisterBoard() {
//
//        String allId = "test";
//
//        All_Member all_member = All_Member.builder().allId(allId).build();
//
//        BoardReplyDTO boardReplyDTO = BoardReplyDTO.builder()
//                .replyText("ReplyDTO text")
//                .allId("dnjswls53")
//                .noticeId(100L)
//                .qnaId(null)
//                .build();
//
//        log.info(boardReplyDTO);
//
//        log.info(boardReplyService.registerBoard(boardReplyDTO));
//    }
//}
