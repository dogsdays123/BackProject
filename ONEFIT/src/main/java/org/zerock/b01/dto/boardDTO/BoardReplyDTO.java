package org.zerock.b01.dto.boardDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardReplyDTO {

    private Long replyId;

    @NotEmpty
    private String allId;

    @NotEmpty
    private String replyText;

    private Long noticeId;

    private Long qnaId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime regDate;

    @JsonIgnore
    private LocalDateTime modDate;

    // noticeId와 qnaId 중 하나만 존재해야 함
    @AssertTrue(message = "noticeId 또는 qnaId 중 하나만 존재해야 합니다.")
    public boolean isValidBoardReference() {
        return (noticeId == null) != (qnaId == null); // XOR 연산: 하나만 존재해야 함
    }
}
