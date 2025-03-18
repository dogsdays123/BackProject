package org.zerock.b01.domain.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity { // (거래) 상품
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId; // 상품 ID

    @Column(name = "p_addr", nullable = false)
    private String pAddr; // 1. 거래 장소

    @Column(name = "p_roles", nullable = false)
    private int pRoles; // 2. 상품 구분 (1: 기구 / 2: 시설)

    @Column(name = "p_status", nullable = false)
    private String pStatus; // 3. 거래 상태 (판매중 / 예약중 / 판매완료)

    @Column(name = "p_price", nullable = false, precision = 10, scale = 0)
    private BigDecimal pPrice; // 4. 판매가

    @Column(name = "p_content", columnDefinition = "TEXT", nullable = false)
    private String pContent; // 5. 판매 내용

    @Column(name = "p_title", length = 255, nullable = false)
    private String pTitle; // 6. 제목

    @Column(name = "p_chat_url", length = 255, nullable = false)
    private String pChatUrl; // 7. 오픈 채팅 URL

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember; // (외래키) 회원 ID

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // (외래키) 카테고리 ID

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
