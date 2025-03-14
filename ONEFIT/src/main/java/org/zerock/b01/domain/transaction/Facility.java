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
    private Long facility_id; // 시설 ID

    @Column(length = 255, nullable = false)
    private String f_center_name; // 1. 센터명

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal f_deposit; // 2. 보증금

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal f_month_rent; // 3. 월세

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal f_admin_cost; // 4. 관리비

    @Column(length = 255, nullable = false)
    private String f_reason_sale; // 5. 매매사유

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal f_cont_area; // 6. 계약 면적

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal f_real_area; // 7. 실 면적

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID
}
