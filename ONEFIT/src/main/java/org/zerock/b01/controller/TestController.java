package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
@Log4j2
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/recruit_list")
    public void recruit_list() {
        log.info("recruit_list");
    }

    @GetMapping("/index")
    public void index() {
        log.info("index");
    }

    @GetMapping("/trainer_view")
    public void trainer_view(){
        log.info("trainer_view");
    }

    @GetMapping("/my")
    public void my(){
        log.info("my");
    }
}
