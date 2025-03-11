package org.zerock.b01.repository;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.zerock.b01.domain.All_Member;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class MemberRepositoryTests {
    @Autowired
    private All_MemberRepository allMemberRepository;

    @Test
    public void testInsert() {
        IntStream.rangeClosed(1,2).forEach(i ->{
            All_Member allMember = All_Member.builder()
                    .name("name"+i)
                    .email("email"+i)
                    .a_member_id("user" + (i%10))
                    .a_psw("1234")
                    .roles("default")
                    .build();

            All_Member result = allMemberRepository.save(allMember);
            log.info("All_id: " + result.getAll_id());
        });
    }

    @Test
    public void testSelect() {
        Long bno = 100L;
        Optional<All_Member> result = allMemberRepository.findById(bno);
        All_Member all_member = result.orElseThrow();
        log.info(all_member);
    }

    @Test
    public void testUpdate() {
        Long all_id = 100L;
        Optional<All_Member> result = allMemberRepository.findById(all_id);
        All_Member all_member = result.orElseThrow();
        all_member.change("update...name 100", "update email 100");
        allMemberRepository.save(all_member);
    }

    @Test
    public void testDelete() {
//        All_member All_member = All_member.builder()
//                .title("title..."+100)
//                .content("content..."+100)
//                .writer("user" + (100%10))
//                .build();
//        All_member result = allMemberRepository.save(All_member);
//        log.info("BNO: " + result.getBno());
        Long all_id = 1L;
        allMemberRepository.deleteById(all_id);
    }

    @Test
    public void testPaging() {
        //1 page order by bno desc
        Pageable pageable = PageRequest.of(0, 10, Sort.by("all_id").descending());
        Page<All_Member> result = allMemberRepository.findAll(pageable);

        log.info("total count: " + result.getTotalElements());
        log.info("total pages: " + result.getTotalPages());
        log.info("page number: " + result.getNumber());
        log.info("page size: " + result.getSize());

        List<All_Member> todoList = result.getContent();
        todoList.forEach(All_member -> log.info(All_member));
    }

    @Test
    public void testSearch1() {
        //2 page order by bno desc
        Pageable pageable = PageRequest.of(1, 10, Sort.by("bno").descending());
        allMemberRepository.search2(pageable);
    }

    @Test
    public void testSearchAll() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0,10, Sort.by("all_id").descending());
        //Page<All_Member> result = allMemberRepository.searchAll(types, keyword, pageable);
    }

    @Test
    public void testSearchAll2() {
        String[] types = {"t", "c", "w"};
        String keyword = "1";
        Pageable pageable = PageRequest.of(0,10, Sort.by("all_id").descending());
        //Page<All_Member> result = allMemberRepository.searchAll(types, keyword, pageable);

        //total pages
        //log.info(result.getTotalPages());

        //page size
        //log.info(result.getSize());

        //pageNumber
        //og.info(result.getNumber());

        //prev next
        //log.info(result.hasPrevious() +": " + result.hasNext());

        //result.getContent().forEach(all_member -> log.info(all_member));
    }
}
