package org.zerock.b01.service.boardService;

import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface NoticeBoardService {

    Long registerNotice(NoticeBoardDTO noticeBoardDTO);

    NoticeBoardDTO readNoticeOne(Long noticeId);

    void modifyNotice(NoticeBoardDTO noticeBoardDTO);

    void removeNotice(Long noticeId);

    PageResponseDTO<NoticeBoardDTO> listNotice(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListReplyCountDTO> listWithNoticeReplyCount(PageRequestDTO pageRequestDTO);

    PageResponseDTO<NoticeBoardListAllDTO> listWithNoticeAll(PageRequestDTO pageRequestDTO);

    void increaseNoticeHits(Long noticeId);

    default Notice_Board dtoToEntity(NoticeBoardDTO noticeBoardDTO) {

        Notice_Board notice_board = Notice_Board.builder()
                .noticeId(noticeBoardDTO.getNoticeId())
                .nTitle(noticeBoardDTO.getNTitle())
                .nContent(noticeBoardDTO.getNContent())
                .allMember(noticeBoardDTO.getAllMember())
                .nHits(noticeBoardDTO.getNHits())
                .build();

        if (noticeBoardDTO.getFileNames() != null) {
            noticeBoardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                notice_board.addNoticeFiles(arr[0], arr[1]);
            });
        }
        return notice_board;
    }

    default NoticeBoardDTO entityToDto(Notice_Board notice_board) {

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .noticeId(notice_board.getNoticeId())
                .nTitle(notice_board.getNTitle())
                .nContent(notice_board.getNContent())
                .allMember(notice_board.getAllMember())
                .nHits(notice_board.getNHits())
                .regDate(notice_board.getRegDate())
                .modDate(notice_board.getModDate())
                .build();

        List<String> fileNames = notice_board.getBoardFileSet().stream().sorted().map(board_file ->
                board_file.getUuid()+"_"+board_file.getFileName()).collect(Collectors.toList());

        noticeBoardDTO.setFileNames(fileNames);

        return noticeBoardDTO;
    }
}
