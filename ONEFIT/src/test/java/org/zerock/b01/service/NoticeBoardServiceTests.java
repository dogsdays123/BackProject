package org.zerock.b01.service;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.boardDTO.BoardFileDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;
import org.zerock.b01.service.boardService.NoticeBoardService;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class NoticeBoardServiceTests {

    @Autowired
    private NoticeBoardService noticeBoardService;

    @Test
    public void testRegisterNotice() {

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        log.info(noticeBoardService.getClass().getName());

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .nTitle("Sample Title...")
                .nContent("Sample Content...")
                .allMember(all_member)
                .nHits(0)
                .build();

        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);

        log.info("NoticeID: " + noticeId);
    }

    @Test
    public void testModifyNotice() {

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .noticeId(202L)
                .nTitle("Updated...202")
                .nContent("Updated...202")
                .build();

        noticeBoardDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));

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

    @Test
    public void testRegisterWithNoticeFiles() {

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        log.info(noticeBoardService.getClass().getName());

        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
                .nTitle("File..Sample Title..")
                .nContent("Sample Content..")
                .allMember(all_member)
                .build();

        noticeBoardDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_bbb.jpg"
                ));

        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);

        log.info("noticeId: " + noticeId);
    }

    @Test
    public void testReadNoticeAll() {

        Long noticeId = 203L;

        NoticeBoardDTO noticeBoardDTO = noticeBoardService.readNoticeOne(noticeId);

        log.info(noticeBoardDTO);

        for (String filename : noticeBoardDTO.getFileNames()) {
            log.info(filename);
        }
    }

    @Test
    public void testRemoveNoticeAll() {

        Long noticeId = 200L;

        noticeBoardService.removeNotice(noticeId);
    }

    @Test
    public void testListWithNoticeAll() {

        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1)
                .size(10)
                .build();

        PageResponseDTO<NoticeBoardListAllDTO> responseDTO = noticeBoardService.listWithNoticeAll(pageRequestDTO);
        List<NoticeBoardListAllDTO> dtoList = responseDTO.getDtoList();
        dtoList.forEach(noticeBoardListAllDTO -> {
            log.info(noticeBoardListAllDTO.getNoticeId() + ":" + noticeBoardListAllDTO.getNTitle());
            if (noticeBoardListAllDTO.getBoardFiles() != null) {
                for (BoardFileDTO boardFile :noticeBoardListAllDTO.getBoardFiles()){
                    log.info(boardFile);
                }
            }
            log.info("-----------------");
        });
    }
}
