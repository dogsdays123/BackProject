package org.zerock.b01.repository.recruitRepository.search;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.recruit.QRecruit_Register;
import org.zerock.b01.domain.recruit.Recruit_Register;

import java.util.List;

public class RecruitSearchImpl extends QuerydslRepositorySupport implements RecruitSearch {

    public RecruitSearchImpl() {
        super(Recruit_Register.class);
    }

    @Override
    public Page<Recruit_Register> search1(Pageable pageable) {

        QRecruit_Register recruit_register = QRecruit_Register.recruit_Register;

        JPQLQuery<Recruit_Register> query = from(recruit_register);

        query.where(recruit_register.re_title.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Recruit_Register> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Recruit_Register> searchAll(String[] types, String keyword, Pageable pageable) {

        QRecruit_Register recruit_register = QRecruit_Register.recruit_Register;

        JPQLQuery<Recruit_Register> query = from(recruit_register);

        if((types != null && types.length > 0) && keyword != null) {
            BooleanBuilder booleanBuilder = new BooleanBuilder();

            for(String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(recruit_register.re_title.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(recruit_register.re_company.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(recruit_register.reJobType.contains(keyword));
                        break;

                }
            }
            query.where(booleanBuilder);
        }
        query.where(recruit_register.recruitId.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Recruit_Register> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }

}
