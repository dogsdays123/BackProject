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

    @Column(length = 50, nullable = false)
    private String reJobType; // 고용형태(정규직, 프리, 파트)

    @Column(length = 100, nullable = false)
    private String reIndustry; // 업직종(헬스/PT , 필라테스)

    @Column(nullable = false)
    private int reNumHiring; // 모집인원(00명)

    @Column(length = 50, nullable = false)
    private String reWorkDays; // 근무요일(월~금, 월~토)

    @Column(length = 10)
    private String reDutyDays; // 주말당직여부

    @Column(length = 50, nullable = false)
    private String reWorkStartTime; // 근무시작시간

    @Column(length = 50, nullable = false)
    private String reWorkEndTime; // 근무종료시간

    @Column(length = 10, nullable = false)
    private String reTimeNegotiable; // 시간협의가능여부

    @Column(length = 50, nullable = false)
    private String reSalaryType; // 급여종류

    @Column(length = 50, nullable = false)
    private String reSalaryValue; // 급여

    @Column(length = 255)
    private String reSalaryCheck; // 급여추가정보선택

    @Column(length = 255)
    private String reSalaryDetail; // 급여상세정보

    @Column(length = 10, nullable = false)
    private String reGender; //성별

    @Column(length = 50, nullable = false)
    private String reMinAge; // 최소연령

    @Column(length = 50, nullable = false)
    private String reMaxAge; // 최대연령

    @Column(length = 50, nullable = false)
    private String reJobHistory; // 경력조건

    @Column(length = 50, nullable = false)
    private String reEducation; // 학력조건

    @Column(length = 255)
    private String rePreference; // 필수/우대조건

//    @Column(nullable = false)
//    private LocalDateTime regDate;

    @Column(nullable = false)
    private LocalDateTime reDeadline; // 접수마감일

    @Column(length = 100, nullable = false)
    private String reApplyMethod; // 접수방법

    @Column(length = 100, nullable = false)
    private String reAdminName; // 담당자이름

    @Column(length = 255, nullable = false)
    private String reAdminEmail; // 담당자이메일

    @Column(nullable = false)
    private String reAdminPhone; // 담당자전화번호

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business_Member business_member;

    public void change(String title, String company, String jobType) {
        this.reTitle = title;
        this.reCompany = company;
        this.reJobType = jobType;
    }
}
