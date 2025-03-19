package org.zerock.b01.domain.trainer;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.member.User_Member;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "imageSet")
public class Trainer extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trainerId;

    @Column(length = 120, nullable = false)
    private String title;

    @Column(length = 80, nullable = false)
    private String academyFinal;

    @Column(length = 400, nullable = false)
    private String academy;

    @Column(nullable = false)
    private int careerPeriod;

    @Column(length = 400)
    private String career;

    @Column(length = 400)
    private String license;

    @Column(length = 400)
    private String prize;

    @Column(length = 80, nullable = false)
    private String wantJob;

    @Column(length = 80, nullable = false)
    private String wantType;

    @Column(length = 80, nullable = false)
    private String wantLegion;

    @Column(nullable = false)
    private Double wantTime;

    @Column(nullable = false)
    private int wantDay;

    @Column(nullable = false)
    private String wantDayType;

    @Column(length = 40, nullable = false)
    private String wantSalType;

    @Column(nullable = false)
    private int wantSal;

    @Column(length = 2000, nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User_Member userMember;

    @OneToMany(mappedBy = "trainer", cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, orphanRemoval = true)
    @Builder.Default
    private Set<Trainer_Thumbnails> imageSet = new HashSet<>();
}
