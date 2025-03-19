package org.zerock.b01.controller.park;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.service.recruitService.RecruitService;

@Controller
@RequestMapping("/recruit")
@Log4j2
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("recruit_list Get...............");

        PageResponseDTO<RecruitDTO> responseDTO = recruitService.list(pageRequestDTO);

        log.info(responseDTO);

        model.addAttribute("responseDTO", responseDTO);


    }

    @GetMapping("/register")
    public void registerGET(){

    }

    @PostMapping("/register")
    public String registerPost (@Valid RecruitDTO recruitDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        log.info("recruit_register Post...............");

        if(bindingResult.hasErrors()) {
            log.info("has errors");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            return "redirect:/recruit/register";
        }

        log.info(recruitDTO);

        Long recruitId = recruitService.register(recruitDTO);

        redirectAttributes.addFlashAttribute("result", recruitId);

        return "redirect:/recruit/list";
    }

    @GetMapping({"/read","/modify"})
    public void read(Long recruitId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("recruit_read Get...............");

        RecruitDTO recruitDTO = recruitService.readOne(recruitId);

        log.info(recruitDTO);

        model.addAttribute("dto", recruitDTO);
    }
}
