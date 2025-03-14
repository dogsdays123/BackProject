package org.zerock.b01.domain.transaction;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Equipment { // (거래 - 상품) 기구
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipment_id; // 기구 ID

    @Column(length = 255, nullable = false)
    private String e_name; // 1. 제품명

    @Column(length = 255, nullable = false)
    private String e_brand; // 2. 제조사

    @Column(length = 20, nullable = false)
    private String e_status; // 3. 제품 상태(상, 중, 하)

    @Column(length = 255, nullable = false)
    private String e_pur_price; // 4. 구매가

    @Column(nullable = false)
    private LocalDate e_use_start; // 5. 사용 시작일

    @Column(nullable = false)
    private LocalDate e_use_end; // 6. 사용 종료일

    @Column(length = 20, nullable = false)
    private String e_as; // 7. A/S 가능여부

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID
}
