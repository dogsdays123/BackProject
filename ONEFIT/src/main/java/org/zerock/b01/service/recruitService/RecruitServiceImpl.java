package org.zerock.b01.service.recruitService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.boot.web.client.RestClientCustomizer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.domain.recruit.Recruit_Register_Image;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitApplyDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.recruitDTO.RecruitImageDTO;
import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;
import org.zerock.b01.repository.recruitRepository.RecruitRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class RecruitServiceImpl implements RecruitService {

    private final ModelMapper modelMapper;
    private final RecruitRepository recruitRepository;
    private final RestClientCustomizer restClientCustomizer;

    @Override
    public Long register(RecruitDTO recruitDTO) {

//        Recruit_Register recruit_register = modelMapper.map(recruitDTO, Recruit_Register.class);
        Recruit_Register recruit_register = dtoToEntity(recruitDTO);

        Long recruitId = recruitRepository.save(recruit_register).getRecruitId();

        return recruitId;
    }

    @Override
    public Long apply(RecruitApplyDTO recruitApplyDTO) {
        return null;
    }
    @Override
    public Business_MemberDTO readBusinessMember(Long recruitId) {
        // recruitId로 Recruit_Register 조회
        Optional<Recruit_Register> result = recruitRepository.findByBusinessId(recruitId);

        // Recruit_Register가 없으면 예외 발생
        Recruit_Register recruit_register = result.orElseThrow(() -> new RuntimeException("Recruit_Register not found"));

        // Recruit_Register에서 Business_Member 추출
        Business_Member business_member = recruit_register.getBusiness_member();

        if (business_member == null) {
            throw new RuntimeException("Business Member not found");
        }

        // Business_MemberDTO로 변환
        Business_MemberDTO business_memberDTO = modelMapper.map(business_member, Business_MemberDTO.class);

        return business_memberDTO;
    }

    @Override
    public RecruitDTO readOne(Long recruitId) {
        Optional<Recruit_Register> result = recruitRepository.findByIdWithImages(recruitId);

        Recruit_Register recruit_register = result.orElseThrow();

        RecruitDTO recruitDTO = entityToDTO(recruit_register);

        return recruitDTO;
    }

    @Override
    public RecruitListAllDTO readOneWithImage(Long recruitId) {
        return null;

    }

    @Override
    public void modify(RecruitDTO recruitDTO) {
        if (recruitDTO.getRecruitId() == null) {
            throw new IllegalArgumentException("Recruit ID cannot be null");
        }
        Optional<Recruit_Register> result = recruitRepository.findById(recruitDTO.getRecruitId());

        Recruit_Register recruit_register = result.orElseThrow();

        recruit_register.change(recruitDTO.getReMainAddress(), recruitDTO.getReDetailAddress(), recruitDTO.getReTitle(), recruitDTO.getReCompany(), recruitDTO.getReJobTypeFull(), recruitDTO.getReJobTypePart()
                ,recruitDTO.getReJobTypeFree(), recruitDTO.getReJobTypeTrainee(), recruitDTO.getReJobTypeAlba()
                ,recruitDTO.getReIndustry(), recruitDTO.getReNumHiring(), recruitDTO.getReWorkDays(), recruitDTO.getReDutyDays()
        ,recruitDTO.getReWorkStartTime(), recruitDTO.getReWorkEndTime(), recruitDTO.getReTimeNegotiable(), recruitDTO.getReSalaryType()
        ,recruitDTO.getReSalaryValue(), recruitDTO.getReSalaryCheckAgree(), recruitDTO.getReSalaryCheckMeal(), recruitDTO.getReSalaryCheckDuty()
        ,recruitDTO.getReSalaryCheckProb(), recruitDTO.getReSalaryDetail(), recruitDTO.getReGender(), recruitDTO.getReAgeType()
        ,recruitDTO.getReMinAge(), recruitDTO.getReMaxAge(), recruitDTO.getReJobHistory(), recruitDTO.getReEducation(),recruitDTO.getRePreference()
        ,recruitDTO.getReDeadline(), recruitDTO.getReApplyMethodOnline(), recruitDTO.getReApplyMethodEmail(), recruitDTO.getReApplyMethodMsg(),
                recruitDTO.getReApplyMethodTel(), recruitDTO.getReAdminName(), recruitDTO.getReAdminEmail(), recruitDTO.getReAdminPhone());

        recruit_register.clearImage();

        if(recruitDTO.getFileNames() != null){
            for(String fileName : recruitDTO.getFileNames()){
                String[] arr = fileName.split("_",2);
                recruit_register.addImage(arr[0], arr[1]);
            }
        }
        recruitRepository.save(recruit_register);
    }

    @Override
    public void remove(Long recruitId) {
        recruitRepository.deleteById(recruitId);
    }

    @Override
    public PageResponseDTO<RecruitDTO> list1(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        String gender = pageRequestDTO.getGender();
        String age = pageRequestDTO.getAge();
        String jobTypeFull = pageRequestDTO.getJobTypeFull();
        String jobTypePart = pageRequestDTO.getJobTypePart();
        String jobTypeFree = pageRequestDTO.getJobTypeFree();
        String jobTypeTrainee = pageRequestDTO.getJobTypeTrainee();
        String jobTypeAlba = pageRequestDTO.getJobTypeAlba();
        String workDays = pageRequestDTO.getWorkDays();
        String dutyDays = pageRequestDTO.getDutyDays();
        String startTime = pageRequestDTO.getStartTime();
        String endTime = pageRequestDTO.getEndTime();
        String timeNegotiable = pageRequestDTO.getTimeNegotiable();
        String industry = pageRequestDTO.getIndustry();
        String regDateFilter = pageRequestDTO.getRegDateFilter();
        String addressDoFilter = pageRequestDTO.getAddressDoFilter();
        String addressCityFilter = pageRequestDTO.getAddressCityFilter();

        Pageable pageable = pageRequestDTO.getPageable("recruitId");

        Page<Recruit_Register> result = recruitRepository.searchAll1(types, keyword, gender, age,
                jobTypeFull, jobTypePart, jobTypeFree, jobTypeTrainee, jobTypeAlba, workDays, dutyDays, startTime, endTime, timeNegotiable, industry, regDateFilter,
                addressDoFilter,addressCityFilter, pageable);


        List<RecruitDTO> dtoList = result.getContent().stream()
                .map(recruitRegister -> {
                    // Recruit_Register를 RecruitDTO로 변환
                    RecruitDTO recruitDTO = modelMapper.map(recruitRegister, RecruitDTO.class);

                    // Recruit_Register에서 imageSet을 순회하며 re_img_title을 fileNames에 저장
                    List<RecruitImageDTO >titles = recruitRegister.getImageSet().stream().sorted()
                            .map(Recruit_Register_Image->RecruitImageDTO.builder()
                                    .re_img_id(Recruit_Register_Image.getRe_img_id())
                                    .re_img_title(Recruit_Register_Image.getRe_img_title())
                                    .re_img_ord(Recruit_Register_Image.getRe_img_ord())
                                    .build()).collect(Collectors.toList());  // re_img_title만 추출

                    List<String> fileNames = recruitRegister.getImageSet().stream().
                            sorted().map(recruitRegisterImage ->
                                    recruitRegisterImage.getRe_img_id()+"_"+recruitRegisterImage.getRe_img_title()
                            ).collect(Collectors.toList());
                    recruitDTO.setFileNames(fileNames);
                    // 추출한 titles를 recruitDTO의 fileNames에 설정
                    recruitDTO.setRecruitImages(titles);

                    return recruitDTO;
                })
                .collect(Collectors.toList());

        return PageResponseDTO.<RecruitDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<RecruitListAllDTO> list(PageRequestDTO pageRequestDTO) {

        String[] types = pageRequestDTO.getTypes();
        String keyword = pageRequestDTO.getKeyword();
        String gender = pageRequestDTO.getGender();
        String age = pageRequestDTO.getAge();
        String jobTypeFull = pageRequestDTO.getJobTypeFull();
        String jobTypePart = pageRequestDTO.getJobTypePart();
        String jobTypeFree = pageRequestDTO.getJobTypeFree();
        String jobTypeTrainee = pageRequestDTO.getJobTypeTrainee();
        String jobTypeAlba = pageRequestDTO.getJobTypeAlba();
        String workDays = pageRequestDTO.getWorkDays();
        String dutyDays = pageRequestDTO.getDutyDays();
        String startTime = pageRequestDTO.getStartTime();
        String endTime = pageRequestDTO.getEndTime();
        String timeNegotiable = pageRequestDTO.getTimeNegotiable();
        String industry = pageRequestDTO.getIndustry();
        String regDateFilter = pageRequestDTO.getRegDateFilter();

        Pageable pageable = pageRequestDTO.getPageable("recruitId");

//        gender, age, jobTypeFull, jobTypePart, jobTypeFree, jobTypeTrainee, jobTypeAlba, workDays, dutyDays, startTime, endTime, timeNegotiable, industry, regDateFilter,
        Page<RecruitListAllDTO> result = recruitRepository.searchAll(types, keyword,
                gender, age, jobTypeFull, jobTypePart, jobTypeFree, jobTypeTrainee, jobTypeAlba,
                workDays, dutyDays, startTime, endTime, timeNegotiable, industry, regDateFilter, pageable);


//        List<RecruitDTO> dtoList = result.getContent().stream()
//                .map(recruitRegister -> modelMapper.map(recruitRegister, RecruitDTO.class)).
//                collect(Collectors.toList());
        List<RecruitListAllDTO> dtoList = result.getContent().stream()
                .map(recruitRegister -> modelMapper.map(recruitRegister, RecruitListAllDTO.class))
                .collect(Collectors.toList());

        return PageResponseDTO.<RecruitListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(dtoList)
                .total((int)result.getTotalElements())
                .build();
    }

    @Override
    public PageResponseDTO<RecruitListAllDTO> listWithAll(PageRequestDTO pageRequestDTO) {
        return null;
    }





}