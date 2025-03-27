package org.zerock.b01.domain.trainer;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Trainer_Thumbnails implements Comparable<Trainer_Thumbnails> {
    @Id
    private String thumbnailUuid;
    private String imgname;
    private int ord;

    @ManyToOne
    @JoinColumn(name = "trainer_id", nullable = false)
    private Trainer trainer;

    @Override
    public int compareTo(Trainer_Thumbnails o) {
        return this.ord - o.ord;
    }

    public void changeTrainer(Trainer trainer) {
        this.trainer = trainer;
    }

    public void changeOrd(int ord) {
        this.ord = ord;
    }
}
