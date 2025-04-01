package org.zerock.b01.service.boardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardListAllDTO;
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

//        Qna_Board qna_board = modelMapper.map(qnaBoardDTO, Qna_Board.class);
        Qna_Board qna_board = dtoToEntity(qnaBoardDTO);

        Long qnaId = qnaBoardRepository.save(qna_board).getQnaId();

        return qnaId;
    }

    //조회
    @Override
    public QnaBoardDTO readQnaOne(Long qnaId) {

        Optional<Qna_Board> result = qnaBoardRepository.findByIdWithQnafiles(qnaId);

        Qna_Board qna_board = result.orElseThrow();

        QnaBoardDTO qnaBoardDTO = entityToDto(qna_board);

        return qnaBoardDTO;
    }

    //수정
    @Override
    public void modifyQna(QnaBoardDTO qnaBoardDTO) {

        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaBoardDTO.getQnaId());

        Qna_Board qna_board = result.orElseThrow();

        qna_board.changeQna(qnaBoardDTO.getQTitle(), qnaBoardDTO.getQContent());

        //첨부파일처리
        //기존 첨부 파일 제거
        qna_board.clearQnaFiles();
        // 새로운 첨부 파일이 존재하는 경우 추가
        if (qnaBoardDTO.getFileNames() != null) {
            for (String fileName : qnaBoardDTO.getFileNames()) {
                //파일명을 _ 기준으로 분리
                String[] arr = fileName.split("_");
                // 게시글에 이미지 추가
                qna_board.addQnaFiles(arr[0], arr[1]);
            }
        }

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

    @Override
    public PageResponseDTO<QnaBoardListAllDTO> listWithQnaAll(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();

        Pageable pageable = pageRequestDTO.getPageable("qnaId");

        //검색 조건과 페이지 정보를 이용하여 데이터 조회
        Page<QnaBoardListAllDTO> result = qnaBoardRepository
                                    .searchWithQnaAll(types, keyword, startDate, endDate, pageable);

        //조회 결과를 PageResponseDTO 객체로 변환하여 반환
        return PageResponseDTO.<QnaBoardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    @Transactional
    public void increaseQnaHits(Long qnaId) {
        // 공지사항 조회
        Qna_Board qna_board = qnaBoardRepository.findById(qnaId)
                .orElseThrow(() -> new RuntimeException("공지사항을 찾을 수 없습니다."));

        // 조회수 증가
        qna_board.increaseQnaHits();

        // 변경된 공지사항 저장 (데이터베이스에 반영)
        qnaBoardRepository.save(qna_board);
    }
}
