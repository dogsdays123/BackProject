package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.board.Board_Reply;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;

@SpringBootTest
@Log4j2
public class NoticeReplyRepositoryTests {

    @Autowired
    private BoardReplyRepository boardReplyRepository;

    @Test
    public void testInsert() {

        Long noticeId = 501L;

        Notice_Board notice_board = Notice_Board.builder().noticeId(noticeId).build();

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        Board_Reply board_reply =Board_Reply.builder()
                .allMember(all_member)
                .replyText("댓글......")
                .noticeBoard(notice_board)
                .qnaBoard(null)
                .build();

        boardReplyRepository.save(board_reply);

    }

    @Test
    public void testNoticeReplies() {

        Long noticeId = 501L;

        Pageable pageable = PageRequest.of(0, 10, Sort.by("replyId").descending());

        Page<Board_Reply> result = boardReplyRepository.listOfNoticeBoard(noticeId,pageable);

        result.getContent().forEach(board_reply -> {
            log.info(board_reply);
        });
    }

}
