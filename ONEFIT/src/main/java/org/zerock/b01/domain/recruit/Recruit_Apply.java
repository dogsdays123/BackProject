package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.trainer.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Apply extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reApplyId;

//    @Column(nullable = false)
//    private LocalDateTime regdate;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit_Register recruitRegister;

//    @ManyToOne
//    @JoinColumn(name = "trainer_id", nullable = false)
//    private Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User_Member userMember;

    @ManyToOne
    @JoinColumn(name = "business_id")
    private Business_Member businessMember;
}
