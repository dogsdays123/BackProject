package org.zerock.b01.dto.trainerDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.member.User_Member;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainerDTO {
    private Long trainer_id;
    private String title;
    private LocalDateTime make_date;
    private String academy_final;
    private String academy;
    private int career_period;
    private String career;
    private String license;
    private String prize;
    private String want_job;
    private String want_type;
    private String want_legion;
    private int want_time;
    private int want_day;
    private String want_day_type;
    private String want_sal_type;
    private String content;
    private User_Member user_member;
}
