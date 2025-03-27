package org.zerock.b01.domain.transaction;
import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;

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
    @Column(name = "equipment_id")
    private Long equipmentId; // 기구 ID

    @Column(name = "e_name", length = 255, nullable = false)
    private String eName; // 1. 제품명

    @Column(name = "e_brand", length = 255, nullable = false)
    private String eBrand; // 2. 제조사

    @Column(name = "e_status", length = 20, nullable = false)
    private String eStatus; // 3. 제품 상태(상, 중, 하)

    @Column(name = "e_pur_price", length = 255, nullable = false)
    private String ePurPrice; // 4. 구매가

    @Column(name = "e_use_start", nullable = false)
    private LocalDate eUseStart; // 5. 사용 시작일

    @Column(name = "e_use_end", nullable = false)
    private LocalDate eUseEnd; // 6. 사용 종료일

    @Column(name = "e_as", length = 20, nullable = false)
    private String eAs; // 7. A/S 가능여부

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID

    public void change(EquipmentDTO equipmentDTO) {
        this.eName = equipmentDTO.getEName();
        this.eBrand = equipmentDTO.getEBrand();
        this.eStatus = equipmentDTO.getEStatus();
        this.ePurPrice = equipmentDTO.getEPurPrice();
        this.eUseStart = equipmentDTO.getEUseStart();
        this.eUseEnd = equipmentDTO.getEUseEnd();
        this.eAs = equipmentDTO.getEAs();
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
