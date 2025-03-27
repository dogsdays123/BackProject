package org.zerock.b01.dto.boardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListReplyCountDTO {

    private Long noticeId;
    private Long qnaId;
    private String title;
    private String allId;
    private String boardType; // "notice" 또는 "qna"로 구분 가능
    private int hits;
    private LocalDateTime regDate;

    private Long replyCount;

    public void setBoardType() {
        this.title = title;
        if (noticeId != null) {
            this.boardType = "notice";
        } else if (qnaId != null) {
            this.boardType = "qna";
        }
    }
}