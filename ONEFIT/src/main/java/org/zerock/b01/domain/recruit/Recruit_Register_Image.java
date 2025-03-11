package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Recruit_Register_Image {
    @Id
    @Column(length = 255)
    private String re_img_id;

    @Column(length = 255, nullable = false)
    private String re_img_title;

    @Column(nullable = false)
    private int re_img_ord;

    @ManyToOne
    @JoinColumn(name = "recruit_id", nullable = false)
    private Recruit_Register recruit_register;
}
