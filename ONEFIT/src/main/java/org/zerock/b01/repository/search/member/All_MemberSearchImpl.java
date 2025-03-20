package org.zerock.b01.repository.search.member;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.QAll_Member;

import java.util.List;

public class All_MemberSearchImpl extends QuerydslRepositorySupport implements All_MemberSearch {
    public All_MemberSearchImpl() {super(All_MemberSearchImpl.class);}

    @Override
    public Page<All_Member> search2(Pageable pageable) {
        QAll_Member allMember = QAll_Member.all_Member; // Q도메인 객체
        JPQLQuery<All_Member> query = from(allMember); //select from board
        query.where(allMember.name.contains("1")); //where title like....

        //paging = QuerydslRepositorySupport에서 상속받은 것을 사용
        this.getQuerydsl().applyPagination(pageable, query);

        List<All_Member> list = query.fetch();
        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<All_Member> searchMember(String[] types, String keyword, Pageable pageable) {
        QAll_Member all_Member = QAll_Member.all_Member;
        JPQLQuery<All_Member> query = from(all_Member);

        if ((types != null) && (types.length > 0) && keyword != null) {
            //검색 조건과 키워드가 있다면
            BooleanBuilder booleanBuilder = new BooleanBuilder();
            for (String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(all_Member.name.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(all_Member.email.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(all_Member.allId.contains(keyword));
                }
            }//end for
            query.where(booleanBuilder);
        }//end if
        //bno > 0
        //query.where(all_Member.allId.gt());
        //paging
        this.getQuerydsl().applyPagination(pageable, query);
        List<All_Member> list = query.fetch();
        long count = query.fetchCount();
        return new PageImpl<All_Member>(list, pageable, count);
    }
}
