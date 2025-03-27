package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.All_Member;
<<<<<<< Updated upstream
import org.zerock.b01.domain.board.Notice_Board;
=======
>>>>>>> Stashed changes
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
import org.zerock.b01.service.boardService.BoardReplyService;

@SpringBootTest
@Log4j2
public class BoardReplyServiceTests {

    @Autowired
    private BoardReplyService boardReplyService;

    @Test
<<<<<<< Updated upstream
    public void testRegisterBoard() {

        String allId = "member1";

        All_Member all_member = All_Member.builder().allId(allId).build();

        BoardReplyDTO boardReplyDTO = BoardReplyDTO.builder()
                .replyText("ReplyDTO text")
                .allMember(all_member)
                .noticeId(100L)
                .qnaId(null)
                .build();

        log.info(boardReplyDTO);

=======
    public void registerBoardReply() {

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        BoardReplyDTO boardReplyDTO = BoardReplyDTO.builder()
                .replyText("ReplyDTO Text")
                .allId(allId)
                .noticeId(503L)
                .build();

>>>>>>> Stashed changes
        log.info(boardReplyService.registerBoardReply(boardReplyDTO));
    }
}
