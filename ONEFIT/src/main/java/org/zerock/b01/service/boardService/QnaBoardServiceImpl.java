package org.zerock.b01.service.boardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.repository.boardRepository.QnaBoardRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class QnaBoardServiceImpl implements QnaBoardService {

    private final ModelMapper modelMapper;

    private final QnaBoardRepository qnaBoardRepository;

    //등록
    @Override
    public Long registerQna(QnaBoardDTO qnaBoardDTO) {

        Qna_Board qna_board = modelMapper.map(qnaBoardDTO, Qna_Board.class);

        Long qnaId = qnaBoardRepository.save(qna_board).getQnaId();

        return qnaId;
    }

    //조회
    @Override
    public QnaBoardDTO readQnaOne(Long qnaId) {

        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaId);

        Qna_Board qna_board = result.orElseThrow();

        QnaBoardDTO qnaBoardDTO = modelMapper.map(qna_board, QnaBoardDTO.class);

        return qnaBoardDTO;
    }

    //수정
    @Override
    public void modifyQna(QnaBoardDTO qnaBoardDTO) {

        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaBoardDTO.getQnaId());

        Qna_Board qna_board = result.orElseThrow();

        qna_board.changeQna(qnaBoardDTO.getQTitle(), qnaBoardDTO.getQContent());

        qnaBoardRepository.save(qna_board);
    }

    @Override
    public void removeQna(Long qnaId) {

        qnaBoardRepository.deleteById(qnaId);

    }

    @Override
    public PageResponseDTO<QnaBoardDTO> listQna(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();
        Pageable pageable = pageRequestDTO.getPageable("qnaId");

        Page<Qna_Board> result = qnaBoardRepository.searchQnaAll(types, keyword, startDate, endDate, pageable);

        List<QnaBoardDTO> dtoList = result.getContent().stream()
                .map(qna_board -> modelMapper.map(qna_board,QnaBoardDTO.class)).
                collect(Collectors.toList());

        return PageResponseDTO.<QnaBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithQnaReplyCount(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();
        Pageable pageable = pageRequestDTO.getPageable("qnaId");

        Page<BoardListReplyCountDTO> result = qnaBoardRepository
                .searchWithQnaReplyCount(types, keyword, startDate, endDate, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }
}
