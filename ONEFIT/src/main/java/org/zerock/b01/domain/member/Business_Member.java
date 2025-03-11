package org.zerock.b01.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;

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

    private Long b_regnum;

    private Long b_average;

    private Long b_employees;

    private Long b_assets;

    @Column(length = 10)
    private String b_size;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;
}
