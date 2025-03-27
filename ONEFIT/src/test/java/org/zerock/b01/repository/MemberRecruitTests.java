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
                .reAdminEmail("789")
                .reAdminName("789")
                .reAdminPhone("789")
                .reCompany("789")
                .reDeadline(LocalDateTime.now())
                .reEducation("789")
                .reGender("789")
                .reIndustry("789")
                .reJobHistory("789")
                .reMaxAge("789")
                .reMinAge("789")
                .reNumHiring(789)
                .reSalaryType("789")
                .reSalaryValue("789")
                .reTitle("789")
                .reWorkDays("789")
                .reWorkEndTime("789")
                .reWorkStartTime("789")
                .reAgeType("789")
                .reDetailAddress("789")
                .reMainAddress("123")
                .business_member(business_Member)
                .build();

        recruitRepository.save(rr);
    }
}
