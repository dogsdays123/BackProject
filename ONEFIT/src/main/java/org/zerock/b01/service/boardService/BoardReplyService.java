package org.zerock.b01.service.boardService;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;

public interface BoardReplyService {

    Long registerBoard(BoardReplyDTO boardReplyDTO);

    BoardReplyDTO readBoard(Long replyId);

    void modifyBoard(BoardReplyDTO boardReplyDTO);

    void removeBoard(Long replyId);

    PageResponseDTO<BoardReplyDTO> getListOfNoticeBoard(Long noticeId, PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardReplyDTO> getListOfQnaBoard(Long qnaId, PageRequestDTO pageRequestDTO);
}
