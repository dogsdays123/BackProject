package org.zerock.b01.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class All_Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long all_id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String email;

    @Column(length = 10, nullable = false)
    private String a_member_id;

    @Column(length = 20, nullable = false)
    private String a_psw;

    private Long a_phone;

    private boolean a_social;

    @Column(length = 10, nullable = false)
    private String roles;

    public void change(String name, String email){
        this.name = name;
        this.email = email;
    }
}
