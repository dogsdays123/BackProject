package org.zerock.b01.controller.park;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
}
