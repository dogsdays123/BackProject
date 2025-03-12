package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.trainer.Trainer;
import java.time.LocalDate;

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
    private LocalDate regdate;

    @ManyToOne
    @JoinColumn(name = "re_id", nullable = false)
    private Recruit_Register recruit_register;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
}
