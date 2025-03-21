package org.zerock.b01.dto.boardDTO;

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
public class NoticeBoardDTO {

    private Long noticeId;

    @NotEmpty
    private String nTitle;

    @NotEmpty
    private String nContent;

    @NotNull
    private int nHits;

    private All_Member allMember;

    private LocalDateTime regDate;

    private LocalDateTime modDate;
}
