package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.board.Notice_Board;

public interface NoticeBoardSearch {

    Page<Notice_Board> searchNotice1(Pageable pageable);

    Page<Notice_Board> searchNoticeAll(String[] types, String keyword, Pageable pageable);
}
