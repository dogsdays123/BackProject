package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Register extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recruit_id")
    private Long recruitId; // 채용공고 ID

    @Column(length = 170, nullable = false)
    private String reTitle; // 공고제목

    @Column(length = 130, nullable = false)
    private String reCompany; // 회사명(근무지)

    @Column(name = "re_job_type" ,length = 50, nullable = false)
    private String reJobType; // 고용형태(정규직, 프리, 파트)

    @Column(length = 100, nullable = false)
    private String reIndustry; // 업직종(헬스/PT , 필라테스)

    @Column(nullable = false)
    private int reNumHiring; // 모집인원(00명)

    @Column(length = 50, nullable = false)
    private String reWorkDays; // 근무요일(월~금, 월~토)

    @Column(length = 10)
    private String reDutyDays;

    @Column(length = 50, nullable = false)
    private String reWorkStartTime;

    @Column(length = 50, nullable = false)
    private String reWorkEndTime;

    @Column(length = 10, nullable = false)
    private String reTimeNegotiable;

    @Column(name="re_salary_type", length = 50, nullable = false)
    private String reSalaryType;

    @Column(length = 50, nullable = false)
    private String reSalaryValue;

    @Column(length = 255)
    private String reSalaryCheck;

    @Column(length = 255)
    private String reSalaryDetail;

    @Column(length = 10, nullable = false)
    private String reGender;

    @Column(length = 50, nullable = false)
    private String reMinAge;

    @Column(length = 50, nullable = false)
    private String reMaxAge;

    @Column(length = 50, nullable = false)
    private String reEducation;

    @Column(length = 255)
    private String rePreference;

//    @Column(nullable = false)
//    private LocalDateTime regDate;

    @Column(nullable = false)
    private LocalDateTime reDeadline;

    @Column(length = 100, nullable = false)
    private String reApplyMethod;

    @Column(length = 100, nullable = false)
    private String reAdminName;

    @Column(length = 255, nullable = false)
    private String reAdminEmail;

    @Column(nullable = false)
    private String reAdminPhone;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business_Member business_member;

    public void change(String title, String company, String jobType) {
        this.reTitle = title;
        this.reCompany = company;
        this.reJobType = jobType;
    }
}
