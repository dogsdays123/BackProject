package org.zerock.b01.service.boardService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;
import org.zerock.b01.repository.boardRepository.NoticeBoardRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class NoticeBoardServiceImpl implements NoticeBoardService {

    private final ModelMapper modelMapper;

    private final NoticeBoardRepository noticeBoardRepository;

    //등록
    @Override
    public Long registerNotice(NoticeBoardDTO noticeBoardDTO) {

//        Notice_Board notice_board = modelMapper.map(noticeBoardDTO, Notice_Board.class);
        Notice_Board notice_board = dtoToEntity(noticeBoardDTO);

        Long noticeId = noticeBoardRepository.save(notice_board).getNoticeId();

        return noticeId;
    }

    //조회
    @Override
    public NoticeBoardDTO readNoticeOne(Long noticeId) {

        Optional<Notice_Board> result = noticeBoardRepository.findByIdWithNoticefiles(noticeId);

        Notice_Board notice_board = result.orElseThrow();

        NoticeBoardDTO noticeBoardDTO = entityToDto(notice_board);

        return noticeBoardDTO;
    }


    //수정
    @Override
    public void modifyNotice(NoticeBoardDTO noticeBoardDTO) {

        Optional<Notice_Board> result = noticeBoardRepository.findById(noticeBoardDTO.getNoticeId());

        Notice_Board notice_board = result.orElseThrow();

        notice_board.changeNotice(noticeBoardDTO.getNTitle(), noticeBoardDTO.getNContent());

        //첨부파일처리
        //기존 첨부 파일 제거
        notice_board.clearNoticeFiles();
        // 새로운 첨부 파일이 존재하는 경우 추가
        if (noticeBoardDTO.getFileNames() != null) {
            for (String fileName : noticeBoardDTO.getFileNames()) {
                //파일명을 _ 기준으로 분리
                String[] arr = fileName.split("_");
                // 게시글에 이미지 추가
                notice_board.addNoticeFiles(arr[0], arr[1]);
            }
        }

        noticeBoardRepository.save(notice_board);
    }

    //삭제
    @Override
    public void removeNotice(Long noticeId) {

        noticeBoardRepository.deleteById(noticeId);

    }

    @Override
    public PageResponseDTO<NoticeBoardDTO> listNotice(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();
        Pageable pageable = pageRequestDTO.getPageable("noticeId");

        Page<Notice_Board> result = noticeBoardRepository.searchNoticeAll(types, keyword, startDate, endDate, pageable);

        List<NoticeBoardDTO> dtoList = result.getContent().stream()
                .map(notice_board -> modelMapper.map(notice_board,NoticeBoardDTO.class)).
                                                                                collect(Collectors.toList());

        return PageResponseDTO.<NoticeBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardListReplyCountDTO> listWithNoticeReplyCount(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();
        Pageable pageable = pageRequestDTO.getPageable("noticeId");

        Page<BoardListReplyCountDTO> result = noticeBoardRepository
                .searchWithNoticeReplyCount(types, keyword, startDate, endDate, pageable);

        return PageResponseDTO.<BoardListReplyCountDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int) result.getTotalElements())
                .build();
    }

    public String getNoticeTitle(Long noticeId) {
        Optional<Notice_Board> notice = noticeBoardRepository.findById(noticeId);
        return notice.map(Notice_Board::getNTitle).orElseThrow(() ->
                new IllegalArgumentException("공지사항 제목이 없습니다."));
    }

    @Override
    public PageResponseDTO<NoticeBoardListAllDTO> listWithNoticeAll(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        LocalDate startDate = pageRequestDTO.getStartDate();
        LocalDate endDate = pageRequestDTO.getEndDate();

        Pageable pageable = pageRequestDTO.getPageable("noticeId");

        //검색 조건과 페이지 정보를 이용하여 데이터 조회
        Page<NoticeBoardListAllDTO> result = noticeBoardRepository
                                    .searchWithNoticeAll(types, keyword, startDate, endDate, pageable);

        //조회 결과를 PageResponseDTO 객체로 변환하여 반환
        return PageResponseDTO.<NoticeBoardListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }
}
