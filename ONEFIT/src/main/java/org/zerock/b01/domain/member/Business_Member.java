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
public class Business_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long business_id;

    @Column(length = 100, nullable = false)
    private String b_address;

    @Column(length = 30, nullable = false)
    private String b_name;

    @Column(nullable = false)
    private Long b_regnum;

    @Column(length = 30, nullable = false)
    private String b_exponent;

    @Column(nullable = false)
    private Long b_phone;

    private Long b_average;

    private Long b_employees;

    private Long b_assets;

    @Column(length = 10)
    private String b_size;

    private LocalDateTime b_esta_date;

    @Column(length = 30)
    private String b_homepage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;
}
