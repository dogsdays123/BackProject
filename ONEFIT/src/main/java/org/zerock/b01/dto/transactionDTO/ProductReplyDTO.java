package org.zerock.b01.dto.transactionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.Product;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReplyDTO { // (거래 - 상품) 댓글

    private Long productReplyId; //  댓글 ID

    private String pReplyText; // 댓글 내용

    private Long productId; // (외래키) 상품 ID

    private String allId; // (외래키) 회원 ID

    private String productRole;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
