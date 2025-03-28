package org.zerock.b01.dto.memberDTO;

import lombok.*;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AllBoardSearchDTO {
    private List<NoticeBoardDTO> noticeBoardDTO;
    private List<QnaBoardDTO> qnaBoardDTO;
}
