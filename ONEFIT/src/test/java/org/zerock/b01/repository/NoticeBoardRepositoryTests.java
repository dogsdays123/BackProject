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
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.boardDTO.BoardListReplyCountDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardListAllDTO;
import org.zerock.b01.repository.boardRepository.BoardReplyRepository;
import org.zerock.b01.repository.boardRepository.NoticeBoardRepository;
import org.zerock.b01.service.All_MemberService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class NoticeBoardRepositoryTests {

    @Autowired
    private NoticeBoardRepository noticeBoardRepository;

    @Autowired
    private BoardReplyRepository boardReplyRepository;

    //임시
    @Autowired
    private All_MemberService all_MemberService;

    @Test
    public void insertMember() {

        IntStream.rangeClosed(1, 10).forEach(i -> {

            All_MemberDTO member = All_MemberDTO.builder()
                    //수정
                    .allId("member" + i)
                    .name(("1234") + i)
                    .email("email" + i + "@aaa.bbb")
                    .aPsw(("1234") + i)
                    .aPhone(123L)
                    .memberType("default")
                    .del(false)
                    .aSocial(false)
                    .build();

            String result = all_MemberService.register(member);
            log.info("Notice_id: " + result);
        });
    }

    @Test
    public void testInsertNotice() {

        String allId = "member1";  //수정

        All_Member all_member =All_Member.builder().allId(allId).build(); //수정

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Notice_Board notice_board = Notice_Board.builder()
                    .nTitle("title..." + i)
                    .nContent("content..." + i)
                    .allMember(all_member)
                    .nHits(0)
                    .build();

            Notice_Board result = noticeBoardRepository.save(notice_board);
            log.info("NoticeId: " + result.getNoticeId());
        });
    }

    @Test
    public void testSelectNotice() {
        Long noticeId =100L;

        Optional<Notice_Board> result = noticeBoardRepository.findById(noticeId);

        Notice_Board notice_board = result.orElseThrow();

        log.info(notice_board);
    }

    @Test
    public void testUpdateNotice() {
        Long noticeId =100L;

        Optional<Notice_Board> result = noticeBoardRepository.findById(noticeId);

        Notice_Board notice_board = result.orElseThrow();

        notice_board.changeNotice("update.title 100", "update...content 100");

        noticeBoardRepository.save(notice_board);
    }

    @Test
    public void testDeleteNotice() {
        Long noticeId =1L;

        noticeBoardRepository.deleteById(noticeId);
    }

    @Test
    public void testNoticePaging() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("noticeId").descending());

       Page<Notice_Board> result = noticeBoardRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<Notice_Board> todoList = result.getContent();

        todoList.forEach(notice_board -> log.info(notice_board));
    }

    @Test
    public void testSearchNotice1() {

        Pageable pageable = PageRequest.of(0, 10, Sort.by("noticeId").descending());

        noticeBoardRepository.searchNotice1(pageable);
    }

    @Test
    public void testSearchNoticeAll() {

        String[] types = {"t", "c"};

        String keyword = "1";

        LocalDate startDate = LocalDate.now();

        LocalDate endDate = LocalDate.now().plusDays(1);

        Pageable pageable = PageRequest.of(0, 10, Sort.by("noticeId").descending());

        Page<Notice_Board> result = noticeBoardRepository.searchNoticeAll(types, keyword, startDate, endDate,pageable);

        //total pages
        log.info(result.getTotalPages());
        //page size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(notice_board -> log.info(notice_board));
    }

    @Test
    public void testSearchNoticeReplyCount() {

        String[] types = {"t", "c"};

        String keyword = "ddd";

        LocalDate startDate = LocalDate.now().minusDays(7);

        LocalDate endDate = LocalDate.now().plusDays(1);

        Pageable pageable = PageRequest.of(0,10, Sort.by("noticeId").descending());

        Page<BoardListReplyCountDTO> result = noticeBoardRepository.searchWithNoticeReplyCount(types, keyword,
                                                                                        startDate, endDate,pageable);

        //total pages
        log.info(result.getTotalPages());
        //pag size
        log.info(result.getSize());
        //pageNumber
        log.info(result.getNumber());
        //prev next
        log.info(result.hasPrevious() + ": " + result.hasNext());

        result.getContent().forEach(noticeBoard ->log.info(noticeBoard));
    }

    @Test
    public void testInsertWithNoticeFile() {

        String allId = "member1";

        All_Member all_member =All_Member.builder().allId(allId).build();

        Notice_Board notice_board =Notice_Board.builder()
                .nTitle("file Test")
                .nContent("첨부파일 테스트")
                .allMember(all_member)
                .build();

        for (int i = 0; i < 3; i++) {

            notice_board.addNoticeFiles(UUID.randomUUID().toString(), "file" + i + ".jpg");
        } //end for

        noticeBoardRepository.save(notice_board);
    }

    @Test
    public void testReadWithNoticeFiles() {

        Optional<Notice_Board> result = noticeBoardRepository.findByIdWithNoticefiles(102L);

        Notice_Board notice_board = result.orElseThrow();

        log.info(notice_board);
        log.info("---------------");
        log.info(notice_board.getBoardFileSet());
    }

    @Transactional
    @Commit
    @Test
    public void testModifyNoticeFiles() {

        Optional<Notice_Board> result = noticeBoardRepository.findByIdWithNoticefiles(102L);

        Notice_Board notice_board = result.orElseThrow();

        notice_board.clearNoticeFiles();

        for (int i = 0; i < 2; i++) {

            notice_board.addNoticeFiles(UUID.randomUUID().toString(), "updatefile" + i + ".jpg");
        }
        noticeBoardRepository.save(notice_board);
    }

    @Test
    @Transactional
    @Commit
    public void testRemoveNoticeAll() {

        Long noticeId = 100L;

        boardReplyRepository.deleteByNoticeBoard_noticeId(noticeId);

        noticeBoardRepository.deleteById(noticeId);
    }

    @Test
    public void testInsertNoticeAll() {

        String allId = "member1";

        All_Member all_member = All_Member.builder().allId(allId).build();

        for (int i = 0; i < 100; i++) {
            Notice_Board notice_board = Notice_Board.builder()
                    .nTitle("Title.." + i)
                    .nContent("Content.." + i)
                    .allMember(all_member)
                    .nHits(0)
                    .build();

            for (int j = 0; j < 3; j++) {
                if(i % 5 == 0) {
                    continue;
                }
                notice_board.addNoticeFiles(UUID.randomUUID().toString(), "file" + i + ".jpg");
            }
            noticeBoardRepository.save(notice_board);
        }
    }

    @Transactional
    @Test
    public void testSearchNoticeFileReplyCount() {

        Pageable pageable = PageRequest.of(0,10, Sort.by("noticeId").descending());

        Page<NoticeBoardListAllDTO> result = noticeBoardRepository
                                .searchWithNoticeAll(null,null,null, null, pageable);

        log.info("---------------------");
        log.info(result.getTotalElements());

        result.getContent().forEach(noticeBoardListAllDTO -> log.info(noticeBoardListAllDTO));
    }

}
