package org.zerock.b01.repository.search.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.board.Notice_Board;

import java.time.LocalDate;

public interface NoticeBoardSearch {

    Page<Notice_Board> searchNotice1(Pageable pageable);

    Page<Notice_Board> searchNoticeAll(String[] types, String keyword,
                                       LocalDate startDate, LocalDate endDate, Pageable pageable);
}
