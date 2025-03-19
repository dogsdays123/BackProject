package org.zerock.b01.dto.recruitDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDTO {

    private Long recruitId;

    private String reTitle;

    private String reCompany;

    private String reJobType;

    private String reIndustry;


    private int reNumHiring;


    private String reWorkDays;

    private String reDutyDays;


    private String reWorkStartTime;


    private String reWorkEndTime;


    private String reTimeNegotiable;


    private String reSalaryType;


    private String reSalaryValue;

    private String reSalaryCheck;

    private String reSalaryDetail;


    private String reGender;


    private String reMinAge;


    private String reMaxAge;


    private String reJobHistory;


    private String reEducation;

    private String rePreference;

    private LocalDateTime regDate;

    private LocalDateTime modDate;


    private LocalDateTime reDeadline;


    private String reApplyMethod;


    private String reAdminName;


    private String reAdminEmail;


    private String reAdminPhone;

    private Business_Member business_member;
}
