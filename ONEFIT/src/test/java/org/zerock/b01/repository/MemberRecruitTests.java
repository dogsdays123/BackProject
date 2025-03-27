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
                .reAdminEmail("456")
                .reAdminName("456")
                .reAdminPhone("456")
                .reCompany("456")
                .reDeadline(LocalDateTime.now())
                .reEducation("456")
                .reGender("456")
                .reIndustry("456")
                .reJobHistory("456")
                .reMaxAge("456")
                .reMinAge("456")
                .reNumHiring(456)
                .reSalaryType("456")
                .reSalaryValue("456")
                .reTitle("456")
                .reWorkDays("456")
                .reWorkEndTime("456")
                .reWorkStartTime("456")
                .reAgeType("456")
                .business_member(business_Member)
                .build();

        recruitRepository.save(rr);
    }
}
