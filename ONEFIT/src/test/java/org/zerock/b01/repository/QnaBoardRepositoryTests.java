package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardListAllDTO;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;
import org.zerock.b01.repository.boardRepository.QnaBoardRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class QnaBoardRepositoryTests {

    @Autowired
    private QnaBoardRepository qnaBoardRepository;

    @Autowired
    private BoardReplyRepository boardReplyRepository;

    @Test
    public void testInsertQna() {

        String allId = "member1"; //수정

        All_Member all_member =All_Member.builder().allId(allId).build(); //수정

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Qna_Board qna_board = Qna_Board.builder()
                    .qTitle("title..." + i)
                    .qContent("content..." + i)
                    .allMember(all_member)
                    .qHits(0)
                    .build();

            Qna_Board result = qnaBoardRepository.save(qna_board);
            log.info("QnaId: " + result.getQnaId());
        });
    }

    @Test
    public void testSelectQna() {
        Long qnaId =100L;

        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaId);

        Qna_Board qna_board = result.orElseThrow();

        log.info(qna_board);
    }

    @Test
    public void testUpdateQna() {
        Long qnaId =100L;

        Optional<Qna_Board> result = qnaBoardRepository.findById(qnaId);

        Qna_Board qna_board = result.orElseThrow();

        qna_board.changeQna("update...title 100", "update.content 100");

        qnaBoardRepository.save(qna_board);
    }

    @Test
    public void testDeleteQna() {
        Long qnaId =1L;

        qnaBoardRepository.deleteById(qnaId);
    }

    @Test
    public void testQnaPaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());

        Page<Qna_Board> result = qnaBoardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<Qna_Board> todoList = result.getContent();

        todoList.forEach(qna_board -> log.info(qna_board));
    }

    @Test
    public void testSearchQna1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());

        qnaBoardRepository.searchQna1(pageable);
    }

    @Test
    public void testSearchQnaAll() {

        String[] types = {"t", "c", "m"};

        String keyword = "1";

        LocalDate startDate = LocalDate.now();

        LocalDate endDate = LocalDate.now().plusDays(1);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("qnaId").descending());

        Page<Qna_Board> result = qnaBoardRepository.searchQnaAll(types, keyword, startDate, endDate,pageable);

        //total pages
        log.info(result.getTotalPages());
        //page size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(qna_board -> log.info(qna_board));
    }

    @Test
    public void testSearchQnaReplyCount() {

        String[] types = {"t", "c", "m"};

        String keyword = "1232";

        LocalDate startDate = LocalDate.now().minusDays(7);

        LocalDate endDate = LocalDate.now().plusDays(1);

        Pageable pageable = PageRequest.of(0,10, Sort.by("qnaId").descending());

        Page<BoardListReplyCountDTO> result = qnaBoardRepository.searchWithQnaReplyCount(types, keyword,
                startDate, endDate,pageable);

        //total pages
        log.info(result.getTotalPages());
        //pag size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(qnaBoard ->log.info(qnaBoard));
    }

    @Test
    public void testInsertWithQnaFile() {

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        Qna_Board qna_board =Qna_Board.builder()
                .qTitle("file Test")
                .qContent("첨부파일 테스트")
                .allMember(all_member)
                .build();

        for (int i = 0; i < 3; i++) {

            qna_board.addQnaFiles(UUID.randomUUID().toString(), "file" + i + ".jpg");
        } //end for

        qnaBoardRepository.save(qna_board);
    }

    @Test
    public void testReadWithQnaFiles() {

        Optional<Qna_Board> result = qnaBoardRepository.findByIdWithQnafiles(101L);

        Qna_Board qna_board = result.orElseThrow();

        log.info(qna_board);
        log.info("---------------");
        log.info(qna_board.getBoardFileSet());
    }

    @Transactional
    @Commit
    @Test
    public void testModifyQnaFiles() {

        Optional<Qna_Board> result = qnaBoardRepository.findByIdWithQnafiles(101L);

        Qna_Board qna_board = result.orElseThrow();

        qna_board.clearQnaFiles();

        for (int i = 0; i < 2; i++) {

            qna_board.addQnaFiles(UUID.randomUUID().toString(), "updatefile" + i + ".jpg");
        }
        qnaBoardRepository.save(qna_board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveQnaAll() {

        Long qnaId = 100L;

        boardReplyRepository.deleteByQnaBoard_qnaId(qnaId);

        qnaBoardRepository.deleteById(qnaId);
    }

    @Test
    public void testInsertQnaAll() {

        String allId = "member1";

        All_Member all_member = All_Member.builder().allId(allId).build();

        for (int i = 0; i < 100; i++) {
            Qna_Board qna_board = Qna_Board.builder()
                    .qTitle("Title.." + i)
                    .qContent("Content.." + i)
                    .allMember(all_member)
                    .qHits(0)
                    .build();

            for (int j = 0; j < 3; j++) {
                if(i % 5 == 0) {
                    continue;
                }
                qna_board.addQnaFiles(UUID.randomUUID().toString(), "file" + i + ".jpg");
            }
            qnaBoardRepository.save(qna_board);
        }
    }

    @Transactional
    @Test
    public void testSearchQnaFileReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("qnaId").descending());

        Page<QnaBoardListAllDTO> result = qnaBoardRepository
                  .searchWithQnaAll(null, null, null, null, pageable);

        log.info("---------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(qnaBoardListAllDTO -> log.info(qnaBoardListAllDTO));
    }

}
