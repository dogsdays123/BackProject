package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.repository.boardRepository.NoticeBoardRepository;
import org.zerock.b01.repository.recruitRepository.RecruitRepository;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;

import java.time.LocalDateTime;

@SpringBootTest
@Log4j2
public class MemberRecruitTests {

    @Autowired
    private Member_Set_Type_Service member_Set_Type_Service;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private RecruitRepository recruitRepository;

    @Test
    public void testInsert(){
        Business_MemberDTO business = member_Set_Type_Service.BusinessRead("test123");
        Business_Member business_Member = modelMapper.map(business, Business_Member.class);


        Recruit_Register rr = Recruit_Register.builder()
                .reAdminEmail("123")
                .reAdminName("123")
                .reAdminPhone("123")
                .reCompany("123")
                .reDeadline(LocalDateTime.now())
                .reEducation("123")
                .reGender("123")
                .reIndustry("123")
                .reJobHistory("123")
                .reMaxAge("123")
                .reMinAge("123")
                .reNumHiring(123)
                .reSalaryType("123")
                .reSalaryValue("123")
                .reTitle("123")
                .reWorkDays("123")
                .reWorkEndTime("123")
                .reWorkStartTime("123")
                .reAgeType("123")
                .business_member(business_Member)
                .build();

        recruitRepository.save(rr);
    }
}
