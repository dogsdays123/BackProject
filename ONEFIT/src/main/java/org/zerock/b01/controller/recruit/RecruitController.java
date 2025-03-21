package org.zerock.b01.controller.recruit;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.recruitService.RecruitService;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/recruit")
@Log4j2
@RequiredArgsConstructor
public class RecruitController {

    private final RecruitService recruitService;

    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;

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
    public void read(Long recruitId, PageRequestDTO pageRequestDTO,  Business_MemberDTO businessMemberDTO, Model model, All_MemberDTO all_memberDTO) {
        log.info("recruit_read Get...............");

        RecruitDTO recruitDTO = recruitService.readOne(recruitId);

        businessMemberDTO = member_Set_Type_Service.BusinessRead(businessMemberDTO.getAllId());


        LocalDateTime deadline = recruitDTO.getReDeadline();  // 마감일 (LocalDateTime)
        LocalDateTime now = LocalDateTime.now();  // 현재 시간

        long daysLeft = ChronoUnit.DAYS.between(now, deadline);  // 남은 날짜 계산

        // D-0이면 "오늘 마감", D-음수면 "마감 종료" 표시
        String dDayText = daysLeft > 0 ? "D-" + daysLeft : (daysLeft == 0 ? "D-Day" : "마감 종료");

        model.addAttribute("dDayText", dDayText);

        log.info("Recruit ID: " + recruitDTO.getRecruitId());
        model.addAttribute("businessMemberDTO", businessMemberDTO);
        log.info("BusinessMemberDTO: " + businessMemberDTO);
        model.addAttribute("dto", recruitDTO);
    }

    @PostMapping("/remove")
    public String remove(Long recruitId, RedirectAttributes redirectAttributes) {

        log.info("remove post .. " + recruitId);
        recruitService.remove(recruitId);

        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/recruit/list";
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO, RecruitDTO recruitDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("recruit_modify post...............");

        if(bindingResult.hasErrors()) {
            log.info("has errors");

            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());

            redirectAttributes.addAttribute("recruitId", recruitDTO.getRecruitId());

            return "redirect:/recruit/modify?"+link;
        }

        recruitService.modify(recruitDTO);

        redirectAttributes.addFlashAttribute("result", "modified");

        redirectAttributes.addAttribute("recruitId", recruitDTO.getRecruitId());

        return "redirect:/recruit/read";
    }



}
