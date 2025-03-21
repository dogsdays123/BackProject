package org.zerock.b01.dto.transactionDTO;

import lombok.*;
import org.zerock.b01.domain.transaction.Product;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class EquipmentDTO extends ProductDTO{ // (거래 - 상품) 기구
    private Long equipmentId; // a. (기본키) 기구 ID

    private Long productId; // b. (외래키) 상품 ID

    private String eName; // 1. 제품명

    private String eBrand; // 2. 제조사

    private String eStatus; // 3. 제품 상태(상, 중, 하)

    private String ePurPrice; // 4. 구매가

    private LocalDate eUseStart; // 5. 사용 시작일

    private LocalDate eUseEnd; // 6. 사용 종료일

    private String eAs; // 7. A/S 가능여부

}
