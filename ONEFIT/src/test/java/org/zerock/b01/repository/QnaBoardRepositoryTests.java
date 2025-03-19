//package org.zerock.b01.repository;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.domain.board.Qna_Board;
//import org.zerock.b01.repository.boardRepository.QnaBoardRepository;
//
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//@Log4j2
//public class QnaBoardRepositoryTests {
//
//    @Autowired
//    private QnaBoardRepository qnaBoardRepository;
//
//    @Test
//    public void testInsertQna() {
//
//        String all_id = "member1"; //수정
//
//        All_Member all_member =All_Member.builder().all_id(all_id).build(); //수정
//
//        IntStream.rangeClosed(1, 100).forEach(i -> {
//            Qna_Board qna_board = Qna_Board.builder()
//                    .qTitle("title..." + i)
//                    .qContent("content..." + i)
//                    .allMember(all_member)
//                    .qHits(0)
//                    .build();
//
//            Qna_Board result = qnaBoardRepository.save(qna_board);
//            log.info("QnaId: " + result.getQnaId());
//        });
//    }
//
//    @Test
//    public void testSelectQna() {
//        Long qnaId =100L;
//
//        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaId);
//
//        Qna_Board qna_board = result.orElseThrow();
//
//        log.info(qna_board);
//    }
//
//    @Test
//    public void testUpdateQna() {
//        Long qnaId =110L;
//
//        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaId);
//
//        Qna_Board qna_board = result.orElseThrow();
//
//        qna_board.changeQna("update...title 100", "update.content 100");
//
//        qnaBoardRepository.save(qna_board);
//    }
//
//    @Test
//    public void testDeleteQna() {
//        Long qnaId =1L;
//
//        qnaBoardRepository.deleteById(qnaId);
//    }
//
//    @Test
//    public void testQnaPaging() {
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());
//
//        Page<Qna_Board> result = qnaBoardRepository.findAll(pageable);
//
//        log.info("total count: " + result.getTotalElements());
//        log.info("total pages: " + result.getTotalPages());
//        log.info("page number: " + result.getNumber());
//        log.info("page size: " + result.getSize());
//
//        List<Qna_Board> todoList = result.getContent();
//
//        todoList.forEach(qna_board -> log.info(qna_board));
//    }
//
//    @Test
//    public void testSearchQna1() {
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());
//
//        qnaBoardRepository.searchQna1(pageable);
//    }
//
//    @Test
//    public void testSearchQnaAll() {
//
//        String[] types = {"t", "c", "m"};
//
//        String keyword = "1";
//
//        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());
//
//        Page<Qna_Board> result = qnaBoardRepository.searchQnaAll(types, keyword, pageable);
//
//        //total pages
//        log.info(result.getTotalPages());
//        //page size
//        log.info(result.getSize());
//        //pageNumber
//        log.info(result.getNumber());
//        //prev next
//        log.info(result.hasPrevious() + ": " + result.hasNext());
//
//        result.getContent().forEach(qna_board -> log.info(qna_board));
//    }
//}
