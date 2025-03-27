package org.zerock.b01.domain.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.domain.QAll_Member;

@Entity
@Table(name = "product_reply", indexes = {
        @Index(name = "idx_product_reply_product_product_id", columnList = "product_id"),
        @Index(name = "idx_product_reply_all_member_all_id", columnList = "all_id")
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"product", "allMember"})
public class ProductReply extends BaseEntity { // (거래 - 상품) 게시글 댓글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productReplyId;

    @Column(name = "p_reply_text", nullable = false)
    private String pReplyText; // 댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember; // (외래키) 회원 ID

    public void setProduct(Product product) { this.product = product; }
    public void setAllMember(All_Member allMember) { this.allMember = allMember; }

    public void changeText(String text) {
        this.pReplyText = text;
    }

}
