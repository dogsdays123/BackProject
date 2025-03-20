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

    private String reJobTypeFull; // 고용형태(정규직, 프리, 파트)

    private String reJobTypePart; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeFree; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeTrainee; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeAlba; // 고용형태(정규직, 프리, 파트)

    private String reIndustry;

    private int reNumHiring;

    private String reWorkDays;

    private String reDutyDays;

    private String reWorkStartTime;


    private String reWorkEndTime;


    private String reTimeNegotiable;


    private String reSalaryType;


    private String reSalaryValue;

    private String reSalaryCheckAgree; // 급여추가정보선택

    private String reSalaryCheckMeal; // 급여추가정보선택

    private String reSalaryCheckDuty; // 급여추가정보선택

    private String reSalaryCheckProb; // 급여추가정보선택

    private String reSalaryDetail;


    private String reGender;

    private String reAgeType;

    private String reMinAge;


    private String reMaxAge;


    private String reJobHistory;


    private String reEducation;

    private String rePreference;

    private LocalDateTime regDate;

    private LocalDateTime modDate;


    private LocalDateTime reDeadline;

    private String reApplyMethodOnline; // 접수방법

    private String reApplyMethodEmail; // 접수방법

    private String reApplyMethodMsg; // 접수방법

    private String reApplyMethodTel; // 접수방법

    private String reAdminName;

    private String reAdminEmail;

    private String reAdminPhone;

    private Business_Member business_member;
}
