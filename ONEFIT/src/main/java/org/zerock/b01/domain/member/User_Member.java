package org.zerock.b01.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 30)
    private String uNickname;

    private LocalDateTime uBirthday;

    @Column(length = 20, nullable = false)
    private String uSocial;

    @Column(length = 100)
    private String uAddress;

    private Long uResident;

    @ManyToOne
    @JoinColumn(name = "allId", nullable = false)
    private All_Member all_member;
}
