package org.zerock.b01.repository.boardRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.board.Board_Reply;

public interface BoardReplyRepository extends JpaRepository<Board_Reply, Long> {
}
