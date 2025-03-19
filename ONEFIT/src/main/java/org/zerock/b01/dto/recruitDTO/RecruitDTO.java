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

    @NotEmpty
    @Size(min = 5, max = 40)
    private String reTitle;

    @NotEmpty
    private String reCompany;

    @NotEmpty
    private String reJobType;

    @NotEmpty
    private String reIndustry;

    @NotEmpty
    private int reNumHiring;

    @NotEmpty
    private String reWorkDays;

    private String reDutyDays;

    @NotEmpty
    private String reWorkStartTime;

    @NotEmpty
    private String reWorkEndTime;

    @NotEmpty
    private String reTimeNegotiable;

    @NotEmpty
    private String reSalaryType;

    @NotEmpty
    private String reSalaryValue;

    private String reSalaryCheck;

    private String reSalaryDetail;

    @NotEmpty
    private String reGender;

    @NotEmpty
    private String reMinAge;

    @NotEmpty
    private String reMaxAge;

    @NotEmpty
    private String reJobHistory;

    @NotEmpty
    private String reEducation;

    private String rePreference;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    @NotEmpty
    private LocalDateTime reDeadline;

    @NotEmpty
    private String reApplyMethod;

    @NotEmpty
    private String reAdminName;

    @NotEmpty
    private String reAdminEmail;

    @NotEmpty
    private String reAdminPhone;

    private Business_Member business_member;
}
