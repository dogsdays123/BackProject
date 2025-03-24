package org.zerock.b01.dto.recruitDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecruitListAllDTO {

    private Long recruitId;

    @NotEmpty
    private String reTitle;

    @NotEmpty
    private String reCompany;

    private String reJobTypeFull; // 고용형태(정규직, 프리, 파트)

    private String reJobTypePart; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeFree; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeTrainee; // 고용형태(정규직, 프리, 파트)

    private String reJobTypeAlba; // 고용형태(정규직, 프리, 파트)

    @NotEmpty
    private String reIndustry;

    private int reNumHiring;

    @NotEmpty
    private String reWorkDays;

    private String reDutyDays;

    @NotEmpty
    private String reWorkStartTime;

    @NotEmpty
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

    private String reAgeType; // 최소연령

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

    private List<RecruitImageDTO> recruitImages;
}
