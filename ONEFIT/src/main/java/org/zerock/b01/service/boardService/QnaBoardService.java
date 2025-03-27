package org.zerock.b01.service.boardService;

import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.*;

import java.util.List;
import java.util.stream.Collectors;

public interface QnaBoardService {

    Long registerQna(QnaBoardDTO qnaBoardDTO);

    QnaBoardDTO readQnaOne(Long qnaId);

    void modifyQna(QnaBoardDTO qnaBoardDTO);

    void removeQna(Long qnaId);

    PageResponseDTO<QnaBoardDTO> listQna(PageRequestDTO pageRequestDTO);

    PageResponseDTO<BoardListReplyCountDTO> listWithQnaReplyCount(PageRequestDTO pageRequestDTO);

    PageResponseDTO<QnaBoardListAllDTO> listWithQnaAll(PageRequestDTO pageRequestDTO);

    default Qna_Board dtoToEntity(QnaBoardDTO qnaBoardDTO) {

        Qna_Board qna_board = Qna_Board.builder()
                .qnaId(qnaBoardDTO.getQnaId())
                .qTitle(qnaBoardDTO.getQTitle())
                .qContent(qnaBoardDTO.getQContent())
                .allMember(qnaBoardDTO.getAllMember())
                .qHits(qnaBoardDTO.getQHits())
                .build();

        if (qnaBoardDTO.getFileNames() != null) {
            qnaBoardDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                qna_board.addQnaFiles(arr[0], arr[1]);
            });
        }
        return qna_board;
    }

    default QnaBoardDTO entityToDto(Qna_Board qna_board) {

        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
                .qnaId(qna_board.getQnaId())
                .qTitle(qna_board.getQTitle())
                .qContent(qna_board.getQContent())
                .allMember(qna_board.getAllMember())
                .qHits(qna_board.getQHits())
                .regDate(qna_board.getRegDate())
                .modDate(qna_board.getModDate())
                .build();

        List<String> fileNames = qna_board.getBoardFileSet().stream().sorted().map(board_file ->
                board_file.getUuid()+"_"+board_file.getFileName()).collect(Collectors.toList());

        qnaBoardDTO.setFileNames(fileNames);

        return qnaBoardDTO;
    }
}
