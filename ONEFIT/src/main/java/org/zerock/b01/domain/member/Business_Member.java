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
    private Long businessId;

    @Column(length = 100, nullable = false)
    private String bAddress;

    @Column(length = 30, nullable = false)
    private String bName;

    @Column(nullable = false)
    private Long bRegnum;

    @Column(length = 30, nullable = false)
    private String bExponent;

    @Column(nullable = false)
    private Long bPhone;

    private Long bAverage;

    private Long bEmployees;

    private Long bAssets;

    @Column(length = 10)
    private String bSize;

    private LocalDateTime bEstaDate;

    @Column(length = 30)
    private String bHomepage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "allId", nullable = false)
    private All_Member all_member;
}
