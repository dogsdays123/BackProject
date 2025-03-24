package org.zerock.b01.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDate;

@Entity
@Table(name = "User_Member", indexes = {
        @Index(name = "idx_user_member_no", columnList = "all_Member_allId")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "allMember")
public class User_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(length = 30)
    private String uNickname;

    private LocalDate uBirthday;

    @Column(length = 100)
    private String uAddress;

    @Column(length = 100)
    private String uAddressExtra;

    private Long uResident;

    @ManyToOne(fetch = FetchType.LAZY)
    private All_Member allMember;
}
