//package org.zerock.b01.repository;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.domain.board.Board_Reply;
//import org.zerock.b01.domain.board.Notice_Board;
//import org.zerock.b01.domain.board.Qna_Board;
//import org.zerock.b01.repository.boardRepository.BoardReplyRepository;
//
//@SpringBootTest
//@Log4j2
//public class QnaReplyRepositoryTests {
//
//    @Autowired
//    private BoardReplyRepository boardReplyRepository;
//
//    @Test
//    public void testInsert() {
//
//        Long qnaId = 100L;
//
//        Qna_Board qna_board = Qna_Board.builder().qnaId(qnaId).build();
//
//        String allId = "member1";
//
//        All_Member all_member =All_Member.builder().allId(allId).build();
//
//        Board_Reply board_reply =Board_Reply.builder()
//                .allMember(all_member)
//                .replyText("댓글......")
//                .noticeBoard(null)
//                .qnaBoard(qna_board)
//                .build();
//
//        boardReplyRepository.save(board_reply);
//
//    }
//
//    @Test
//    public void testQnaReplies() {
//
//        Long qnaId = 100L;
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("replyId").descending());
//
//        Page<Board_Reply> result = boardReplyRepository.listOfQnaBoard(qnaId,pageable);
//
//        result.getContent().forEach(board_reply -> {
//            log.info(board_reply);
//        });
//    }
//}
