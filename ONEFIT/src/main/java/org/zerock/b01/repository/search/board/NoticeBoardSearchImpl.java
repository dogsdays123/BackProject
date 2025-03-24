package org.zerock.b01.repository.search.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.QBoard_Reply;
import org.zerock.b01.domain.board.QNotice_Board;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;

import java.time.LocalDate;
import java.util.List;

public class NoticeBoardSearchImpl extends QuerydslRepositorySupport implements NoticeBoardSearch {

    public NoticeBoardSearchImpl() {
        super(Notice_Board.class);
    }

    @Override
    public Page<Notice_Board> searchNotice1(Pageable pageable) {

        QNotice_Board notice_board =QNotice_Board.notice_Board;

        JPQLQuery<Notice_Board> query = from(notice_board);

        query.where(notice_board.nTitle.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice_Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Notice_Board> searchNoticeAll(String[] types, String keyword,
                                              LocalDate startDate, LocalDate endDate, Pageable pageable) {

        QNotice_Board notice_board =QNotice_Board.notice_Board;
        JPQLQuery<Notice_Board> query = from(notice_board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        typeBuilder.or(notice_board.nTitle.contains(keyword));
                        break;
                    case "c":
                        typeBuilder.or(notice_board.nContent.contains(keyword));
                        break;
                }
            }//end for
            booleanBuilder.and(typeBuilder);
        }//end if

        // 🔹 날짜 범위 검색 조건 추가 (regDate는 LocalDateTime이므로 변환 필요)
        if (startDate != null && endDate != null) {
            booleanBuilder.and(
                    notice_board.regDate.between(
                            startDate.atStartOfDay(),
                            endDate.plusDays(1).atStartOfDay()  // 하루를 더해서 00:00:00 기준 비교
                    )
            );
        } else if (startDate != null) {
            booleanBuilder.and(notice_board.regDate.goe(startDate.atStartOfDay())); // startDate 이후 검색
        } else if (endDate != null) {
            booleanBuilder.and(notice_board.regDate.loe(endDate.plusDays(1).atStartOfDay())); // endDate 이전 검색
        }

        // 🔹 기본 조건: noticeId > 0
        booleanBuilder.and(notice_board.noticeId.gt(0L));
        query.where(booleanBuilder);

        // 🔹 페이징 처리
        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice_Board> list = query.fetch();
        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithNoticeReplyCount(String[] types, String keyword,
                                                                   LocalDate startDate, LocalDate endDate,
                                                                   Pageable pageable) {

        QNotice_Board notice_board =QNotice_Board.notice_Board;
        QBoard_Reply board_reply = QBoard_Reply.board_Reply;

        JPQLQuery<Notice_Board> query = from(notice_board);
        query.leftJoin(board_reply).on(board_reply.noticeBoard.eq(notice_board));

        query.groupBy(notice_board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        typeBuilder.or(notice_board.nTitle.contains(keyword));
                        break;
                    case "c":
                        typeBuilder.or(notice_board.nContent.contains(keyword));
                        break;
                }
            }//end for
            booleanBuilder.and(typeBuilder);
        }//end if

        // 🔹 날짜 범위 검색 조건 추가 (regDate는 LocalDateTime이므로 변환 필요)
        if (startDate != null && endDate != null) {
            booleanBuilder.and(
                    notice_board.regDate.between(
                            startDate.atStartOfDay(),
                            endDate.plusDays(1).atStartOfDay()  // 하루를 더해서 00:00:00 기준 비교
                    )
            );
        } else if (startDate != null) {
            booleanBuilder.and(notice_board.regDate.goe(startDate.atStartOfDay())); // startDate 이후 검색
        } else if (endDate != null) {
            booleanBuilder.and(notice_board.regDate.loe(endDate.plusDays(1).atStartOfDay())); // endDate 이전 검색
        }

        // 🔹 기본 조건: noticeId > 0
        booleanBuilder.and(notice_board.noticeId.gt(0L));
        query.where(booleanBuilder);

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.
                bean(BoardListReplyCountDTO.class,
                            notice_board.noticeId,
                            notice_board.nTitle.as("title"),
                            notice_board.allMember.allId.as("allId"),
                            notice_board.nHits.as("hits"),
                            notice_board.regDate,
                            board_reply.count().as("replyCount")
                ));

        // 🔹 페이징 처리
        this.getQuerydsl().applyPagination(pageable, dtoQuery);

        List<BoardListReplyCountDTO> dtoList = dtoQuery.fetch();

        // setBoardType 호출
        dtoList.forEach(dto -> dto.setBoardType(dto.getTitle()));

        long count = dtoQuery.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }
}
