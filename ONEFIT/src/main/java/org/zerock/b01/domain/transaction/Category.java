package org.zerock.b01.domain.transaction;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Category { // (거래) 카테고리
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId; // 카테고리 ID

    @Column(name = "c_roles")
    private int cRoles; // 1. 카테고리 구분

    @Column(name = "c_category", length = 100, nullable = false)
    private String cCategory; // 카테고리
}
