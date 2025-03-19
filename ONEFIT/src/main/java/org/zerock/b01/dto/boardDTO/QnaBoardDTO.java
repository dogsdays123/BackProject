package org.zerock.b01.dto.boardDTO;

import jakarta.validation.constraints.NotEmpty;
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

    @NotEmpty
    private String qTitle;

    @NotEmpty
    private String qContent;

    @NotEmpty
    private int qHits;

    @NotEmpty
    private All_Member allMember;

    private LocalDateTime regdate;

    private LocalDateTime moddate;
}
