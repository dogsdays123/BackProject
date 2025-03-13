package org.zerock.b01.controller.seo;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.All_MemberService;

@Controller
@RequestMapping("/main")
@Log4j2
@RequiredArgsConstructor
public class All_MemberController {

    private final All_MemberService all_memberService;

    @GetMapping("/my_board")
    public void my_board(){
        log.info("my_board");
    }

    @GetMapping("/my_default_page")
    public void my_default_page(){
        log.info("my_default_page");
    }

    @GetMapping("/my_business_page")
    public void my_business_page(){
        log.info("my_business_page");
    }

    @GetMapping("/first")
    public void first(){
        log.info("first");
    }













    
    //연습용, 가져다 쓰기용
    //연습용, 가져다 쓰기용
    //연습용, 가져다 쓰기용
    //연습용, 가져다 쓰기용

    @GetMapping("/list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        PageResponseDTO<All_MemberDTO> responseDTO = all_memberService.list(pageRequestDTO);
        log.info("@@@@@@@@@@@@list@@@@@@@@@@@" + responseDTO);
        model.addAttribute("responseDTO", responseDTO);
    }

    @GetMapping("register")
    public void registerGet() {

    }

    @PostMapping("register")
    public String registerPost(@Valid All_MemberDTO all_memberDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("all_memberDTO POST register.@.@.@.@.@>>@>@>@>@>@>@");

        if(bindingResult.hasErrors()) {
            log.info("has errors @@@@@@@@@@@@@@@@");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/main/register";
        }

        log.info("board@@@@@@@@@@@@@@@@@@@@@ ----" + all_memberDTO);

        Long all_id = all_memberService.register(all_memberDTO);
        redirectAttributes.addFlashAttribute("result", all_id+" 생성 완료");
        return "redirect:/main/list";
    }

    @GetMapping({"/read", "/modify"})
    public void read(Long all_id, PageRequestDTO pageRequestDTO, Model model) {
        All_MemberDTO all_memberDTO = all_memberService.readOne(all_id);
        log.info(all_memberDTO);
        model.addAttribute("dto", all_memberDTO);
    }

    @PostMapping("/modify")
    public String modify(PageRequestDTO pageRequestDTO,
                         @Valid All_MemberDTO all_memberDTO, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        log.info("board modify post ..........@@@@@@@@@@@@@@@@" + all_memberDTO);

        if(bindingResult.hasErrors()) {
            log.info("has errors..........");
            String link = pageRequestDTO.getLink();
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("all_id", all_memberDTO.getAll_id());
            return "redirect:/board/modify" + link;
        }

        all_memberService.modify(all_memberDTO);
        redirectAttributes.addFlashAttribute("result", "modified");
        redirectAttributes.addAttribute("all_id", all_memberDTO.getAll_id());
        return "redirect:/main/read";
    }

    @PostMapping("/remove")
    public String remove(Long all_id, RedirectAttributes redirectAttributes) {
        log.info("remove post..." + all_id);
        all_memberService.remove(all_id);
        redirectAttributes.addFlashAttribute("result", "removed");
        return "redirect:/main/list";
    }
}
