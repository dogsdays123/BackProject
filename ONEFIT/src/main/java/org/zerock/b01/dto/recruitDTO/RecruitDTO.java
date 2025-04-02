package org.zerock.b01.dto.recruitDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDTO {

    private Long recruitId;

    @NotEmpty
    private String reMainAddress;

    @NotEmpty
    private String reDetailAddress;

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

    @NotNull(message = "필수 입력 항목입니다.")
    private int reNumHiring;

    @NotEmpty
    private String reWorkDays;

    private String reDutyDays;

    @NotEmpty
    private String reWorkStartTime;

    @NotEmpty
    private String reWorkEndTime;

    private String reTimeNegotiable;

    @NotEmpty
    private String reSalaryType;

    @NotEmpty
    private String reSalaryValue;

    private String reSalaryCheckAgree; // 급여추가정보선택

    private String reSalaryCheckMeal; // 급여추가정보선택

    private String reSalaryCheckDuty; // 급여추가정보선택

    private String reSalaryCheckProb; // 급여추가정보선택

    private String reSalaryDetail;

    @NotEmpty
    private String reGender;

    @NotEmpty
    private String reAgeType; // 최소연령


    private String reMinAge;


    private String reMaxAge;

    @NotEmpty
    private String reJobHistory;

    @NotEmpty
    private String reEducation;

    private String rePreference;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @NotNull(message = "마감일은 필수 항목입니다.")
    private LocalDateTime reDeadline;

    private String reApplyMethodOnline; // 접수방법

    private String reApplyMethodEmail; // 접수방법

    private String reApplyMethodMsg; // 접수방법

    private String reApplyMethodTel; // 접수방법

    @NotEmpty
    private String reAdminName;

    @NotEmpty
    private String reAdminEmail;

    @NotEmpty
    private String reAdminPhone;

    private Business_Member business_member;

    public void setBusiness_member(Business_Member business_member) {
        this.business_member = business_member;
    }

    private List<String> fileNames;

    private List<RecruitImageDTO> recruitImages;


}
