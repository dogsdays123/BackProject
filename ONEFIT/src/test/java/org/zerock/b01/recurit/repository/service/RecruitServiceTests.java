package org.zerock.b01.recurit.repository.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.memberRepository.Business_MemberRepository;
import org.zerock.b01.service.recruitService.RecruitService;

import java.time.LocalDateTime;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class RecruitServiceTests {


    LocalDateTime date = LocalDateTime.now();

    @Autowired
    private RecruitService recruitService;

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
    public void testRegister(){
        log.info(recruitService.getClass().getName());
        IntStream.rangeClosed(1, 5).forEach(i -> {
            // 기존 Business_Member 가져오기 (1~10까지 순환)
            Business_Member businessMember = memberRepository.findById((long) ((i % 10) + 1)).orElse(null);

            if (businessMember == null) {
                log.warn("Business_Member가 존재하지 않습니다.");
                return;
            }

            RecruitDTO recruitDTO = RecruitDTO.builder()
                    .reAdminEmail("abc@abcd.com")
                    .reAdminName("hong....")
                    .reAdminPhone("010-1234-5678")
                    .reApplyMethod("온라인지원")
                    .reCompany("mit fitness..")
                    .reDeadline(date)
                    .reDutyDays("1")
                    .reEducation("고등학교졸업")
                    .reGender("성별무관")
                    .reIndustry("헬스/PT")
                    .reJobType("정규직")
                    .reMaxAge("40")
                    .reMinAge("20")
                    .reNumHiring(1)
                    .rePreference("전공자우대,관련자격증 보유 우대")
                    .reSalaryCheck("협의가능")
                    .reSalaryDetail("기본급 100만원")
                    .reSalaryType("월급")
                    .reSalaryValue("300")
                    .reTimeNegotiable("1")
                    .reTitle("sample title...")
                    .reWorkDays("월~금")
                    .reWorkEndTime("15:00")
                    .reWorkStartTime("09:00")
                    .business_member(businessMember)// 외래키 설정
                    .build();

            Long recruitId = recruitService.register(recruitDTO);

            log.info("recruitId: " + recruitId);
        });
    }

    @Test
    public void testModify(){

        RecruitDTO recruitDTO = RecruitDTO.builder()
                .recruitId(415L)
                .reTitle("Update Title....")
                .reCompany("Update Content....")
                .reJobType("Pilates").build();

        recruitService.modify(recruitDTO);
    }

    @Transactional
    @Test
    public void testList(){

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("u")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<RecruitDTO> responseDTO = recruitService.list(pageRequestDTO);

        log.info("responseDTO: " + responseDTO);
    }
}