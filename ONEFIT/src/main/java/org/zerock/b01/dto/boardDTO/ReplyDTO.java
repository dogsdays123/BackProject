package org.zerock.b01.dto.boardDTO;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReplyDTO {

    private Long replyId;

    @NotEmpty
    private String allId;

    @NotEmpty
    private String replyText;

    private Long noticeId;

    private Long qnaId;

    private LocalDateTime regDate, modDate;

    // noticeId와 qnaId 중 하나만 존재해야 함
    @AssertTrue(message = "noticeId 또는 qnaId 중 하나만 존재해야 합니다.")
    public boolean isValidBoardReference() {
        return (noticeId == null) != (qnaId == null); // XOR 연산: 하나만 존재해야 함
    }
}
