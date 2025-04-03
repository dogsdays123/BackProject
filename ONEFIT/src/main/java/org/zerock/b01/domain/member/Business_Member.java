package org.zerock.b01.domain.member;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "allMember")
public class Business_Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long businessId;

    @Column(length = 100, nullable = false)
    private String bAddress;

    @Column(length = 100)
    private String bAddressExtra;

    @Column(length = 30, nullable = false)
    private String bName;

    @Column(nullable = false)
    private Long bRegNum;

    @Column(length = 30, nullable = false)
    private String bExponent;

    @Column(nullable = false)
    private String bPhone;

    private Long bAverage;

    private Long bEmployees;

    private Long bAssets;

    @Column(length = 10)
    private String bSize;

    private LocalDate bEstaDate;

    @Column(length = 30)
    private String bHomepage;

    @org.hibernate.annotations.Cascade(org.hibernate.annotations.CascadeType.ALL)
    @ManyToOne(fetch = FetchType.LAZY)
    private All_Member allMember;

    public void modifyMember(String bName, String bExponent, String bAddress, String bAddressExtra, String bPhone, String  bHomepage, Long bEmployees, Long bAverage, Long bAssets) {
        this.bName = bName;
        this.bExponent = bExponent;
        this.bAddress = bAddress;
        this.bAddressExtra = bAddressExtra;
        this.bPhone = bPhone;
        this.bHomepage = bHomepage;
        this.bEmployees = bEmployees;
        this.bAverage = bAverage;
        this.bAssets = bAssets;
    }
}
