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
public class Facility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facility_id;

    @Column(length = 255, nullable = false)
    private String p_center_name;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal p_deposit;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal p_month_rent;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal p_admin_cost;

    @Column(length = 255, nullable = false)
    private String p_reason_sale;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal p_cont_area;

    @Column(nullable = false, precision = 7, scale = 2)
    private BigDecimal p_real_area;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
