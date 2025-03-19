package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.service.boardService.QnaBoardService;

@SpringBootTest
@Log4j2
public class QnaBoardServiceTests {

    @Autowired
    private QnaBoardService qnaBoardService;

    @Test
    public void testRegisterQna() {

        String allId = "member1"; //수정

        All_Member all_member =All_Member.builder().allId(allId).build(); //수정

        log.info(qnaBoardService.getClass().getName());

        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
                .qTitle("Sample Title...")
                .qContent("Sample Content...")
                .allMember(all_member)  //수정
                .qHits(0)
                .build();

        Long qnaId = qnaBoardService.registerQna(qnaBoardDTO);

        log.info("QnaID: " + qnaId);
    }

    @Test
    public void testModifyQna() {

        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
                .qnaId(210L)
                .qTitle("Updated...210")
                .qContent("Updated...210")
                .build();

        qnaBoardService.modifyQna(qnaBoardDTO);
    }

    @Test
    public void testListQna() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tcm")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<QnaBoardDTO> responseDTO = qnaBoardService.listQna(pageRequestDTO);

        log.info(responseDTO);

    }
}
