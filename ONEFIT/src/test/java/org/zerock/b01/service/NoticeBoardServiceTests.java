package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.service.boardService.NoticeBoardService;

@SpringBootTest
@Log4j2
public class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Test
    public void testRegisterNotice() {

        String all_id = "member1"; //수정

        All_Member all_member =All_Member.builder().all_id(all_id).build(); //수정

        log.info(noticeBoardService.getClass().getName());

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .nTitle("Sample Title...")
                .nContent("Sample Content...")
                .allMember(all_member)  //수정
                .nHits(0)
                .build();

        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);

        log.info("NoticeID: " + noticeId);
    }

    @Test
    public void testModifyNotice() {

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .noticeId(210L)
                .nTitle("Updated...210")
                .nContent("Updated...210")
                .build();

        noticeBoardService.modifyNotice(noticeBoardDTO);
    }

    @Test
    public void testListNotice() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .type("tc")
                .keyword("1")
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<NoticeBoardDTO> responseDTO = noticeBoardService.listNotice(pageRequestDTO);

        log.info(responseDTO);

    }
}
