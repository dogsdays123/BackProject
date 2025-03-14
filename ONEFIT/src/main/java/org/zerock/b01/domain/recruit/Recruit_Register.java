package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.member.Business_Member;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recruit_id;

    @Column(length = 170, nullable = false)
    private String re_title;

    @Column(length = 130, nullable = false)
    private String re_company;

    @Column(length = 50, nullable = false)
    private String re_job_type;

    @Column(length = 100, nullable = false)
    private String re_industry;

    @Column(nullable = false)
    private int re_num_hiring;

    @Column(length = 50, nullable = false)
    private String re_work_days;

    @Column(length = 50, nullable = false)
    private String re_work_hours;

    private Integer re_salary;

    @Column(length = 255)
    private String re_salary_detail;

    @Column(length = 10, nullable = false)
    private String re_gender;

    @Column(length = 50, nullable = false)
    private String re_age;

    @Column(length = 50, nullable = false)
    private String re_education;

    @Column(length = 255)
    private String re_preference;

    @Column(nullable = false)
    private LocalDate regdate;

    @Column(nullable = false)
    private LocalDate re_deadline;

    @Column(length = 100, nullable = false)
    private String re_apply_method;

    @Column(nullable = false)
    private String re_admin_phone;

    @Column(length = 100, nullable = false)
    private String re_admin_name;

    @Column(length = 255, nullable = false)
    private String re_admin_email;

    @ManyToOne
    @JoinColumn(name = "business_id", nullable = false)
    private Business_Member business_member;
}
