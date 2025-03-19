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
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.repository.boardRepository.NoticeBoardRepository;

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

        Notice_Board notice_board = modelMapper.map(noticeBoardDTO, Notice_Board.class);

        Long noticeId = noticeBoardRepository.save(notice_board).getNoticeId();

        return noticeId;
    }

    //조회
    @Override
    public NoticeBoardDTO readNoticeOne(Long noticeId) {

        Optional<Notice_Board> result = noticeBoardRepository.findById(noticeId);

        Notice_Board notice_board = result.orElseThrow();

        NoticeBoardDTO noticeBoardDTO = modelMapper.map(notice_board, NoticeBoardDTO.class);

        return noticeBoardDTO;
    }


    //수정
    @Override
    public void modifyNotice(NoticeBoardDTO noticeBoardDTO) {

        Optional<Notice_Board> result = noticeBoardRepository.findById(noticeBoardDTO.getNoticeId());

        Notice_Board notice_board = result.orElseThrow();

        notice_board.changeNotice(noticeBoardDTO.getNTitle(), noticeBoardDTO.getNContent());

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
        Pageable pageable = pageRequestDTO.getPageable("noticeId");

        Page<Notice_Board> result = noticeBoardRepository.searchNoticeAll(types, keyword, pageable);

        List<NoticeBoardDTO> dtoList = result.getContent().stream()
                .map(notice_board -> modelMapper.map(notice_board,NoticeBoardDTO.class)).
                                                                                collect(Collectors.toList());

        return PageResponseDTO.<NoticeBoardDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
