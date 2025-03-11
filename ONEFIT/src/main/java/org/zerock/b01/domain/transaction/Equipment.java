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
public class Equipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long equipment_id;

    @Column(length = 255, nullable = false)
    private String p_pur_price;

    @Column(nullable = false)
    private LocalDate p_use_start;

    @Column(nullable = false)
    private LocalDate p_use_end;

    @Column(length = 20, nullable = false)
    private String p_as;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
