package org.zerock.b01.service.boardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.board.Board_Reply;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

=======
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.board.Board_Reply;
import org.zerock.b01.dto.boardDTO.BoardReplyDTO;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;

<<<<<<< Updated upstream
<<<<<<< Updated upstream
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
@Service
@RequiredArgsConstructor
@Log4j2
public class BoardReplyServiceImpl implements BoardReplyService {

    private final BoardReplyRepository boardReplyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long registerBoardReply(BoardReplyDTO boardReplyDTO) {

        Board_Reply board_reply = modelMapper.map(boardReplyDTO, Board_Reply.class);

        Long replyId = boardReplyRepository.save(board_reply).getReplyId();

        return replyId;
    }
<<<<<<< Updated upstream
<<<<<<< Updated upstream
<<<<<<< Updated upstream

    @Override
    public BoardReplyDTO readBoardReply(Long replyId) {

        Optional<Board_Reply> replyOptional = boardReplyRepository.findById(replyId);

        Board_Reply board_reply = replyOptional.orElseThrow();

        return modelMapper.map(board_reply, BoardReplyDTO.class);
    }

    @Override
    public void modifyBoardReply(BoardReplyDTO boardReplyDTO) {


        Optional<Board_Reply> replyOptional = boardReplyRepository.findById(boardReplyDTO.getReplyId());

        Board_Reply board_reply = replyOptional.orElseThrow();

        board_reply.changeText(boardReplyDTO.getReplyText());

        boardReplyRepository.save(board_reply);
    }

    @Override
    public void removeBoardReply(Long replyId) {

        boardReplyRepository.deleteById(replyId);
    }

    @Override
    public PageResponseDTO<BoardReplyDTO> getListOfNoticeBoardReply(Long noticeId, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1
                , pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Board_Reply> result = boardReplyRepository.listOfNoticeBoardReply(noticeId, pageable);

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
    public PageResponseDTO<BoardReplyDTO> getListOfQnaBoardReply(Long qnaId, PageRequestDTO pageRequestDTO) {

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() <= 0? 0: pageRequestDTO.getPage() -1
                , pageRequestDTO.getSize(),
                Sort.by("replyId").ascending());

        Page<Board_Reply> result = boardReplyRepository.listOfQnaBoardReply(qnaId, pageable);

        List<BoardReplyDTO> dtoList = result.getContent().stream().map(board_reply ->
                        modelMapper.map(board_reply, BoardReplyDTO.class))
                        .collect(Collectors.toList());

        return PageResponseDTO.<BoardReplyDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
=======
>>>>>>> Stashed changes
}
