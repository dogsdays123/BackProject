package org.zerock.b01.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MainController {

    @GetMapping("/main")
    public void main(){
        log.info("main");
    }
}
