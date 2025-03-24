package org.zerock.b01.domain.trainer;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.member.User_Member;

import java.util.HashSet;
import java.util.List;
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

    public void changeMainInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void changeAcademyInfo(String academyFinal, String academy) {
        this.academyFinal = academyFinal;
        this.academy = academy;
    }

    public void changeCareerInfo(int careerPeriod, String career) {
        this.careerPeriod = careerPeriod;
        this.career = career;
    }

    public void changeOtherInfo(String license, String prize) {
        this.license = license;
        this.prize = prize;
    }

    public void changeWantsInfo(String wantJob, String wantType, String wantLegion, Double wantTime,
                                int wantDay, String wantDayType, String wantSalType, int wantSal) {
        this.wantJob = wantJob;
        this.wantType = wantType;
        this.wantLegion = wantLegion;
        this.wantTime = wantTime;
        this.wantDay = wantDay;
        this.wantDayType = wantDayType;
        this.wantSalType = wantSalType;
        this.wantSal = wantSal;
    }

    public void addImage(String thumbnailUuid, String imgname) {
        Trainer_Thumbnails image = Trainer_Thumbnails.builder()
                .thumbnailUuid(thumbnailUuid)
                .imgname(imgname)
                .trainer(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(image);
    }

    public void clearImages() {
        imageSet.forEach(trainerThumbnails -> trainerThumbnails.changeTrainer(null));
        this.imageSet.clear();
    }
}
