//package org.zerock.b01.service;
//
//import lombok.extern.log4j.Log4j2;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.test.annotation.Commit;
//import org.springframework.transaction.annotation.Transactional;
//import org.zerock.b01.domain.recruit.Recruit_Register;
//import org.zerock.b01.domain.recruit.Recruit_Register_Image;
//import org.zerock.b01.dto.PageRequestDTO;
//import org.zerock.b01.dto.PageResponseDTO;
//import org.zerock.b01.dto.recruitDTO.RecruitDTO;
//import org.zerock.b01.dto.recruitDTO.RecruitImageDTO;
//import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;
//import org.zerock.b01.repository.recruitRepository.RecruitRepository;
//import org.zerock.b01.service.recruitService.RecruitService;
//
//import java.time.LocalDateTime;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@SpringBootTest
//@Log4j2
//public class tests {
//
//    LocalDateTime date = LocalDateTime.now();
//
//    @Autowired
//    private RecruitRepository recruitRepository;
//    @Autowired
//    private RecruitService recruitService;
//
//    @Test
//    public void test(){
//
//
//        for(int i = 1 ; i <= 100 ; i++) {
//            Recruit_Register recruit_register = Recruit_Register.builder()
//                    .reTitle("Image Test")
//                    .reCompany("1")
//                    .reJobTypeFull("정규직")
//                    .reJobTypePart("파트")
//                    .reJobTypeFree("프리")
//                    .reJobTypeTrainee("연습생")
//                    .reJobTypeAlba("알바")
//                    .reIndustry("헬스")
//                    .reNumHiring(1)
//                    .reWorkDays("월~금")
//                    .reDutyDays("당직")
//                    .reWorkStartTime("12:00")
//                    .reWorkEndTime("15:00")
//                    .reTimeNegotiable("협의")
//                    .reSalaryType("월급")
//                    .reSalaryValue("100")
//                    .reSalaryCheckAgree("agree")
//                    .reSalaryCheckMeal("meal")
//                    .reSalaryCheckDuty("duty")
//                    .reSalaryCheckProb("prob")
//                    .reSalaryDetail("detail")
//                    .reGender("남")
//                    .reAgeType("age")
//                    .reMinAge("10")
//                    .reMaxAge("20")
//                    .reJobHistory("무관")
//                    .reEducation("고졸")
//                    .rePreference("필수")
//                    .reDeadline(date)
//                    .reApplyMethodOnline("online")
//                    .reApplyMethodEmail("email")
//                    .reApplyMethodMsg("msg")
//                    .reApplyMethodTel("tel")
//                    .reAdminName("hong")
//                    .reAdminEmail("hong@zerock.com")
//                    .reAdminPhone("010-1234-5678")
//                    .business_member(null)
//                    .build();
//
//            for (int j = 0; j < 3; j++) {
//                if(i%5 == 0){
//                    continue;
//                }
//                recruit_register.addImage(UUID.randomUUID().toString(), "file" + i + ".jpg");
//            }
//
//            recruitRepository.save(recruit_register);
//        }
//    }
//
//    @Transactional
//    @Test
//    public void testRead(){
//        Optional<Recruit_Register> result = recruitRepository.findById(7L);
//
//        Recruit_Register recruit_register = result.orElseThrow();
//
//        log.info(recruit_register);
//        log.info("-000000000000000");
//        log.info(recruit_register.getImageSet());
//    }
//
//    @Test
//    public void testWrite(){
//        Optional<Recruit_Register> result = recruitRepository.findByIdWithImages(7L);
//
//        Recruit_Register recruit_register = result.orElseThrow();
//
//        log.info(recruit_register);
//        log.info("-000000000000000");
//        for(Recruit_Register_Image recruitRegisterImage : recruit_register.getImageSet()){
//            log.info(recruitRegisterImage);
//        }
//    }
//
//    @Transactional
//    @Commit
//    @Test
//    public void testDelete(){
//        Optional<Recruit_Register> result = recruitRepository.findByIdWithImages(7L);
//
//        Recruit_Register recruit_register = result.orElseThrow();
//
//        recruit_register.clearImage();
//
//        for(int i = 0 ; i <2 ; i++){
//            recruit_register.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
//        }
//
//        recruitRepository.save(recruit_register);
//    }
//
////    @Transactional
////    @Test
////    public void testSearch(){
////        Pageable pageable = PageRequest.of(0, 10, Sort.by("recruitId").descending());
////
////        Page<RecruitListAllDTO> result = recruitRepository.searchAll(null, null, pageable);
////
////        log.info("---------------------------");
////        log.info(result.getTotalElements());
////
////        result.getContent().forEach(recruitListAllDTO -> log.info(recruitListAllDTO));
////    }
//
//    @Test
//    public void testRegisterWithImage(){
//        log.info(recruitService.getClass().getName());
//
//        RecruitDTO recruitDTO = RecruitDTO.builder()
//                .reTitle("Image Test")
//                .reCompany("1")
//                .reJobTypeFull("정규직")
//                .reJobTypePart("파트")
//                .reJobTypeFree("프리")
//                .reJobTypeTrainee("연습생")
//                .reJobTypeAlba("알바")
//                .reIndustry("헬스")
//                .reNumHiring(1)
//                .reWorkDays("월~금")
//                .reDutyDays("당직")
//                .reWorkStartTime("12:00")
//                .reWorkEndTime("15:00")
//                .reTimeNegotiable("협의")
//                .reSalaryType("월급")
//                .reSalaryValue("100")
//                .reSalaryCheckAgree("agree")
//                .reSalaryCheckMeal("meal")
//                .reSalaryCheckDuty("duty")
//                .reSalaryCheckProb("prob")
//                .reSalaryDetail("detail")
//                .reGender("남")
//                .reAgeType("age")
//                .reMinAge("10")
//                .reMaxAge("20")
//                .reJobHistory("무관")
//                .reEducation("고졸")
//                .rePreference("필수")
//                .reDeadline(date)
//                .reApplyMethodOnline("online")
//                .reApplyMethodEmail("email")
//                .reApplyMethodMsg("msg")
//                .reApplyMethodTel("tel")
//                .reAdminName("hong")
//                .reAdminEmail("hong@zerock.com")
//                .reAdminPhone("010-1234-5678")
//                .business_member(null)
//                .build();
//
//        recruitDTO.setFileNames(
//                Arrays.asList(
//                        UUID.randomUUID()+"_aaa.jpg",
//                        UUID.randomUUID()+"_bbb.jpg",
//                        UUID.randomUUID()+"_ccc.jpg"
//                )
//        );
//
//        Long recruitId = recruitService.register(recruitDTO);
//
//        log.info("recruitID: " + recruitId);
//    }
//
//    @Test
//    public void testReadAll(){
//        Long bno = 109L;
//
//        RecruitDTO recruitDTO = recruitService.readOne(bno);
//
//        log.info(recruitDTO);
//
//        for(String fileName : recruitDTO.getFileNames()){
//            log.info(fileName);
//        }
//    }
//
//    @Test
//    public void testModify(){
//
//        RecruitDTO recruitDTO = RecruitDTO.builder()
//                .recruitId(109L)
//                .reTitle("Update Test")
//                .reCompany("1")
//                .reJobTypeFull("정규직")
//                .reJobTypePart("파트")
//                .reJobTypeFree("프리")
//                .reJobTypeTrainee("연습생")
//                .reJobTypeAlba("알바")
//                .reIndustry("헬스")
//                .reNumHiring(1)
//                .reWorkDays("월~금")
//                .reDutyDays("당직")
//                .reWorkStartTime("12:00")
//                .reWorkEndTime("15:00")
//                .reTimeNegotiable("협의")
//                .reSalaryType("월급")
//                .reSalaryValue("100")
//                .reSalaryCheckAgree("agree")
//                .reSalaryCheckMeal("meal")
//                .reSalaryCheckDuty("duty")
//                .reSalaryCheckProb("prob")
//                .reSalaryDetail("detail")
//                .reGender("남")
//                .reAgeType("age")
//                .reMinAge("10")
//                .reMaxAge("20")
//                .reJobHistory("무관")
//                .reEducation("고졸")
//                .rePreference("필수")
//                .reDeadline(date)
//                .reApplyMethodOnline("online")
//                .reApplyMethodEmail("email")
//                .reApplyMethodMsg("msg")
//                .reApplyMethodTel("tel")
//                .reAdminName("hong")
//                .reAdminEmail("hong@zerock.com")
//                .reAdminPhone("010-1234-5678")
//                .business_member(null)
//                .build();
//
//        recruitDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));
//
//        recruitService.modify(recruitDTO);
//    }
//
//    @Test
//    public void testRemoveAll(){
//        Long bno = 7L;
//
//        recruitService.remove(bno);
//    }
//
//    @Test
//    public void testListWithAll(){
//    PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
//            .page(1)
//            .size(10)
//            .build();
//
//        PageResponseDTO<RecruitListAllDTO> responseDTO = recruitService.list(pageRequestDTO);
//
//        List<RecruitListAllDTO> dtoList = responseDTO.getDtoList();
//
//        dtoList.forEach(recruitListAllDTO -> {
//            log.info(recruitListAllDTO.getRecruitId() + "-" );
//
//            if(recruitListAllDTO.getRecruitImages() != null){
//                for(RecruitImageDTO imageDTO : recruitListAllDTO.getRecruitImages()){
//                    log.info(imageDTO);
//                }
//            }
//            log.info("-------------------------------");
//        });
//    }
//}
