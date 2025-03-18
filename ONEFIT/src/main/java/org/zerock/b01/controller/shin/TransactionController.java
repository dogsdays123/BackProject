package org.zerock.b01.controller.shin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;

import java.util.List;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    // 거래 게시판
    @GetMapping("/transa_list")
    public void list(Model model) {

    }

    // 기구 판매 게시글 (읽기) - Get
    @GetMapping("/transa_eq_read")
    public void eqReadGet(Model model) {

    }

    // 기구 판매 게시글 (등록) - Get
    @GetMapping("/transa_eq_register")
    public void eqRegisterGet(Model model) {

    }

    // 기구 판매 게시글 (등록) - Post
    @PostMapping("/transa_eq_register")
    public String eqRegisterPost(@Valid @ModelAttribute EquipmentDTO equipmentDTO, BindingResult bindingResult,
                                    RedirectAttributes redirectAttributes) {
        log.info("*****************************************************************");
        log.info("/transaction/transa_eq_register - POST");
        log.info("*****************************************************************");

        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors.........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/transaction/transa_list";
        }

        // 기본값 세팅
        // 판매 상품 구분 : 1(기구)
        equipmentDTO.setPRoles(1);
        // 거래 상태 : 판매중
        equipmentDTO.setPStatus("판매중");

        // 데이터 확인
        log.info(equipmentDTO);

        return "redirect:/transaction/transa_list";
    }

    // 시설 판매 게시글 (읽기) - Get
    @GetMapping("/transa_fa_read")
    public void faReadGet(Model model) {

    }

    // 시설 판매 게시글 (등록) - Get
    @GetMapping("/transa_fa_register")
    public void faRegisterGet(Model model) {

    }

    // 시설 판매 게시글 (등록) - Post
    @PostMapping("/transa_fa_register")
    public String faRegisterPost(@Valid @ModelAttribute FacilityDTO facilityDTO, BindingResult bindingResult,
                               RedirectAttributes redirectAttributes) {

        log.info("*****************************************************************");
        log.info("/transaction/transa_fa_register - POST");
        log.info("*****************************************************************");

        if (bindingResult.hasErrors()) {
            log.info("bindingResult has errors.........");
            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            return "redirect:/transaction/transa_list";
        }

        // 기본값 세팅
        // 판매 상품 구분 : 1(시설)
        facilityDTO.setPRoles(2);
        // 거래 상태 : 판매중
        facilityDTO.setPStatus("판매중");

        // 데이터 확인
        log.info(facilityDTO);

        return "redirect:/transaction/transa_list";

    }
}
