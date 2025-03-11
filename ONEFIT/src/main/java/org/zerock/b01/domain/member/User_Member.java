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
    private Long user_id;

    @Column(length = 30)
    private String u_nickname;

    private LocalDateTime u_birthday;

    @Column(length = 20, nullable = false)
    private String u_social;

    @Column(length = 100)
    private String u_address;

    private Long u_resident;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;
}
