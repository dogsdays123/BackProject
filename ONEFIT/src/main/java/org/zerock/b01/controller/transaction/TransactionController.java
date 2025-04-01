package org.zerock.b01.controller.transaction;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.transactionDTO.*;
import org.zerock.b01.repository.transactionRepository.InterestRepository;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.transactionService.ProductReplyService;
import org.zerock.b01.service.transactionService.ProductService;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@Log4j2
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController {

    private final ProductService productService;
    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;
    private final ProductReplyService productReplyService;

    // *** 테이블 리셋 후 게시글 등록 시 주의사항 ***
    // 카테고리 데이터 채우기
    // 회원 임시 데이터 채우기
    // 로그인 상태에서 밀었다면 쿠키 지우기!

    // 필터링 & 검색 기능

    @ModelAttribute
    public void Profile(All_MemberDTO all_memberDTO, Model model, Authentication authentication) {
        // 인증 정보가 없다면 null 설정
        if (authentication == null) {
            log.info("###### 인증 정보 없음");
            model.addAttribute("all_memberDTO", null);
        } else {
            // 소셜 로그인인 경우 OAuth2AuthenticationToken 처리
            if (authentication instanceof OAuth2AuthenticationToken) {
                OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
                OAuth2AuthenticatedPrincipal principal = token.getPrincipal();

                // OAuth2 인증을 통해 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(principal.getName());
                log.info("##### 소셜 로그인 사용자 정보: " + all_memberDTO);
            }
            // 일반 로그인인 경우 UsernamePasswordAuthenticationToken 처리
            else if (authentication instanceof UsernamePasswordAuthenticationToken) {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                // token.getPrincipal()이 MemberSecurityDTO 타입이라면, 이를 MemberSecurityDTO로 캐스팅
                MemberSecurityDTO principal = (MemberSecurityDTO) token.getPrincipal();
                String username = principal.getAllId(); // MemberSecurityDTO에서 사용자 이름 가져오기

                // 일반 로그인 사용자 정보 가져오기
                all_memberDTO = all_memberService.readOne(username);
                log.info("##### 일반 로그인 사용자 정보: " + all_memberDTO);
            }
        }

        //유저정보(일반, 개인) 전역에 갖고오기
        User_MemberDTO user_MemberDTO = member_Set_Type_Service.userRead(all_memberDTO.getAllId());
        Business_MemberDTO business_memberDTO = member_Set_Type_Service.BusinessRead(all_memberDTO.getAllId());

        //유저정보(일반Default)가 존재할 때
        if (all_memberDTO != null) {
            model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가

            //유저정보(개인User)가 있을 때
            if (user_MemberDTO != null) {
                model.addAttribute("user_memberDTO", user_MemberDTO);
                model.addAttribute("memberTypeAgree", "user");
                log.info("개인회원전역@@@@@@@@@" + user_MemberDTO);

                //유저정보(기업Business)가 있을 때
            } else if (business_memberDTO != null) {
                model.addAttribute("business_memberDTO", business_memberDTO);
                model.addAttribute("memberTypeAgree", "business");
                log.info("기업회원전역@@@@@@@@@" + business_memberDTO);

                //유저정보(개인User)가 없을 때
            } else {
                model.addAttribute("memberTypeAgree", "default");
                log.info("일반회원정보없음@@@@@@@@@");
            }

            //유저정보(일반Default)가 없을 때
        } else {
            model.addAttribute("all_memberDTO", null);
        }
        model.addAttribute("sidebar", false);
        log.info("회원전역@@@@@@@@@" + all_memberDTO);
    }
    //유저모든정보

    // 거래 게시판
    @GetMapping("/transa_list")
    public void list(PageRequestDTO pageRequestDTO, Model model) {
        log.info("*****************************************************************");
        log.info("/transaction/transa_list - GET");
        log.info("*****************************************************************");

        pageRequestDTO.setSize(12);
        PageResponseDTO<ProductListAllDTO> responseDTO = productService.listWithAllProducts(pageRequestDTO);

        if (pageRequestDTO.getTypes() != null) {
            model.addAttribute("keyword", pageRequestDTO.getKeyword());
        }

        if (pageRequestDTO.getMinPrice() != null) {
            model.addAttribute("minPrice", pageRequestDTO.getMinPrice());
        }

        if (pageRequestDTO.getMaxPrice() != null) {
            model.addAttribute("maxPrice", pageRequestDTO.getMaxPrice());
        }

        if (pageRequestDTO.getOnSale() != null) {
            model.addAttribute("onSale", pageRequestDTO.getOnSale());
        }

        if (pageRequestDTO.getReserved() != null) {
            model.addAttribute("reserved", pageRequestDTO.getReserved());
        }

        if (pageRequestDTO.getSoldOut() != null) {
            model.addAttribute("soldOut", pageRequestDTO.getSoldOut());
        }

        Boolean interested = pageRequestDTO.getInterested();

        if (pageRequestDTO.getAllId() != null && (interested == null || !interested)) {
            model.addAttribute("listTitle", "거래 게시판: '내가 쓴 게시글'");
        }

        if (Boolean.TRUE.equals(interested)) {
            model.addAttribute("listTitle", "거래 게시판: '나의 관심상품'");
        }

        log.info(pageRequestDTO.toString());

        model.addAttribute("responseDTO", responseDTO);
    }

    // 기구 판매 게시글 (읽기) - Get
    // 기구 판매 게시글 (수정) - Get
    @GetMapping({"/transa_eq_read", "/transa_eq_modify"})
    @Operation
    public void eqReadModifyGet(Long productId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("******************************************************************");
        log.info("/transaction/transa_eq_read - GET");
        log.info("/transaction/transa_eq_modify - GET");
        log.info("******************************************************************");

        log.info("productId: {}", productId);

        EquipmentDTO equipmentDTO = productService.readEquipmentOne(productId);
        equipmentDTO.setInterestCount(productService.countInterest(productId));
        log.info("equipmentDTO: {}", equipmentDTO);

        PageResponseDTO<ProductReplyDTO> responseDTO = productReplyService.getListOfProduct(productId, pageRequestDTO);
        log.info("responseDTO: {}", responseDTO);

        All_MemberDTO all_memberDTO = all_memberService.readOne(equipmentDTO.getAllId());
        model.addAttribute("memberType", all_memberDTO.getMemberType());

        model.addAttribute("dto", equipmentDTO);
        model.addAttribute("responseDTO", responseDTO);
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
    // 시설 판매 게시글 (수정) - Get
    @GetMapping({"/transa_fa_read", "/transa_fa_modify"})
    public void faReadGet(Long productId, PageRequestDTO pageRequestDTO, Model model) {
        log.info("***************************************************************");
        log.info("/transaction/transa_fa_read - GET");
        log.info("/transaction/transa_fa_modify - GET");
        log.info("***************************************************************");

        log.info("productId: {}", productId);

        FacilityDTO facilityDTO = productService.readFacilityOne(productId);
        facilityDTO.setInterestCount(productService.countInterest(productId));
        log.info("facilityDTO: {}", facilityDTO);

        PageResponseDTO<ProductReplyDTO> responseDTO = productReplyService.getListOfProduct(productId, pageRequestDTO);
        log.info("responseDTO: {}", responseDTO);

        All_MemberDTO all_memberDTO = all_memberService.readOne(facilityDTO.getAllId());
        model.addAttribute("memberType", all_memberDTO.getMemberType());

        model.addAttribute("dto", facilityDTO);
        model.addAttribute("responseDTO", responseDTO);
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

    // 기구 판매 게시글 (수정) - Post
    @PostMapping("/transa_eq_modify")
    public String eqModifyPost(@Valid @ModelAttribute EquipmentDTO equipmentDTO, BindingResult bindingResult,
                               PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("***************************************************************");
        log.info("/transaction/transa_eq_modify - POST");
        log.info("***************************************************************");

        if (bindingResult.hasErrors()) {
            log.info("has errors.........");
            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("productId", equipmentDTO.getProductId());

            return "redirect:/transaction/transa_fa_modify?" + link;
        }

        productService.modifyEquipment(equipmentDTO);

        redirectAttributes.addFlashAttribute("result", "eq-modified");
        redirectAttributes.addAttribute("productId", equipmentDTO.getProductId());

        return "redirect:/transaction/transa_eq_read";
    }

    // 시설 판매 게시글 (수정) - Post
    @PostMapping("/transa_fa_modify")
    public String faModifyPost(@Valid @ModelAttribute FacilityDTO facilityDTO, BindingResult bindingResult,
                               PageRequestDTO pageRequestDTO, RedirectAttributes redirectAttributes) {
        log.info("***************************************************************");
        log.info("/transaction/transa_fa_modify - POST");
        log.info("***************************************************************");

        if (bindingResult.hasErrors()) {
            log.info("has errors.........");
            String link = pageRequestDTO.getLink();

            redirectAttributes.addFlashAttribute("errors", bindingResult.getAllErrors());
            redirectAttributes.addAttribute("productId", facilityDTO.getProductId());

            return "redirect:/transaction/transa_fa_modify?" + link;
        }

        productService.modifyFacility(facilityDTO);

        redirectAttributes.addFlashAttribute("result", "fa-modified");
        redirectAttributes.addAttribute("productId", facilityDTO.getProductId());

        return "redirect:/transaction/transa_fa_read";
    }

    @PostMapping("/remove")
    public String remove(ProductDTO productDTO, RedirectAttributes redirectAttributes) {
        Long productId = productDTO.getProductId();
        log.info("board POST remove.........." + productId);

        productService.removeProduct(productId);

        // 게시물이 DB상에서 삭제되었다면 첨부파일 삭제
        log.info(productDTO.getImageFileNames());
        // 삭제할 파일 리스트 가져오기
        List<String> fileNames = productDTO.getImageFileNames();
        if (fileNames != null && fileNames.size() > 0) {
            removeFiles(fileNames);
        }
        // 삭제 결과를 플래시 속성에 추가
        redirectAttributes.addFlashAttribute("result", "removed");

        return "redirect:/transaction/transa_list";
    }

    @Value("C:\\upload")
    private String uploadPath;

    public void removeFiles(List<String> files) {
        for (String fileName : files) {
            // 파일 경로를 이용해 리소스 객체 생성
            Resource resource = new FileSystemResource(uploadPath + File.separator + fileName);
            String resourceName = resource.getFilename();

            try {
                String contentType = Files.probeContentType(resource.getFile().toPath());
                resource.getFile().delete();

            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }

    @GetMapping("/interest")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleInterest(
            @RequestParam("allId") String allId,
            @RequestParam("productId") Long productId) {
        log.info("***************************************************************");
        log.info("/transaction/interest - GET");
        log.info("***************************************************************");

        log.info("allId: {}", allId);
        log.info("productId: {}", productId);

        InterestDTO interestDTO = new InterestDTO();
        interestDTO.setProductId(productId);
        interestDTO.setAllId(allId);

        // 관심 상품 등록/취소 처리 (예제)
        boolean isRegistered = productService.registerInterest(interestDTO);
        Long countInterest = productService.countInterest(productId);

        // 응답 메시지 생성
        Map<String, Object> response = new HashMap<>();
        response.put("message", isRegistered ? "관심 상품에서 제거되었습니다." : "관심 상품에 추가되었습니다.");
        response.put("status", isRegistered ? "removed" : "added");
        response.put("countInterest", countInterest);
        response.put("productId", productId);
        response.put("allId", allId);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/interest_chk_me")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleInterestChkMe(
            @RequestParam("allId") String allId,
            @RequestParam("productId") Long productId) {
        log.info("***************************************************************");
        log.info("/transaction/interestChkMe - GET");
        log.info("***************************************************************");

        boolean isRegistered = productService.isRegisteredInterest(productId, allId);

        Map<String, Object> response = new HashMap<>();
        response.put("status", isRegistered ? "yes" : "no");

        return ResponseEntity.ok(response);
    }


}
