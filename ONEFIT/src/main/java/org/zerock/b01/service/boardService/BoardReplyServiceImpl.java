package org.zerock.b01.service.boardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.board.Board_Reply;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;
import org.zerock.b01.repository.boardRepository.NoticeBoardRepository;
import org.zerock.b01.repository.boardRepository.QnaBoardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReplyServiceImpl implements BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;
    private final All_MemberRepository all_MemberRepository;

    private final ModelMapper modelMapper;
    private final NoticeBoardRepository noticeBoardRepository;
    private final QnaBoardRepository qnaBoardRepository;

    @Override
    public Long registerBoard(BoardReplyDTO boardReplyDTO) {
        log.info("123");

//        Board_Reply board_reply = modelMapper.map(boardReplyDTO, Board_Reply.class);
        All_Member all_Member = all_MemberRepository.findByAllId(boardReplyDTO.getAllId()).orElseThrow();

        Notice_Board notice_board = null;
        if (boardReplyDTO.getNoticeId() != null) {
            notice_board = noticeBoardRepository.findById(boardReplyDTO.getNoticeId()).orElseThrow();
        }

        Qna_Board qna_board = null;
        if (boardReplyDTO.getQnaId() != null) {
            qna_board = qnaBoardRepository.findById(boardReplyDTO.getQnaId()).orElseThrow();
        }

        if (notice_board == null && qna_board == null) {
            throw new IllegalArgumentException("공지 게시판 ID와 QnA 게시판 ID 중 하나는 반드시 있어야 합니다.");
        }

        Board_Reply board_reply = Board_Reply.builder()
                .replyText(boardReplyDTO.getReplyText())
                .allMember(all_Member)
                .noticeBoard(notice_board)
                .qnaBoard(qna_board)
                .build();

        Long replyId = boardReplyRepository.save(board_reply).getReplyId();

        log.info(board_reply);

        return replyId;
    }

    @Override
    public BoardReplyDTO readBoard(Long replyId) {

        Optional<Board_Reply> replyOptional = boardReplyRepository.findById(replyId);

        Board_Reply board_reply = replyOptional.orElseThrow();

        return modelMapper.map(board_reply, BoardReplyDTO.class);
    }

    @Override
    public void modifyBoard(BoardReplyDTO boardReplyDTO) {


        Optional<Board_Reply> replyOptional = boardReplyRepository.findById(boardReplyDTO.getReplyId());

        Board_Reply board_reply = replyOptional.orElseThrow();

        board_reply.changeText(boardReplyDTO.getReplyText());

        boardReplyRepository.save(board_reply);
    }

    @Override
    public void removeBoard(Long replyId) {

        boardReplyRepository.deleteById(replyId);
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfNoticeBoard(Long noticeId, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1
                , pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Board_Reply> result = boardReplyRepository.listOfNoticeBoard(noticeId, pageable);

        List<BoardReplyDTO> dtoList = result.getContent().stream().map(board_reply ->
                        modelMapper.map(board_reply, BoardReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfQnaBoard(Long qnaId, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1
                , pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Board_Reply> result = boardReplyRepository.listOfQnaBoard(qnaId, pageable);

        List<BoardReplyDTO> dtoList = result.getContent().stream().map(board_reply ->
                        modelMapper.map(board_reply, BoardReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
}
