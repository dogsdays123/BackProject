package org.zerock.b01.dto.transactionDTO;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class FacilityDTO extends ProductDTO{ // (거래 - 상품) 시설
    private String fCenterName; // 1. 센터명

    private BigDecimal fDeposit; // 2. 보증금

    private BigDecimal fMonthRent; // 3. 월세

    private BigDecimal fAdminCost; // 4. 관리비

    private String fReasonSale; // 5. 매매사유

    private BigDecimal fContArea; // 6. 계약 면적 (㎡)

    private BigDecimal fContAreaPyeong; //  6-1. 계약 면적 (평)

    private BigDecimal fRealArea; // 7. 실 면적 (㎡)

    private BigDecimal fRealAreaPyeong; // 7-1. 실 면적 (평)

}
