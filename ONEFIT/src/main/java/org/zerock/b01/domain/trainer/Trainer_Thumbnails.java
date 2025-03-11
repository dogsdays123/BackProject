package org.zerock.b01.domain.trainer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trainer_Thumbnails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tthumbnails_id;

    @Column(length = 100, nullable = false)
    private String imgname;

    @Column(nullable = false)
    private int ord;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;
}
