package org.zerock.b01.dto.transactionDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductListAllDTO {
    private Long productId; // 상품 ID
    private String allId; // 회원 ID
    private String pTitle; // 제목
    private String pStatus; // 거래 상태 (판매중 / 예약중 / 판매완료)
    private BigDecimal pPrice; // 가격
    private String pAddr; // 거래 장소
    private LocalDateTime regDate; // 등록 시각
    private Long replyCount; // 댓글 수
    private List<ImageFileDTO> imageFileList;

//    private Long replyCount;
}
