package org.zerock.b01.repository.boardRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.repository.search.QnaBoardSearch;

public interface QnaBoardRepository extends JpaRepository<Qna_Board, Long>, QnaBoardSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

    //조회수 증가
    @Modifying
    @Transactional
    @Query("UPDATE Qna_Board n SET n.qHits = n.qHits + 1 WHERE n.qnaId = :qnaId")
    void increaseQnaViewCount(Long qnaId);
}
