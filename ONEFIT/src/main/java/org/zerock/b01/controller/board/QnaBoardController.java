package org.zerock.b01.controller.board;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.service.boardService.QnaBoardService;

@Controller
@Log4j2
@RequestMapping("/zboard")
@RequiredArgsConstructor
public class QnaBoardController {

    private final QnaBoardService qnaBoardService;


    @GetMapping("/board_qa_list")
    public void listQna(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<QnaBoardDTO> responseDTO = qnaBoardService.listQna(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }
}
