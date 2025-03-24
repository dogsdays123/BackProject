package org.zerock.b01.service.boardService;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;

public interface QnaBoardService {

    Long registerQna(QnaBoardDTO qnaBoardDTO);

    QnaBoardDTO readQnaOne(Long qnaId);

    void modifyQna(QnaBoardDTO qnaBoardDTO);

    void removeQna(Long qnaId);

    PageResponseDTO<QnaBoardDTO> listQna(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListReplyCountDTO> listWithQnaReplyCount(PageRequestDTO pageRequestDTO);
}
