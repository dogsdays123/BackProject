package org.zerock.b01.domain.recruit;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "recruit_register")
public class Recruit_Register_Image implements Comparable<Recruit_Register_Image> {

    @Id
    @Column(length = 255)
    private String re_img_id;

    @Column(length = 255, nullable = false)
    private String re_img_title;

    @Column(nullable = false)
    private int re_img_ord;

    @ManyToOne
    @JoinColumn(name = "recruit_id")
    private Recruit_Register recruit_register;

    @Override
    public int compareTo(Recruit_Register_Image other){
        return this.re_img_ord - other.re_img_ord;
    }

    public void changeRecruit(Recruit_Register recruit_register){
        this.recruit_register = recruit_register;
    }
}
