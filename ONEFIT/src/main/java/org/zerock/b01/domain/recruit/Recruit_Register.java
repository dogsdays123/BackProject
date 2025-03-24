package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Register extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruitId; // 채용공고 ID

    @Column(length = 170, nullable = false)
    private String reTitle; // 공고제목

    @Column(length = 130, nullable = false)
    private String reCompany; // 회사명(근무지)

    @Column(length = 10)
    private String reJobTypeFull; // 고용형태(정규직, 프리, 파트)

    @Column(length = 10)
    private String reJobTypePart; // 고용형태(정규직, 프리, 파트)

    @Column(length = 10)
    private String reJobTypeFree; // 고용형태(정규직, 프리, 파트)

    @Column(length = 10)
    private String reJobTypeTrainee; // 고용형태(정규직, 프리, 파트)

    @Column(length = 10)
    private String reJobTypeAlba; // 고용형태(정규직, 프리, 파트)

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

    @Column(length = 10)
    private String reTimeNegotiable; // 시간협의가능여부

    @Column(length = 50, nullable = false)
    private String reSalaryType; // 급여종류

    @Column(length = 50, nullable = false)
    private String reSalaryValue; // 급여

    @Column(length = 10)
    private String reSalaryCheckAgree; // 급여추가정보선택

    private String reSalaryCheckMeal; // 급여추가정보선택

    @Column(length = 10)
    private String reSalaryCheckDuty; // 급여추가정보선택

    @Column(length = 10)
    private String reSalaryCheckProb; // 급여추가정보선택

    @Column(length = 255)
    private String reSalaryDetail; // 급여상세정보

    @Column(length = 10, nullable = false)
    private String reGender; //성별

    @Column(length = 10, nullable = false)
    private String reAgeType;


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

    @Column(length = 10)
    private String reApplyMethodOnline; // 접수방법

    @Column(length = 10)
    private String reApplyMethodEmail; // 접수방법

    @Column(length = 10)
    private String reApplyMethodMsg; // 접수방법

    @Column(length = 10)
    private String reApplyMethodTel; // 접수방법

    @Column(length = 100, nullable = false)
    private String reAdminName; // 담당자이름

    @Column(length = 255, nullable = false)
    private String reAdminEmail; // 담당자이메일

    @Column(nullable = false)
    private String reAdminPhone; // 담당자전화번호

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business_Member business_member;

    public void change(String title, String company, String reJobTypeFull, String reJobTypePart, String reJobTypeFree, String reJobTypeTrainee
    , String reJobTypeAlba, String reIndustry, int reNumHiring, String reWorkDays, String reDutyDays, String reWorkStartTime, String reWorkEndTime,
                       String reTimeNegotiable, String reSalaryType, String reSalaryValue, String reSalaryCheckAgree, String reSalaryCheckMeal
    ,String reSalaryCheckDuty, String reSalaryCheckProb, String reSalaryDetail, String reGender, String reAgeType, String reMinAge, String reMaxAge
    ,String reJobHistory, String reEducation, String rePreference, LocalDateTime reDeadline, String reApplyMethodOnline, String reApplyMethodEmail
    ,String reApplyMethodMsg, String reApplyMethodTel, String reAdminName, String reAdminEmail, String reAdminPhone) {
        this.reTitle = title;
        this.reCompany = company;
        this.reJobTypeFull = reJobTypeFull;
        this.reJobTypePart = reJobTypePart;
        this.reJobTypeFree = reJobTypeFree;

        this.reJobTypeTrainee = reJobTypeTrainee;
        this.reJobTypeAlba = reJobTypeAlba;
        this.reIndustry = reIndustry;
        this.reNumHiring = reNumHiring;
        this.reWorkDays = reWorkDays;
        this.reDutyDays = reDutyDays;

        this.reWorkStartTime = reWorkStartTime;
        this.reWorkEndTime = reWorkEndTime;

        this.reTimeNegotiable = reTimeNegotiable;
        this.reSalaryType = reSalaryType;
        this.reSalaryValue = reSalaryValue;
        this.reSalaryCheckAgree = reSalaryCheckAgree;
        this.reSalaryCheckMeal = reSalaryCheckMeal;
        this.reSalaryCheckDuty = reSalaryCheckDuty;
        this.reSalaryCheckProb = reSalaryCheckProb;
        this.reSalaryDetail = reSalaryDetail;
        this.reGender = reGender;
        this.reAgeType = reAgeType;
        this.reMinAge = reMinAge;
        this.reMaxAge = reMaxAge;
        this.reJobHistory = reJobHistory;
        this.reEducation = reEducation;
        this.rePreference = rePreference;
        this.reDeadline = reDeadline;
        this.reApplyMethodOnline = reApplyMethodOnline;
        this.reApplyMethodEmail = reApplyMethodEmail;
        this.reApplyMethodMsg = reApplyMethodMsg;
        this.reApplyMethodTel = reApplyMethodTel;
        this.reAdminName = reAdminName;
        this.reAdminEmail = reAdminEmail;
        this.reAdminPhone = reAdminPhone;
    }

    @OneToMany(mappedBy = "recruit_register",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<Recruit_Register_Image> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName) {
        Recruit_Register_Image boardImage = Recruit_Register_Image.builder()
                .re_img_id(uuid)
                .re_img_title(fileName)
                .recruit_register(this)
                .re_img_ord(imageSet.size())
                .build();

        imageSet.add(boardImage);
    }

    public void clearImage() {
        imageSet.forEach(boardImage -> boardImage.changeRecruit(null));

        this.imageSet.clear();
    }
}
