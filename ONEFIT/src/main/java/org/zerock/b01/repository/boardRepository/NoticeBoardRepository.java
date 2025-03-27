package org.zerock.b01.repository.boardRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.repository.search.board.NoticeBoardSearch;

import java.util.Optional;

public interface NoticeBoardRepository extends JpaRepository<Notice_Board, Long>, NoticeBoardSearch {

    @Query(value = "select now()", nativeQuery = true)
    String getTime();

    @EntityGraph(attributePaths = {"boardFileSet","allMember"})
    @Query("select b from Notice_Board b where b.noticeId =:noticeId")
    Optional<Notice_Board> findByIdWithNoticefiles(Long noticeId);

    //조회수 증가
    @Modifying
    @Transactional
    @Query("UPDATE Notice_Board n SET n.nHits = n.nHits + 1 WHERE n.noticeId = :noticeId")
    void increaseNoticeViewCount(Long noticeId);
}
