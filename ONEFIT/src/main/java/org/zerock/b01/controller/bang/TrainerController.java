package org.zerock.b01.controller.bang;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/trainer")
@Log4j2
@RequiredArgsConstructor
public class TrainerController {
    @GetMapping("/trainer_list")
    public void trainer_list() { log.info("trainer_list"); }

    @GetMapping("/trainer_register")
    public void trainer_register() { log.info("trainer_register"); }

    @GetMapping("/trainer_view")
    public void trainer_view() { log.info("trainer_view"); }

    @GetMapping("/trainer_modify")
    public void trainer_modify() { log.info("trainer_modify"); }
}
