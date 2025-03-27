//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.dto.PageRequestDTO;
//import org.zerock.b01.dto.PageResponseDTO;
//import org.zerock.b01.dto.boardDTO.BoardFileDTO;
//import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;
//import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
//import org.zerock.b01.dto.boardDTO.QnaBoardListAllDTO;
//import org.zerock.b01.service.boardService.QnaBoardService;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.UUID;
//
//@SpringBootTest
//@Log4j2
//public class QnaBoardServiceTests {
//
//    @Autowired
//    private QnaBoardService qnaBoardService;
//
//    @Test
//    public void testRegisterQna() {
//
//        String allId = "member1";
//
//        All_Member all_member =All_Member.builder().allId(allId).build();
//
//        log.info(qnaBoardService.getClass().getName());
//
//        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
//                .qTitle("Sample Title...")
//                .qContent("Sample Content...")
//                .allMember(all_member)
//                .qHits(0)
//                .build();
//
//        Long qnaId = qnaBoardService.registerQna(qnaBoardDTO);
//
//        log.info("QnaID: " + qnaId);
//    }
//
//    @Test
//    public void testModifyQna() {
//
//        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
//                .qnaId(201L)
//                .qTitle("Updated...201")
//                .qContent("Updated...201")
//                .build();
//
//        qnaBoardDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));
//
//        qnaBoardService.modifyQna(qnaBoardDTO);
//    }
//
//    @Test
//    public void testListQna() {
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .type("tcm")
//                .keyword("1")
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<QnaBoardDTO> responseDTO = qnaBoardService.listQna(pageRequestDTO);
//
//        log.info(responseDTO);
//
//    }
//
//    @Test
//    public void testRegisterWithQnaFiles() {
//
//        String allId = "member1";
//
//        All_Member all_member =All_Member.builder().allId(allId).build();
//
//        log.info(qnaBoardService.getClass().getName());
//
//        QnaBoardDTO qnaBoardDTO = QnaBoardDTO.builder()
//                .qTitle("File..Sample Title..")
//                .qContent("Sample Content..")
//                .allMember(all_member)
//                .build();
//
//        qnaBoardDTO.setFileNames(
//                Arrays.asList(
//                        UUID.randomUUID()+"_aaa.jpg",
//                        UUID.randomUUID()+"_bbb.jpg",
//                        UUID.randomUUID()+"_bbb.jpg"
//                ));
//
//        Long qnaId = qnaBoardService.registerQna(qnaBoardDTO);
//
//        log.info("qnaId: " + qnaId);
//    }
//
//    @Test
//    public void testReadQnaAll() {
//
//        Long qnaId = 202L;
//
//        QnaBoardDTO qnaBoardDTO = qnaBoardService.readQnaOne(qnaId);
//
//        log.info(qnaBoardDTO);
//
//        for (String filename : qnaBoardDTO.getFileNames()) {
//            log.info(filename);
//        }
//    }
//
//    @Test
//    public void testRemoveQnaAll() {
//
//        Long qnaId = 200L;
//
//        qnaBoardService.removeQna(qnaId);
//    }
//
//    @Test
//    public void testListWithQnaAll() {
//
//        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//                .page(1)
//                .size(10)
//                .build();
//
//        PageResponseDTO<QnaBoardListAllDTO> responseDTO = qnaBoardService.listWithQnaAll(pageRequestDTO);
//        List<QnaBoardListAllDTO> dtoList = responseDTO.getDtoList();
//        dtoList.forEach(qnaBoardListAllDTO -> {
//            log.info(qnaBoardListAllDTO.getQnaId() + ":" + qnaBoardListAllDTO.getQTitle());
//            if (qnaBoardListAllDTO.getBoardFiles() != null) {
//                for (BoardFileDTO boardFile :qnaBoardListAllDTO.getBoardFiles()){
//                    log.info(boardFile);
//                }
//            }
//            log.info("-----------------");
//        });
//    }
//}
