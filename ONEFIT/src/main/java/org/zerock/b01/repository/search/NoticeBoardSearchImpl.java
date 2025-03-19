package org.zerock.b01.repository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.QNotice_Board;

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
    public Page<Notice_Board> searchNoticeAll(String[] types, String keyword, Pageable pageable) {

        QNotice_Board notice_board =QNotice_Board.notice_Board;
        JPQLQuery<Notice_Board> query = from(notice_board);

        if ((types != null && types.length > 0) && keyword != null) {

            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for (String type : types) {

                switch (type) {
                    case "t":
                        booleanBuilder.or(notice_board.nTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(notice_board.nContent.contains(keyword));
                        break;
                }
            }//end for
            query.where(booleanBuilder);
        }//end if

        //bno>0
        query.where(notice_board.noticeId.gt(0L));

        //paging
        this.getQuerydsl().applyPagination(pageable, query);

        List<Notice_Board> list = query.fetch();

        long count = query.fetchCount();

//        return null;
        return new PageImpl<>(list, pageable, count);

    }
}
