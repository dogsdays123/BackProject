package org.zerock.b01.dto.boardDTO;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QnaBoardDTO {

    private Long qnaId;

    @NotEmpty
    @Size(max = 400, message = "제목은 최대 400자까지 입력할 수 있습니다.")
    private String qTitle;

    @NotEmpty
    @Size(max = 4000, message = "내용은 최대 4000자까지 입력할 수 있습니다.")
    private String qContent;

    @NotNull
    private int qHits;

    private All_Member allMember;

    private LocalDateTime regDate;

    private LocalDateTime modDate;

    private List<String> fileNames;
}
