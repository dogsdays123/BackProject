package org.zerock.b01.repository.search.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardListAllDTO;

import java.time.LocalDate;

public interface QnaBoardSearch {

    Page<Qna_Board> searchQna1(Pageable pageable);

    Page<Qna_Board> searchQnaAll(String[] types, String keyword,
                                 LocalDate startDate, LocalDate endDate,Pageable pageable);

    Page<BoardListReplyCountDTO> searchWithQnaReplyCount(String[] types, String keyword,
                                                         LocalDate startDate, LocalDate endDate,
                                                         Pageable pageable);

    Page<QnaBoardListAllDTO> searchWithQnaAll(String[] types, String keyword,
                                              LocalDate startDate, LocalDate endDate,
                                              Pageable pageable);
}
