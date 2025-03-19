package org.zerock.b01.dto.boardDTO;

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
public class QnaBoardDTO {

    private Long qnaId;

    private String qTitle;

    private String qContent;

    private int qHits;

    private All_Member allMember;

    private LocalDateTime regdate;

    private LocalDateTime moddate;
}
