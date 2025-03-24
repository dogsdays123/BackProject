package org.zerock.b01.service.boardService;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;

public interface NoticeBoardService {

    Long registerNotice(NoticeBoardDTO noticeBoardDTO);

    NoticeBoardDTO readNoticeOne(Long noticeId);

    void modifyNotice(NoticeBoardDTO noticeBoardDTO);

    void removeNotice(Long noticeId);

    PageResponseDTO<NoticeBoardDTO> listNotice(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListReplyCountDTO> listWithNoticeReplyCount(PageRequestDTO pageRequestDTO);

}
