package org.zerock.b01.service.boardService;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;


public interface BoardReplyService {

    Long registerBoardReply(BoardReplyDTO boardReplyDTO);

    BoardReplyDTO readBoardReply(Long replyId);

    void modifyBoardReply(BoardReplyDTO boardReplyDTO);

    void removeBoardReply(Long replyId);

    PageResponseDTO<BoardReplyDTO> getListOfNoticeBoardReply(Long noticeId, PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardReplyDTO> getListOfQnaBoardReply(Long qnaId, PageRequestDTO pageRequestDTO);
=======
=======
>>>>>>> Stashed changes
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;

public interface BoardReplyService {

    Long registerBoardReply(BoardReplyDTO boardReplyDTO);
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
