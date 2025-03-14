package org.zerock.b01.controller.seo;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.service.All_MemberService;

import java.security.Principal;

@Controller
@RequestMapping("/member")
@Log4j2
@RequiredArgsConstructor
public class All_MemberController {

    private final All_MemberService all_memberService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_board")
    public void my_board() {
        log.info("my_board");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_default_page")
    public void my_default_page() {
        log.info("my_default_page");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my_business_page")
    public void my_business_page() {
        log.info("my_business_page");
    }

    @GetMapping("/login")
    public void login() {
        log.info("login");
    }

    @GetMapping("/join")
    public void joinGET() {
        log.info("join get........ddada");
    }

    @PostMapping("/join")
    public String joinPOST(All_MemberDTO all_memberDTO, RedirectAttributes redirectAttributes) {
        log.info("join post........");
        log.info(all_memberDTO);

        try {
            all_memberService.join(all_memberDTO);
        } catch (All_MemberService.MidExistException e) {
            redirectAttributes.addFlashAttribute("error", "all_id");
            return "redirect:/member/join";
        }

        redirectAttributes.addFlashAttribute("result", "success");
        return "redirect:/member/login";
    }
}
