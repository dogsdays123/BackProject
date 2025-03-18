package org.zerock.b01.recurit.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.parameters.P;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.memberRepository.Business_MemberRepository;
import org.zerock.b01.repository.recruitRepository.RecruitRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class RecruitRepositoryTests {

    LocalDateTime date = LocalDateTime.now();

    @Autowired
    private RecruitRepository recruitRepository;

    @Autowired
    private Business_MemberRepository memberRepository;

    @Autowired
    private All_MemberRepository allMemberRepository;

    @BeforeEach
    public void setup() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            // 1. All_Member 먼저 생성
            All_Member allMember = All_Member.builder()
                    .all_id("ALL" + i)
                    .name("User " + i)
                    .email("user" + i + "@example.com")
                    .a_psw("password" + i)
                    .a_phone(100000 + i) // 전화번호 임시 값
                    .member_type("GENERAL")
                    .del(false)
                    .a_social(false)
                    .build();

            allMember = allMemberRepository.save(allMember); // 먼저 저장

            // 2. Business_Member 생성 (All_Member의 ID를 외래키로 사용)
            Business_Member businessMember = Business_Member.builder()
                    .b_name("Business " + i)
                    .b_address("Address " + i)
                    .b_regnum((long) i)
                    .b_exponent("Exponent " + i)
                    .b_phone((long)i) // 전화번호 형식 유지
                    .all_member(allMember) // 외래키 설정
                    .build();

            memberRepository.save(businessMember);
        });
    }

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1, 100).forEach(i -> {
            // 기존 Business_Member 가져오기 (1~10까지 순환)
            Business_Member businessMember = memberRepository.findById((long) ((i % 10) + 1)).orElse(null);

            if (businessMember == null) {
                log.warn("Business_Member가 존재하지 않습니다.");
                return;
            }

            Recruit_Register recruit_register = Recruit_Register.builder()
                    .re_admin_email("abc@abcd.com")
                    .re_admin_name("hong....")
                    .re_admin_phone("000-1234-5678")
                    .re_apply_method("온라인지원")
                    .re_company("mit fitness..")
                    .re_deadline(date)
                    .re_duty_days("1")
                    .re_education("고등학교졸업")
                    .re_gender("성별무관")
                    .re_industry("헬스/PT")
                    .reJobType("정규직")
                    .re_max_age("40")
                    .re_min_age("20")
                    .re_num_hiring(1)
                    .re_preference("전공자우대,관련자격증 보유 우대")
                    .re_salary_check("협의가능")
                    .re_salary_detail("기본급 100만원")
                    .reSalaryType("월급")
                    .re_salary_value("300")
                    .re_time_negotiable("1")
                    .re_title("mit휘트니스 트레이너 모집")
                    .re_work_days("월~금")
                    .re_work_end_time("15:00")
                    .re_work_start_time("09:00")
                    .business_member(businessMember) // 외래키 설정
                    .build();

            Recruit_Register result = recruitRepository.save(recruit_register);
//            log.info("recurit_id: " + result.getRecruit_id());
        });
    }

    @Transactional(readOnly = true)
    @Test
    public void testFindAll() {
        List<Recruit_Register> list = recruitRepository.findAll();
        log.info("총 데이터 개수: " + list.size());
        list.forEach(log::info);
    }

    @Transactional(readOnly = true)
    @Test
    public void testSelect() {
        Long rid = 10L;

        Optional<Recruit_Register> result = recruitRepository.findById(rid);

        Recruit_Register recruit_register = result.orElseThrow();

        log.info(recruit_register);

    }


    @Test
    public void testUpdate(){
        Long rid = 2L;
        Optional<Recruit_Register> result = recruitRepository.findById(rid);

        // 레코드가 존재하지 않으면 커스텀 예외를 던지거나 적절히 처리
        Recruit_Register recruit_register = result.orElseThrow();

        // 값 변경
        recruit_register.change("브랜드 휘트니스", "새로운 회사", "정규직");

        // 엔티티 저장
        recruitRepository.save(recruit_register);

    }

    @Test
    public void testDelete(){
        Long rid = 1L;
        recruitRepository.deleteById(rid);
    }


    @Transactional(readOnly = true)
    @Test
    public void testPaging(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("recruitId").descending());

        Page<Recruit_Register> result = recruitRepository.findAll(pageable);

        log.info("total count : " + result.getTotalElements());
        log.info("total pages : " + result.getTotalPages());
        log.info("page number : " + result.getNumber());
        log.info("page size : " + result.getSize());

        List<Recruit_Register> recruitList = result.getContent();

        recruitList.forEach(recruitRegister -> log.info(recruitRegister));
    }

    @Test
    public void testSearch1(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("recruitId").descending());
        recruitRepository.search1(pageable);
    }

    @Transactional
    @Test
    public void testSearchAll(){
        String[] types = {"t", "c", "w"};

        String keyword = "M";

        Pageable pageable = PageRequest.of(0, 10, Sort.by("recruitId").descending());

        Page<Recruit_Register> result = recruitRepository.searchAll(types, keyword, pageable);

        log.info(result.getTotalElements());

        log.info(result.getSize());

        log.info(result.getNumber());

        log.info(result.hasPrevious() + ":" + result.hasNext());

        result.getContent().forEach(recruitRegister -> log.info(recruitRegister));
    }
}
