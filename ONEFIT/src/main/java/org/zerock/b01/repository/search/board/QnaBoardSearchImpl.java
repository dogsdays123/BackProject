package org.zerock.b01.repository.search.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.board.QBoard_Reply;
import org.zerock.b01.domain.board.QQna_Board;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.boardDTO.BoardFileDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardListAllDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class QnaBoardSearchImpl extends QuerydslRepositorySupport implements QnaBoardSearch {

    public QnaBoardSearchImpl() {
        super(Qna_Board.class);
    }

    @Override
    public Page<Qna_Board> searchQna1(Pageable pageable) {

        QQna_Board qna_board = QQna_Board.qna_Board;

        JPQLQuery<Qna_Board> query = from(qna_board);

        query.where(qna_board.qTitle.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Qna_Board> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Qna_Board> searchQnaAll(String[] types, String keyword,
                                        LocalDate startDate, LocalDate endDate, Pageable pageable) {

        QQna_Board qna_board =QQna_Board.qna_Board;
        JPQLQuery<Qna_Board> query = from(qna_board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        typeBuilder.or(qna_board.qTitle.contains(keyword));
                        break;
                    case "c":
                        typeBuilder.or(qna_board.qContent.contains(keyword));
                        break;
                    case "m":
                        typeBuilder.or(qna_board.allMember.name.contains(keyword));
                        break;
                }
            }//end for
            booleanBuilder.and(typeBuilder);
        }//end if

        // 🔹 날짜 범위 검색 조건 추가 (regDate는 LocalDateTime이므로 변환 필요)
        if (startDate != null && endDate != null) {
            booleanBuilder.and(
                    qna_board.regDate.between(
                            startDate.atStartOfDay(),
                            endDate.plusDays(1).atStartOfDay()  // 하루를 더해서 00:00:00 기준 비교
                    )
            );
        } else if (startDate != null) {
            booleanBuilder.and(qna_board.regDate.goe(startDate.atStartOfDay())); // startDate 이후 검색
        } else if (endDate != null) {
            booleanBuilder.and(qna_board.regDate.loe(endDate.plusDays(1).atStartOfDay())); // endDate 이전 검색
        }

        // 🔹 기본 조건: noticeId > 0
        booleanBuilder.and(qna_board.qnaId.gt(0L));
        query.where(booleanBuilder);

        //bno>0
        query.where(qna_board.qnaId.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Qna_Board> list = query.fetch();

        long count = query.fetchCount();

//        return null;
        return new PageImpl<>(list, pageable, count);
    }

    @Override
    public Page<BoardListReplyCountDTO> searchWithQnaReplyCount(String[] types, String keyword,
                                                                LocalDate startDate, LocalDate endDate,
                                                                Pageable pageable) {

        QQna_Board qna_board =QQna_Board.qna_Board;
        QBoard_Reply board_reply = QBoard_Reply.board_Reply;

        JPQLQuery<Qna_Board> query = from(qna_board);
        query.leftJoin(board_reply).on(board_reply.qnaBoard.eq(qna_board));

        query.groupBy(qna_board);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        typeBuilder.or(qna_board.qTitle.contains(keyword));
                        break;
                    case "c":
                        typeBuilder.or(qna_board.qContent.contains(keyword));
                        break;
                    case "m":
                        typeBuilder.or(qna_board.allMember.name.contains(keyword));
                        break;
                }
            }//end for
            booleanBuilder.and(typeBuilder);
        }//end if

        // 🔹 날짜 범위 검색 조건 추가 (regDate는 LocalDateTime이므로 변환 필요)
        if (startDate != null && endDate != null) {
            booleanBuilder.and(
                    qna_board.regDate.between(
                            startDate.atStartOfDay(),
                            endDate.plusDays(1).atStartOfDay()  // 하루를 더해서 00:00:00 기준 비교
                    )
            );
        } else if (startDate != null) {
            booleanBuilder.and(qna_board.regDate.goe(startDate.atStartOfDay())); // startDate 이후 검색
        } else if (endDate != null) {
            booleanBuilder.and(qna_board.regDate.loe(endDate.plusDays(1).atStartOfDay())); // endDate 이전 검색
        }

        // 🔹 기본 조건: noticeId > 0
        booleanBuilder.and(qna_board.qnaId.gt(0L));
        query.where(booleanBuilder);

        JPQLQuery<BoardListReplyCountDTO> dtoQuery = query.select(Projections.
                bean(BoardListReplyCountDTO.class,
                        qna_board.qnaId,
                        qna_board.qTitle.as("title"),
                        qna_board.allMember.allId.as("allId"),
                        qna_board.qHits.as("hits"),
                        qna_board.regDate,
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

    @Override
    public Page<QnaBoardListAllDTO> searchWithQnaAll(String[] types, String keyword,
                                                     LocalDate startDate, LocalDate endDate,
                                                     Pageable pageable) {

        QQna_Board qna_board = QQna_Board.qna_Board;
        QBoard_Reply board_reply = QBoard_Reply.board_Reply;

        JPQLQuery<Qna_Board> qna_boardJPQLQuery = from(qna_board);
        qna_boardJPQLQuery.leftJoin(board_reply).on(board_reply.qnaBoard.eq(qna_board));

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder typeBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        typeBuilder.or(qna_board.qTitle.contains(keyword));
                        break;
                    case "c":
                        typeBuilder.or(qna_board.qContent.contains(keyword));
                        break;
                    case "m":
                        typeBuilder.or(qna_board.allMember.name.contains(keyword));
                        break;
                }
            }//end for
            booleanBuilder.and(typeBuilder);
        }//end if

        // 🔹 날짜 범위 검색 조건 추가 (regDate는 LocalDateTime이므로 변환 필요)
        if (startDate != null && endDate != null) {
            booleanBuilder.and(
                    qna_board.regDate.between(
                            startDate.atStartOfDay(),
                            endDate.plusDays(1).atStartOfDay()  // 하루를 더해서 00:00:00 기준 비교
                    )
            );
        } else if (startDate != null) {
            booleanBuilder.and(qna_board.regDate.goe(startDate.atStartOfDay())); // startDate 이후 검색
        } else if (endDate != null) {
            booleanBuilder.and(qna_board.regDate.loe(endDate.plusDays(1).atStartOfDay())); // endDate 이전 검색
        }

        qna_boardJPQLQuery.groupBy(qna_board);

        getQuerydsl().applyPagination(pageable, qna_boardJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = qna_boardJPQLQuery.select(qna_board, board_reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<QnaBoardListAllDTO> dtoList = tupleList.stream().map(tuple -> {

            Qna_Board qna_board1 = (Qna_Board) tuple.get(qna_board);
            Long replyCount = tuple.get(1,Long.class);

            QnaBoardListAllDTO dto = QnaBoardListAllDTO.builder()
                    .qnaId(qna_board1.getQnaId())
                    .qTitle(qna_board1.getQTitle())
                    .allId(qna_board1.getAllMember().getAllId())
                    .qHits(qna_board1.getQHits())
                    .regDate(qna_board1.getRegDate())
                    .replyCount(replyCount)
                    .build();

            List<BoardFileDTO> fileDTOS = qna_board1.getBoardFileSet().stream().sorted()
                    .map(board_file -> BoardFileDTO.builder()
                            .uuid(board_file.getUuid())
                            .fileName(board_file.getFileName())
                            .ord(board_file.getOrd())
                            .build()
                    ).collect(Collectors.toList());

            dto.setBoardFiles(fileDTOS);

            return dto;
        }).collect(Collectors.toList());

        long totalCount = qna_boardJPQLQuery.fetchCount();

        return  new PageImpl<>(dtoList, pageable, totalCount);
    }

}
