package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.board.Qna_Board;

public interface QnaBoardSearch {

    Page<Qna_Board> searchQna1(Pageable pageable);

    Page<Qna_Board> searchQnaAll(String[] types, String keyword, Pageable pageable);
}
