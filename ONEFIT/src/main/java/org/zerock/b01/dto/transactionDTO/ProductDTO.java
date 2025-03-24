package org.zerock.b01.dto.transactionDTO;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO { // (거래) 상품
    private Long productId; // a. (기본키) 상품 ID

    private Long categoryId; // b. (외래키) 카테고리 ID

    private String allId; // c. (외래키) 회원 ID

    private String pAddr; // 1. 거래 장소

    private int pRoles; // 2. 상품 구분 (1: 기구 / 2: 시설)

    private String pStatus; // 3. 거래 상태 (판매중 / 예약중 / 판매완료)

    private BigDecimal pPrice; // 4. 판매가

    private String pContent; // 5. 판매 내용

    private String pTitle; // 6. 제목

    private String pChatUrl; // 7. 오픈 채팅 URL

    private String cCategory; // 8. 카테고리

    // 이미지 파일의 이름들
    private List<String> imageFileNames;

    // 삭제할 이미지 uuid
    private List<String> removeImageFileUuid;

    private LocalDateTime regDate; // 9. 게시글 등록일
    private LocalDateTime modDate; // 10. 게시글 수정일

}
