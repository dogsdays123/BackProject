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

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/board_notice_register")
    public void registerNoticeGET() {

    }

    @PostMapping("/board_notice_register")
    public String registerNoticePost(@Valid NoticeBoardDTO noticeBoardDTO, BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes) {

        log.info("board_notice_register Post");

        if (bindingResult.hasErrors()) {
            log.info("has errors....");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/zboard/board_notice_register";
        }

        log.info(noticeBoardDTO);

        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);

        redirectAttributes.addFlashAttribute("result", noticeId);

        return "redirect:/zboard/board_notice_list";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping({"/board_notice_read","/board_notice_modify"})
    public void readNotice(Long noticeId, PageRequestDTO pageRequestDTO, Model model) {

        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readNoticeOne(noticeId);

        log.info(noticeBoardDTO);

        model.addAttribute("dto", noticeBoardDTO);

    }

    @PreAuthorize("principal.username == #noticeBoardDTO.allMember")
    @PostMapping("/board_notice_modify")
    public String modifyNotice(PageRequestDTO pageRequestDTO, @Valid NoticeBoardDTO noticeBoardDTO,
                               BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("board_notice_modify Post" + noticeBoardDTO);

        if (bindingResult.hasErrors()) {

            log.info("has errors....");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addFlashAttribute("noticeId", noticeBoardDTO.getNoticeId());

            return "redirect:/zboard/board_notice_modify?" + link;
        }

        noticeBoardService.modifyNotice(noticeBoardDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addFlashAttribute("noticeId", noticeBoardDTO.getNoticeId());

        return "redirect:/zboard/board_notice_read";

    }

    @PreAuthorize("principal.username == #noticeBoardDTO.allMember")
    @PostMapping("/board_notice_remove")
    public String removeNotice(Long noticeId, RedirectAttributes redirectAttributes) {

        log.info("board_notice_remove Post" + noticeId);

        noticeBoardService.removeNotice(noticeId);

        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/zboard/board_notice_list";
    }
}
