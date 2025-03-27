package org.zerock.b01.service;//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.Category;
import org.zerock.b01.domain.transaction.Equipment;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.transactionRepository.CategoryRepository;
import org.zerock.b01.repository.transactionRepository.EquipmentRepository;
import org.zerock.b01.repository.transactionRepository.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

@SpringBootTest
@Log4j2
public class TransaRepositoryTest {


    @Test
    public void test() {
        String address = "부산광역시 해운대구 중동 456";
        String[] result = extractRegions(address);

        if (result != null) {
            System.out.println("광역자치단체: " + result[0]);
            System.out.println("기초자치단체: " + result[1]);
        } else {
            System.out.println("주소를 파싱할 수 없습니다.");
        }
    }

    public static String[] extractRegions(String address) {
        // 정규식 패턴: 광역자치단체(시/도) + 기초자치단체(시/군/구)
        Pattern pattern = Pattern.compile("^(서울|부산|대구|인천|광주|대전|울산|세종|강원|경기|경상남도|경상북도|전라남도|전라북도|충청남도|충청북도|제주)(?:특별시|광역시|도)?\\s+(.*?시|.*?군|.*?구)");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find()) {
            String province = matcher.group(1); // 광역자치단체
            String city = matcher.group(2);     // 기초자치단체
            return new String[]{province, city};
        }

        return null;
    }


//    public static String[] extractRegions(String address) {
//        // 정규식 패턴: 광역자치단체(시/도) + 기초자치단체(시/군/구)
//        Pattern pattern = Pattern.compile("^(.*?도|.*?시)\\s+(.*?시|.*?군|.*?구)");
//        Matcher matcher = pattern.matcher(address);
//
//        if (matcher.find()) {
//            String province = matcher.group(1); // 광역자치단체
//            String city = matcher.group(2);     // 기초자치단체
//            return new String[]{province, city};
//        }
//
//        return null;
//    }


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

    /// /        IntStream.rangeClosed(1, 10).forEach(i -> {
    /// /
    /// /            All_MemberDTO member = All_MemberDTO.builder()
    /// /                    //수정
    /// /                    .all_id("member" + i)
    /// /                    .name(("1234") + i)
    /// /                    .email("email" + i + "@aaa.bbb")
    /// /                    .a_psw(("1234") + i)
    /// /                    .a_phone(123)
    /// /                    .member_type("default") // << 일반 1. 개인(user), 2. 기업(business)
    /// /                    .del(false)
    /// /                    .a_social(false)
    /// /                    .build();
    /// /
    /// /            String result = all_MemberService.register(member);
    /// /            log.info("Notice_id: " + result);
    /// /        });
//    }
//
//    @Test
//    public void testRegisterEquipment() {
//        log.info("Register equipment");
//
//        All_Member member = All_Member.builder()
//                //수정
//                .allId("member" + 1)
//                .name(("1234") + 1)
//                .email("email" + 1 + "@aaa.bbb")
//                .aPsw(("1234") + 1)
//                .aPhone(123L)
//                .memberType("default") // << 일반 1. 개인(user), 2. 기업(business)
//                .del(false)
//                .aSocial(false)
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
//    }
////
////
}
