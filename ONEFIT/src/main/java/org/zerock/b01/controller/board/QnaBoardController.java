package org.zerock.b01.controller.board;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board_qa_register")
    public void registerQanGET() {

    }

    @PostMapping("/board_qa_register")
    public String registerQnaPost(@Valid QnaBoardDTO qnaBoardDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        log.info("board_qa_register Post");

        if (bindingResult.hasErrors()) {
            log.info("has errors....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/zboard/board_qa_register";
        }

        log.info(qnaBoardDTO);

        Long qnaId = qnaBoardService.registerQna(qnaBoardDTO);

        redirectAttributes.addFlashAttribute("result", qnaId);

        return "redirect:/zboard/board_qa_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/board_qa_read")
    public void readQna(Long qnaId, PageRequestDTO pageRequestDTO, Model model) {

        QnaBoardDTO qnaBoardDTO = qnaBoardService.readQnaOne(qnaId);

        log.info(qnaBoardDTO);

        model.addAttribute("dto", qnaBoardDTO);

    }

    @PreAuthorize("principal.username == #qnaBoardDTO.allMember")
    @PostMapping("/board_qa_modify")
    public String modifyQna(PageRequestDTO pageRequestDTO, @Valid QnaBoardDTO qnaBoardDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board_qna_modify Post" + qnaBoardDTO);

        if (bindingResult.hasErrors()) {

            log.info("has errors....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addFlashAttribute("qnaId", qnaBoardDTO.getQnaId());

            return "redirect:/zboard/board_qa_modify?" + link;
        }

        qnaBoardService.modifyQna(qnaBoardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addFlashAttribute("qnaId", qnaBoardDTO.getQnaId());

        return "redirect:/zboard/board_qa_read";

    }

    @PreAuthorize("principal.username == #qnaBoardDTO.allMember")
    @PostMapping("/board_qa_remove")
    public String removeQna(Long qnaId, RedirectAttributes redirectAttributes) {

        log.info("board_qa_remove Post" + qnaId);

        qnaBoardService.removeQna(qnaId);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/zboard/board_qa_list";
    }
}
