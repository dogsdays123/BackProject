package org.zerock.b01.controller.trainer;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageRequestDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageResponseDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.repository.trainerRepository.TrainerRepository;
import org.zerock.b01.repository.trainerRepository.Trainer_ThumbnailsRepository;
import org.zerock.b01.security.dto.MemberSecurityDTO;
import org.zerock.b01.service.All_MemberService;
import org.zerock.b01.service.memberService.Member_Set_Type_Service;
import org.zerock.b01.service.trainerService.TrainerService;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/trainer")
@Log4j2
@RequiredArgsConstructor
public class TrainerController {
    @Value("${org.zerock.upload.thumbnailPath}")
    private String thumbnailPath;

    private final TrainerService trainerService;
    private final Trainer_ThumbnailsRepository trainerThumbnailsRepository;

    private final All_MemberService all_memberService;
    private final Member_Set_Type_Service member_Set_Type_Service;

    // 로그인 유저 프로필 Model Attribute
    @ModelAttribute
    public void Profile(All_MemberDTO all_memberDTO, Model model, Authentication authentication, HttpServletRequest request) {
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

        //유저정보(일반Default)가 존재할 때
        if (all_memberDTO != null) {
            model.addAttribute("all_memberDTO", all_memberDTO);  // 사용자 정보를 모델에 추가
            //유저정보(일반, 개인) 전역에 갖고오기
            User_MemberDTO user_MemberDTO = member_Set_Type_Service.userRead(all_memberDTO.getAllId());
            Business_MemberDTO business_memberDTO = member_Set_Type_Service.BusinessRead(all_memberDTO.getAllId());

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
        String currentUrl = request.getRequestURI();
        // URL에 따라서 분기
        if (currentUrl.contains("/member")) {
            model.addAttribute("sidebar", true);
        } else{
            model.addAttribute("sidebar", false);
        }

        model.addAttribute("checkId", false);
        model.addAttribute("checkEmail", false);

        List<All_MemberDTO> all_memberDTOList = all_memberService.readAllMember();
        model.addAttribute("all_memberDTOList", all_memberDTOList);
        log.info("모든회원@@@@@@@@@" + all_memberDTOList);
        log.info("회원전역@@@@@@@@@" + all_memberDTO);
    }

    @Operation
    @GetMapping("/trainer_list")
    public String trainer_list(HttpServletResponse response, TrainerPageRequestDTO pageRequestDTO, Model model) {
        log.info("trainer_list");
        TrainerPageResponseDTO<TrainerViewDTO> responseDTO = trainerService.list(pageRequestDTO);
        log.info(responseDTO);

        All_MemberDTO all_memberDTO = (All_MemberDTO) model.getAttribute("all_memberDTO");

        if (all_memberDTO != null && "business".equals(all_memberDTO.getMemberType())) {
            model.addAttribute("responseDTO", responseDTO);
            log.info("business trainer_list link");
            return "trainer/trainer_list";
        }

        if (all_memberDTO != null && "user".equals(all_memberDTO.getMemberType())) {
            return "redirect:/trainer/trainer_register";
        }

        String script = "<script>alert('접근 권한이 없습니다. 개인회원 또는 기업회원으로 로그인하거나 전환하세요.'); history.back();</script>";
        response.setContentType("text/html; charset=UTF-8");
        log.info("default trainer_list link");

        try {
            response.getWriter().write(script);
            response.getWriter().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @GetMapping("/trainer_register")
    public String trainer_register_GET(HttpServletResponse response, TrainerPageRequestDTO pageRequestDTO, Model model) {
        log.info("trainer_register_GET");
        User_MemberDTO user_memberDTO = (User_MemberDTO) model.getAttribute("user_memberDTO");

        if (user_memberDTO != null) {
            int count = trainerService.trainerCount(user_memberDTO.getUserId());
            if (count > 0) {
                String script = "<script>alert('이미 작성한 이력서가 있습니다. 마이페이지에서 확인하세요.'); window.location.href = '/main';</script>";
                response.setContentType("text/html; charset=UTF-8");
                log.info("default trainer_list link");

                try {
                    response.getWriter().write(script);
                    response.getWriter().flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        }
        model.addAttribute("request", pageRequestDTO);
        log.info("default trainer_register link");
        return "trainer/trainer_register";
    }

    @PostMapping("/trainer_register")
    public String trainer_register_POST(@Valid TrainerDTO trainerDTO, BindingResult bindingResult) {
        log.info("trainer_register_POST");

        if (bindingResult.hasErrors()) {
            log.info("trainer_register_POST_ERROR");
            log.info(bindingResult.getAllErrors());
            return "redirect:/trainer/trainer_register";
        }

        log.info(trainerDTO);

        Long trainer_id = trainerService.registerTrainer(trainerDTO);
        return "redirect:/trainer/trainer_view?tid=" + trainer_id;
    }

    @Operation
    @GetMapping({"/trainer_view", "/trainer_modify"})
    public void trainer_view(Long tid, TrainerPageRequestDTO pageRequestDTO, Model model) {
        log.info("trainer_view or trainer_modify");
        log.info(tid);
        TrainerViewDTO trainerViewDTO = trainerService.viewOne(tid);

        log.info(trainerViewDTO);
        model.addAttribute("dto", trainerViewDTO);
        model.addAttribute("request", pageRequestDTO);
    }

    @PostMapping("/trainer_modify")
    public String trainer_modify_POST(@Valid TrainerDTO trainerDTO, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        log.info("trainer_modify_POST");

        if (bindingResult.hasErrors()) {
            log.info("trainer_modify_POST_ERROR");
            log.info(bindingResult.getAllErrors());
            redirectAttributes.addAttribute("tid", trainerDTO.getTrainerId());
            return "redirect:/trainer/trainer_modify";
        }

        log.info(trainerDTO);

        Long trainer_id = trainerService.registerTrainer(trainerDTO);
        redirectAttributes.addAttribute("tid", trainerDTO.getTrainerId());
        return "redirect:/trainer/trainer_view";
    }

    @PostMapping("/trainer_delete")
    public String trainer_delete_POST(TrainerDTO trainerDTO, RedirectAttributes redirectAttributes) {
        log.info("trainer_delete_POST");
        Long tid = trainerDTO.getTrainerId();

        // 삭제되기 전에 미리 이름을 가져와야 서버에서 파일을 지울 수 있음
        List<Trainer_Thumbnails> ttlist = trainerThumbnailsRepository.findByTid(tid).stream().map(Optional::orElseThrow).sorted().collect(Collectors.toList());
        trainerService.removeTrainer(tid);

        // 강사정보 삭제 끝났으면 서버에서 첨부파일들을 삭제
        ttlist.forEach(ttt -> {
            String fileName = ttt.getThumbnailUuid() + "_" + ttt.getImgname();

            try {
                Path filePath = Paths.get(thumbnailPath, fileName);

                if (Files.exists(filePath)) {
                    log.info(ttt.getThumbnailUuid());
                    Files.delete(filePath);
                } else {
                    throw new NoSuchFileException(filePath.toString());
                }
            } catch (Exception e) {
                log.info(ttt.getThumbnailUuid() + "_" + ttt.getImgname() + "Not Found");
                e.printStackTrace();
            }
        });

        return "redirect:/trainer/trainer_list";
    }
}
