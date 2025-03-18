package org.zerock.b01.dto.recruitDTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

    private String re_title;

    private String re_company;

    private String reJobType;// Job type을 저장할 필드

    private String re_industry;

    private int re_num_hiring;

    private String re_work_days;

    private String re_duty_days;

    private String re_work_start_time;

    private String re_work_end_time;

    private String re_time_negotiable;

    private String reSalaryType;  // Salary type을 저장할 필드

    private String re_salary_value;

    private String re_salary_check;

    private String re_salary_detail;

    private String re_gender;

    private String re_min_age;

    private String re_max_age;

    private String re_education;

    private String re_preference;

    private LocalDateTime regdate;

    private LocalDateTime modDate;

    private LocalDateTime re_deadline;

    private String re_apply_method;

    private String re_admin_name;

    private String re_admin_email;

    private String re_admin_phone;

    private Business_Member business_member;
}
