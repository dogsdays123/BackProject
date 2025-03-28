package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.trainer.Trainer;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Apply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long re_apply_id;

    @Column(nullable = false)
    private LocalDateTime regdate;

    @ManyToOne
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit_Register recruit_register;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
}
