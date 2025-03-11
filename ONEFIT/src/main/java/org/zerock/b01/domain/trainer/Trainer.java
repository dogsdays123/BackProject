package org.zerock.b01.domain.trainer;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.member.User_Member;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trainer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainer_id;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(nullable = false)
    private LocalDateTime make_date;

    @Column(length = 80, nullable = false)
    private String academy_final;

    @Column(length = 400, nullable = false)
    private String academy;

    @Column(nullable = false)
    private int career_period;

    @Column(length = 400)
    private String career;

    @Column(length = 400)
    private String license;

    @Column(length = 400)
    private String prize;

    @Column(length = 80, nullable = false)
    private String want_job;

    @Column(length = 80, nullable = false)
    private String want_type;

    @Column(length = 80, nullable = false)
    private String want_legion;

    @Column(nullable = false)
    private int want_time;

    @Column(nullable = false)
    private int want_day;

    @Column(length = 40, nullable = false)
    private String want_sal_type;

    @Column(nullable = false)
    private int want_sal;

    @Column(length = 2000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User_Member user_member;
}
