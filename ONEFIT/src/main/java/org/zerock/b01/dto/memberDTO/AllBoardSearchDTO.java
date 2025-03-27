package org.zerock.b01.dto.memberDTO;

import lombok.*;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.Qna_Board;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllBoardSearchDTO {
    private List<Notice_Board> noticeBoard;
    private List<Qna_Board> qnaBoard;
}
