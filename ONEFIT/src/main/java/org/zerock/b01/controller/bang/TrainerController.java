package org.zerock.b01.controller.bang;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.service.trainerService.TrainerService;

@Controller
@RequestMapping("/trainer")
@Log4j2
@RequiredArgsConstructor
public class TrainerController {
    private final TrainerService trainerService;

    @GetMapping("/trainer_list")
    public void trainer_list() {
        log.info("trainer_list");
    }

    @GetMapping("/trainer_register")
    public void trainer_register_GET() {
        log.info("trainer_register_GET");
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
        return "redirect:/trainer/trainer_list";
    }

    @GetMapping("/trainer_view")
    public void trainer_view() {
        log.info("trainer_view");
    }

    @GetMapping("/trainer_modify")
    public void trainer_modify() {
        log.info("trainer_modify");
    }
}
