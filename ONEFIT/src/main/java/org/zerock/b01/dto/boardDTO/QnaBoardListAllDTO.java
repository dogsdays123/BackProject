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
public class QnaBoardListAllDTO {

    private Long qnaId;

    private String qTitle;

    private String allId;

    private int qHits;

    private LocalDateTime regDate;

    private Long replyCount;

    private boolean fileExists;

    private List<BoardFileDTO> boardFiles;
}
