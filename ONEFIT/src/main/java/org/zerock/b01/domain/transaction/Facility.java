package org.zerock.b01.domain.transaction;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Facility { // (거래 - 상품) 시설
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "facility_id")
    private Long facilityId; // 시설 ID

    @Column(name = "f_center_name", length = 255, nullable = false)
    private String fCenterName; // 1. 센터명

    @Column(name = "f_deposit", nullable = false, precision = 10, scale = 0)
    private BigDecimal fDeposit; // 2. 보증금

    @Column(name = "f_month_rent", nullable = false, precision = 10, scale = 0)
    private BigDecimal fMonthRent; // 3. 월세

    @Column(name = "f_admin_cost", nullable = false, precision = 10, scale = 0)
    private BigDecimal fAdminCost; // 4. 관리비

    @Column(name = "f_reason_sale", length = 255, nullable = false)
    private String fReasonSale; // 5. 매매사유

    @Column(name = "f_cont_area", nullable = false, precision = 7, scale = 2)
    private BigDecimal fContArea; // 6. 계약 면적

    @Column(name = "f_real_area", nullable = false, precision = 7, scale = 2)
    private BigDecimal fRealArea; // 7. 실 면적

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID

    public void setProduct(Product product) {
        this.product = product;
    }
}
