package org.zerock.b01.controller.kim;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.service.boardService.NoticeBoardService;

@Controller
@Log4j2
@RequestMapping("/zboard")
@RequiredArgsConstructor
public class NoticeBoardController {

    private final NoticeBoardService noticeBoardService;


    @GetMapping("/board_notice_list")
    public void listNotice(PageRequestDTO pageRequestDTO, Model model) {

        PageResponseDTO<NoticeBoardDTO> responseDTO = noticeBoardService.listNotice(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);

    }
}
