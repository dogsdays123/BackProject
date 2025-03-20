package org.zerock.b01.repository.search.board;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.board.QQna_Board;
import org.zerock.b01.domain.board.Qna_Board;

import java.util.List;

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
    public Page<Qna_Board> searchQnaAll(String[] types, String keyword, Pageable pageable) {

        QQna_Board qna_board =QQna_Board.qna_Board;
        JPQLQuery<Qna_Board> query = from(qna_board);

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(qna_board.qTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(qna_board.qContent.contains(keyword));
                        break;
                    case "m":
                        booleanBuilder.or(qna_board.allMember.name.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno>0
        query.where(qna_board.qnaId.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Qna_Board> list = query.fetch();

        long count = query.fetchCount();

//        return null;
        return new PageImpl<>(list, pageable, count);
    }
}
