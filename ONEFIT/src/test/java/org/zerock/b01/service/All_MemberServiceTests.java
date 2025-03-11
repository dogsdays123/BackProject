package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

@SpringBootTest
@Log4j2
public class All_MemberServiceTests {
    @Autowired
    private All_MemberService all_MemberService;

    @Test
    public void testRegister(){
        log.info("--------------" + all_MemberService.getClass().getName());
        All_MemberDTO all_memberDTO = All_MemberDTO.builder()
                .name("name")
                .email("email")
                .a_member_id("user")
                .a_psw("1234")
                .roles("default")
                .build();

        Long all_id = all_MemberService.register(all_memberDTO);
        log.info("@@@@@@@@@@@@@@@@@bno : " + all_id);
    }

    @Test
    public void testModify(){
        All_MemberDTO all_memberDTO = All_MemberDTO.builder()
                .all_id(101L)
                .name("name")
                .email("email")
                .a_member_id("user")
                .a_psw("1234")
                .roles("default")
                .build();

        all_MemberService.modify(all_memberDTO);
    }

    @Test
    public void testList(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcw")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<All_MemberDTO> responseDTO = all_MemberService.list(pageRequestDTO);
        log.info(responseDTO);
    }
}
