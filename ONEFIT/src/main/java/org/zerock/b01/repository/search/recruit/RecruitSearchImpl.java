package org.zerock.b01.repository.search.recruit;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.recruit.QRecruit_Register;
import org.zerock.b01.domain.recruit.Recruit_Register;
import com.querydsl.core.types.dsl.NumberTemplate;

import java.util.List;

public class RecruitSearchImpl extends QuerydslRepositorySupport implements RecruitSearch {

    public RecruitSearchImpl() {
        super(Recruit_Register.class);
    }

    @Override
    public Page<Recruit_Register> search1(Pageable pageable) {

        QRecruit_Register recruit_register = QRecruit_Register.recruit_Register;

        JPQLQuery<Recruit_Register> query = from(recruit_register);

        query.where(recruit_register.reTitle.contains("1"));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Recruit_Register> list = query.fetch();

        long count = query.fetchCount();

        return null;
    }

    @Override
    public Page<Recruit_Register> searchAll(String[] types, String keyword, String gender, String age, Pageable pageable) {

        QRecruit_Register recruit_register = QRecruit_Register.recruit_Register;

        JPQLQuery<Recruit_Register> query = from(recruit_register);

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if((types != null && types.length > 0) && keyword != null) {


            for(String type : types) {
                switch (type) {
                    case "t":
                        booleanBuilder.or(recruit_register.reTitle.contains(keyword));
                        break;
                    case "c":
                        booleanBuilder.or(recruit_register.reCompany.contains(keyword));
                        break;
                    case "w":
                        booleanBuilder.or(recruit_register.reIndustry.contains(keyword));
                        break;
                }
            }

        }
        if (gender != null && !gender.isEmpty()) {
            if (!"성별무관".equals(gender)) {
                booleanBuilder.and(recruit_register.reGender.eq(gender)); // "남자" or "여자"만 필터링
            }
        }


        if ("연령무관".equals(age)) {
            // 연령무관이 선택되었을 때 필터 적용
            booleanBuilder.and(
                    recruit_register.reMinAge.eq("연령무관")  // reMinAge가 '연령무관'인 데이터만 필터링
                            .and(recruit_register.reMaxAge.eq("연령무관"))  // reMaxAge가 '연령무관'인 데이터만 필터링
            );
        } else {
            // 실제 연령이 선택된 경우
            if (age != null && !age.isEmpty()) {
                // 실제 연령을 기준으로 연령 범위 필터링
                booleanBuilder.and(
                        recruit_register.reMinAge.loe(age)  // reMinAge가 선택된 연령보다 작거나 같음
                                .and(recruit_register.reMaxAge.goe(age))  // reMaxAge가 선택된 연령보다 크거나 같음
                );
            }
        }


        query.where(booleanBuilder);
        query.where(recruit_register.recruitId.gt(0L));

        this.getQuerydsl().applyPagination(pageable, query);

        List<Recruit_Register> list = query.fetch();

        long count = query.fetchCount();

        return new PageImpl<>(list, pageable, count);
    }


}
