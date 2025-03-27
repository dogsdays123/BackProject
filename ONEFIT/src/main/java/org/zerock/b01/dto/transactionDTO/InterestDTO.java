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
public class InterestDTO {

    private Long interestId;

    private LocalDateTime regDate;

    private Long productId;

    private String allId;
}
