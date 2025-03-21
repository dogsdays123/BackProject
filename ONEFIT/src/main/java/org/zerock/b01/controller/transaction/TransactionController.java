package org.zerock.b01.controller.transaction;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;
import org.zerock.b01.service.transactionService.ProductService;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final ProductService productService;

    // 거래 게시판
    @GetMapping("/transa_list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("*****************************************************************");
        log.info("/transaction/transa_list - GET");
        log.info("*****************************************************************");

        pageRequestDTO.setSize(12);
        PageResponseDTO<ProductListAllDTO> responseDTO = productService.listWithAllProducts(pageRequestDTO);

        log.info("list: " + responseDTO);

        model.addAttribute("responseDTO", responseDTO);
    }

    // 기구 판매 게시글 (읽기) - Get
    // 기구 판매 게시글 (수정) - Get
    @GetMapping({"/transa_eq_read", "/transa_eq_modify"})
    @Operation
    public void eqReadModifyGet(Long productId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("******************************************************************");
        log.info("/transaction/transa_eq_read - GET");
        log.info("******************************************************************");

        log.info("productId: {}", productId);

        EquipmentDTO equipmentDTO = productService.readEquipmentOne(productId);

        log.info("equipmentDTO: {}", equipmentDTO);

        model.addAttribute("dto", equipmentDTO);
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

        // DB에 저장
        Long equipmentId = productService.registerEquipment(equipmentDTO);

        log.info("*** (상품 -기구) 거래 게시글 등록 완료 ***  equipmentId:{}", equipmentId);

        return "redirect:/transaction/transa_list";
    }

    // 시설 판매 게시글 (읽기) - Get
    @GetMapping("/transa_fa_read")
    public void faReadGet(Long productId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("***************************************************************");
        log.info("/transaction/transa_fa_read - GET");
        log.info("***************************************************************");

        log.info("productId: {}", productId);

        FacilityDTO facilityDTO = productService.readFacilityOne(productId);

        log.info("facilityDTO: {}", facilityDTO);

        model.addAttribute("dto", facilityDTO);
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
        // 판매 상품 구분 : 2(시설)
        facilityDTO.setPRoles(2);
        // 거래 상태 : 판매중
        facilityDTO.setPStatus("판매중");

        // 데이터 확인
        log.info(facilityDTO);

        // DB에 저장
        Long facilityId = productService.registerFacility(facilityDTO);

        log.info("*** (상품 -시설) 거래 게시글 등록 완료 ***  facilityId:{}", facilityId);

        return "redirect:/transaction/transa_list";

    }
}
