package org.zerock.b01.repository.boardRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.board.Board_Reply;

public interface BoardReplyRepository extends JpaRepository<Board_Reply, Long> {

    @Query("select r from Board_Reply r where r.noticeBoard.noticeId = :noticeId")
    Page<Board_Reply> listOfNoticeBoard(Long noticeId, Pageable pageable);

    @Query("select r from Board_Reply r where r.qnaBoard.qnaId = :qnaId")
    Page<Board_Reply> listOfQnaBoard(Long qnaId, Pageable pageable);

    void deleteByNoticeBoard_noticeId(Long noticeId);

    void deleteByQnaBoard_qnaId(Long qnaId);
}
