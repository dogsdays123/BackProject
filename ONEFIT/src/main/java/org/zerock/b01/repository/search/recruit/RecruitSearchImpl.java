package org.zerock.b01.repository.search.recruit;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.security.core.parameters.P;
import org.zerock.b01.domain.recruit.QRecruit_Register;
import org.zerock.b01.domain.recruit.QRecruit_Register_Image;
import org.zerock.b01.domain.recruit.Recruit_Register;
import com.querydsl.core.types.dsl.NumberTemplate;
import org.zerock.b01.dto.recruitDTO.RecruitImageDTO;
import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public Page<Recruit_Register> searchAll1(String[] types, String keyword, String gender, String age, String jobTypeFull, String jobTypePart,
                                            String jobTypeFree, String jobTypeTrainee, String jobTypeAlba, String workDays,
                                            String dutyDays, String startTime, String endTime, String timeNegotiable, String industry, String regDateFilter,
                                             String addressDoFilter, String addressCityFilter, Pageable pageable) {

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
            query.where(booleanBuilder);

        }

        if (regDateFilter != null && !regDateFilter.isEmpty()) {
            LocalDateTime today = LocalDateTime.now();
            LocalDate todayDate = today.toLocalDate(); // LocalDate만 추출
            DateTimePath<LocalDateTime> regDatePath = recruit_register.regDate;

            switch (regDateFilter) {
                case "today":
                    // todayDate와 regDate의 날짜를 비교
                    booleanBuilder.and(regDatePath.goe(todayDate.atStartOfDay()).and(regDatePath.lt(todayDate.plusDays(1).atStartOfDay())));
                    break;
                case "3days":
                    LocalDateTime threeDaysAgo = today.minusDays(3); // 3일 전
                    booleanBuilder.and(regDatePath.goe(threeDaysAgo));
                    break;
                case "7days":
                    LocalDateTime sevenDaysAgo = today.minusDays(7); // 7일 전
                    booleanBuilder.and(regDatePath.goe(sevenDaysAgo));
                    break;
            }
        }

        if(addressDoFilter != null && !addressDoFilter.isEmpty()) {
            booleanBuilder.and(recruit_register.reMainAddress.startsWith(addressDoFilter));
        }
        if(addressCityFilter != null && !addressCityFilter.isEmpty()) {
            booleanBuilder.and(recruit_register.reMainAddress.contains(addressCityFilter));
        }

        if (gender != null && !gender.isEmpty()) {
            if (!"성별무관".equals(gender)) {
                booleanBuilder.and(recruit_register.reGender.eq(gender)); // "남자" or "여자"만 필터링
            }
        }

        if (jobTypeFull != null && !jobTypeFull.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeFull.eq(jobTypeFull));
        }

        if (jobTypePart != null && !jobTypePart.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypePart.eq(jobTypePart));
        }

        if (jobTypeFree != null && !jobTypeFree.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeFree.eq(jobTypeFree));
        }

        if (jobTypeTrainee != null && !jobTypeTrainee.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeTrainee.eq(jobTypeTrainee));
        }

        if (jobTypeAlba != null && !jobTypeAlba.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeAlba.eq(jobTypeAlba));
        }

        if(workDays != null && !workDays.isEmpty()) {
            booleanBuilder.or(recruit_register.reWorkDays.eq(workDays));
        }

        if (dutyDays != null && !dutyDays.isEmpty()) {
            if ("null".equals(dutyDays)) {
                dutyDays = null;
            }

            // DB에서 null 값과 비교
            if (dutyDays == null) {
                booleanBuilder.and(recruit_register.reDutyDays.isNull());  // DB에서 null인 값만 필터링
            } else {
                booleanBuilder.and(recruit_register.reDutyDays.eq(dutyDays));  // 실제 값이 있을 경우
            }

        }

        if(timeNegotiable != null && !timeNegotiable.isEmpty()) {
            booleanBuilder.and(recruit_register.reTimeNegotiable.eq(timeNegotiable));
        }

        if(startTime != null && !startTime.isEmpty()) {
            booleanBuilder.and(recruit_register.reWorkStartTime.eq(startTime));
        }

        if(endTime != null && !endTime.isEmpty()) {
            booleanBuilder.and(recruit_register.reWorkEndTime.eq(endTime));
        }

        if(industry != null && !industry.isEmpty()) {
            booleanBuilder.and(recruit_register.reIndustry.eq(industry));
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

    @Override
    public Page<RecruitListAllDTO> searchAll(String[] types, String keyword, String gender, String age, String jobTypeFull, String jobTypePart,
                                             String jobTypeFree, String jobTypeTrainee, String jobTypeAlba, String workDays,
                                             String dutyDays, String startTime, String endTime, String timeNegotiable, String industry,
                                             String regDateFilter,  Pageable pageable) {

        QRecruit_Register recruit_register = QRecruit_Register.recruit_Register;
        QRecruit_Register_Image image = QRecruit_Register_Image.recruit_Register_Image;

//        JPQLQuery<Recruit_Register> query = from(recruit_register);
        JPQLQuery<Recruit_Register> query = from(recruit_register)
                .leftJoin(recruit_register.imageSet, image).fetchJoin();

        query.groupBy(recruit_register);
        getQuerydsl().applyPagination(pageable, query);

        List<Recruit_Register> resultList = query.fetch();
        List<RecruitListAllDTO> dtoList = resultList.stream().map(recruitRegister -> {

            RecruitListAllDTO dto = RecruitListAllDTO.builder()
                    .recruitId(recruitRegister.getRecruitId())
                    .reTitle(recruitRegister.getReTitle())
                    .reCompany(recruitRegister.getReCompany())
                    .reJobTypeFull(recruitRegister.getReJobTypeFull())
                    .reJobTypePart(recruitRegister.getReJobTypePart())
                    .reJobTypeFree(recruitRegister.getReJobTypeFree())
                    .reJobTypeTrainee(recruitRegister.getReJobTypeTrainee())
                    .reJobTypeAlba(recruitRegister.getReJobTypeAlba())
                    .reIndustry(recruitRegister.getReIndustry())
                    .reNumHiring(recruitRegister.getReNumHiring())
                    .reWorkDays(recruitRegister.getReWorkDays())
                    .reDutyDays(recruitRegister.getReDutyDays())
                    .reWorkStartTime(recruitRegister.getReWorkStartTime())
                    .reWorkEndTime(recruitRegister.getReWorkEndTime())
                    .reTimeNegotiable(recruitRegister.getReTimeNegotiable())
                    .reSalaryType(recruitRegister.getReSalaryType())
                    .reSalaryValue(recruitRegister.getReSalaryValue())
                    .reSalaryCheckAgree(recruitRegister.getReSalaryCheckAgree())
                    .reSalaryCheckMeal(recruitRegister.getReSalaryCheckMeal())
                    .reSalaryCheckDuty(recruitRegister.getReSalaryCheckDuty())
                    .reSalaryCheckProb(recruitRegister.getReSalaryCheckProb())
                    .reSalaryDetail(recruitRegister.getReSalaryDetail())
                    .reGender(recruitRegister.getReGender())
                    .reAgeType(recruitRegister.getReAgeType())
                    .reMinAge(recruitRegister.getReMinAge())
                    .reMaxAge(recruitRegister.getReMaxAge())
                    .reJobHistory(recruitRegister.getReJobHistory())
                    .reEducation(recruitRegister.getReEducation())
                    .rePreference(recruitRegister.getRePreference())
                    .reDeadline(recruitRegister.getReDeadline())
                    .reApplyMethodEmail(recruitRegister.getReApplyMethodEmail())
                    .reApplyMethodOnline(recruitRegister.getReApplyMethodOnline())
                    .reApplyMethodMsg(recruitRegister.getReApplyMethodMsg())
                    .reApplyMethodTel(recruitRegister.getReApplyMethodTel())
                    .reAdminName(recruitRegister.getReAdminName())
                    .reAdminEmail(recruitRegister.getReAdminEmail())
                    .reAdminPhone(recruitRegister.getReAdminPhone())
                    .business_member(recruitRegister.getBusiness_member()).build();

            List<RecruitImageDTO> imageDTOS = recruitRegister.getImageSet().stream().sorted()
                    .map(recruitRegisterImage -> RecruitImageDTO.builder()
                            .re_img_id(recruitRegisterImage.getRe_img_id())
                            .re_img_title(recruitRegisterImage.getRe_img_title())
                            .re_img_ord(recruitRegisterImage.getRe_img_ord())
                            .build()).collect(Collectors.toList());

            dto.setRecruitImages(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

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
            query.where(booleanBuilder);

        }
        if (regDateFilter != null && !regDateFilter.isEmpty()) {
            LocalDateTime today = LocalDateTime.now();
            LocalDate todayDate = today.toLocalDate(); // LocalDate만 추출
            DateTimePath<LocalDateTime> regDatePath = recruit_register.regDate;

            switch (regDateFilter) {
                case "today":
                    // todayDate와 regDate의 날짜를 비교
                    booleanBuilder.and(regDatePath.goe(todayDate.atStartOfDay()).and(regDatePath.lt(todayDate.plusDays(1).atStartOfDay())));
                    break;
                case "3days":
                    LocalDateTime threeDaysAgo = today.minusDays(3); // 3일 전
                    booleanBuilder.and(regDatePath.goe(threeDaysAgo));
                    break;
                case "7days":
                    LocalDateTime sevenDaysAgo = today.minusDays(7); // 7일 전
                    booleanBuilder.and(regDatePath.goe(sevenDaysAgo));
                    break;
            }
        }

        if (gender != null && !gender.isEmpty()) {
            if (!"성별무관".equals(gender)) {
                booleanBuilder.and(recruit_register.reGender.eq(gender)); // "남자" or "여자"만 필터링
            }
        }

        if (jobTypeFull != null && !jobTypeFull.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeFull.eq(jobTypeFull));
        }

        if (jobTypePart != null && !jobTypePart.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypePart.eq(jobTypePart));
        }

        if (jobTypeFree != null && !jobTypeFree.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeFree.eq(jobTypeFree));
        }

        if (jobTypeTrainee != null && !jobTypeTrainee.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeTrainee.eq(jobTypeTrainee));
        }

        if (jobTypeAlba != null && !jobTypeAlba.isEmpty()) {
            booleanBuilder.and(recruit_register.reJobTypeAlba.eq(jobTypeAlba));
        }

        if(workDays != null && !workDays.isEmpty()) {
            booleanBuilder.or(recruit_register.reWorkDays.eq(workDays));
        }

        if (dutyDays != null && !dutyDays.isEmpty()) {
            if ("null".equals(dutyDays)) {
                dutyDays = null;
            }

            // DB에서 null 값과 비교
            if (dutyDays == null) {
                booleanBuilder.and(recruit_register.reDutyDays.isNull());  // DB에서 null인 값만 필터링
            } else {
                booleanBuilder.and(recruit_register.reDutyDays.eq(dutyDays));  // 실제 값이 있을 경우
            }

        }

        if(timeNegotiable != null && !timeNegotiable.isEmpty()) {
            booleanBuilder.and(recruit_register.reTimeNegotiable.eq(timeNegotiable));
        }

        if(startTime != null && !startTime.isEmpty()) {
            booleanBuilder.and(recruit_register.reWorkStartTime.eq(startTime));
        }

        if(endTime != null && !endTime.isEmpty()) {
            booleanBuilder.and(recruit_register.reWorkEndTime.eq(endTime));
        }

        if(industry != null && !industry.isEmpty()) {
            booleanBuilder.and(recruit_register.reIndustry.eq(industry));
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

//        List<Recruit_Register> list = query.fetch();

        resultList.forEach(board1 ->{
            System.out.println(board1.getRecruitId());
            System.out.println(board1.getImageSet());
            System.out.println("-----------------------");
        });

        long count = query.fetchCount();

        return new PageImpl<>(dtoList, pageable, count);
    }

}
