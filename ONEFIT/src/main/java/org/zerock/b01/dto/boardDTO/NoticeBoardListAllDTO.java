package org.zerock.b01.dto.boardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NoticeBoardListAllDTO {

    private Long noticeId;

    private String nTitle;

    private String allId;

    private int nHits;

    private LocalDateTime regDate;

    private Long replyCount;

    private boolean fileExists;

    private List<BoardFileDTO> boardFiles;
}
