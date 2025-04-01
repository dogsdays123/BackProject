//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.dto.PageRequestDTO;
//import org.zerock.b01.dto.PageResponseDTO;
//import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
//import org.zerock.b01.service.boardService.NoticeBoardService;
//
//import java.util.Arrays;
//import java.util.UUID;
//
//@SpringBootTest
//@Log4j2
//public class NoticeBoardServiceTests {
//
//    @Autowired
//    private NoticeBoardService noticeBoardService;
//
//    @Test
//    public void testRegisterNotice() {
//
//        String allId = "member1";
//
//        All_Member all_member =All_Member.builder().allId(allId).build();
//
//        log.info(noticeBoardService.getClass().getName());
//
//        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
//                .nTitle("Sample Title...")
//                .nContent("Sample Content...")
//                .allMember(all_member)
//                .nHits(0)
//                .build();
//
//        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);
//
//        log.info("NoticeID: " + noticeId);
//    }
//
//    @Test
//    public void testModifyNotice() {
//
//        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
//                .noticeId(210L)
//                .nTitle("Updated...210")
//                .nContent("Updated...210")
//                .build();
//
//        noticeBoardService.modifyNotice(noticeBoardDTO);
//    }
//
//    @Test
//    public void testListNotice() {
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .type("tc")
//                .keyword("1")
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<NoticeBoardDTO> responseDTO = noticeBoardService.listNotice(pageRequestDTO);
//
//        log.info(responseDTO);
//
//    }
//
//    @Test
//    public void testRegisterWithImages() {
//
//        String allId = "member1";
//
//        All_Member all_member =All_Member.builder().allId(allId).build();
//
//        log.info(noticeBoardService.getClass().getName());
//
//        NoticeBoardDTO noticeBoardDTO = NoticeBoardDTO.builder()
//                .nTitle("File..Sample Title..")
//                .nContent("Sample Content..")
//                .allMember(all_member)
//                .build();
//
//        noticeBoardDTO.setFileNames(
//                Arrays.asList(
//                        UUID.randomUUID()+"_aaa.jpg",
//                        UUID.randomUUID()+"_bbb.jpg",
//                        UUID.randomUUID()+"_bbb.jpg"
//                ));
//
//        Long noticeId = noticeBoardService.registerNotice(noticeBoardDTO);
//
//        log.info("noticeId: " + noticeId);
//    }
//}
