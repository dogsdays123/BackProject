//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.zerock.b01.domain.All_Member;
//import org.zerock.b01.domain.transaction.Category;
//import org.zerock.b01.domain.transaction.Equipment;
//import org.zerock.b01.domain.transaction.Product;
//import org.zerock.b01.dto.All_MemberDTO;
//import org.zerock.b01.repository.All_MemberRepository;
//import org.zerock.b01.repository.transactionRepository.CategoryRepository;
//import org.zerock.b01.repository.transactionRepository.EquipmentRepository;
//import org.zerock.b01.repository.transactionRepository.ProductRepository;
//
//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.stream.IntStream;
//
//@SpringBootTest
//@Log4j2
//public class TransaRepositoryTest {
//    @Autowired
//    private ProductRepository productRepository;
//    @Autowired
//    private CategoryRepository categoryRepository;
//    @Autowired
//    private EquipmentRepository equipmentRepository;
//    @Autowired
//    private All_MemberRepository all_MemberRepository;
//
//    @Test
//    public void insertMember() {
//
//        All_Member member = All_Member.builder()
//                .allId("member" + 1)
//                .name(("1234") + 1)
//                .email("email" + 1 + "@aaa.bbb")
//                .a_psw(("1234") + 1)
//                .a_phone(123)
//                .member_type("default") // << 일반 1. 개인(user), 2. 기업(business)
//                .del(false)
//                .a_social(false)
//                .build();
//
//        all_MemberRepository.save(member);
//
////        IntStream.rangeClosed(1, 10).forEach(i -> {
////
////            All_MemberDTO member = All_MemberDTO.builder()
////                    //수정
////                    .all_id("member" + i)
////                    .name(("1234") + i)
////                    .email("email" + i + "@aaa.bbb")
////                    .a_psw(("1234") + i)
////                    .a_phone(123)
////                    .member_type("default") // << 일반 1. 개인(user), 2. 기업(business)
////                    .del(false)
////                    .a_social(false)
////                    .build();
////
////            String result = all_MemberService.register(member);
////            log.info("Notice_id: " + result);
////        });
//    }
//
//    @Test
//    public void testRegisterEquipment() {
//        log.info("Register equipment");
//
//        All_Member member = All_Member.builder()
//                //수정
//                .all_id("member" + 1)
//                .name(("1234") + 1)
//                .email("email" + 1 + "@aaa.bbb")
//                .a_psw(("1234") + 1)
//                .a_phone(123)
//                .member_type("default") // << 일반 1. 개인(user), 2. 기업(business)
//                .del(false)
//                .a_social(false)
//                .build();
//
//        all_MemberRepository.save(member);
//
//        Category category = Category.builder()
//                .cCategory("수영")
//                .cRoles(1)
//                .build();
//
//        categoryRepository.save(category);
//
//        Product product = Product.builder()
//                .pAddr("서울 강남구")
//                .pRoles(1) // 1: 기구, 2: 시설
//                .pStatus("판매중")
//                .pPrice(new BigDecimal("500000"))
//                .pContent("거의 새것 같은 운동 기구입니다.")
//                .pTitle("런닝머신 팝니다")
//                .pChatUrl("https://open.kakao.com/o/chat")
//                .category(category) // 외래키
//                .allMember(member) // 외래키
//                .build();
//
//        productRepository.save(product);
//
//        Equipment equipment = Equipment.builder()
//                .product(product) // 저장된 Product 객체를 참조
//                .eName("런닝머신")
//                .eBrand("삼성헬스")
//                .eStatus("상")
//                .ePurPrice("1200000")
//                .eUseStart(LocalDate.of(2022, 1, 1))
//                .eUseEnd(LocalDate.of(2024, 3, 1))
//                .eAs("가능")
//                .build();
//
//        equipmentRepository.save(equipment);
//
//
//    }
