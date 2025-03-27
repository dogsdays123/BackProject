package org.zerock.b01.service.recruitService;

import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface RecruitService {

    Long register(RecruitDTO recruitDTO);

    RecruitDTO readOne(Long recruitId);

    RecruitListAllDTO readOneWithImage(Long recruitId);

    Business_MemberDTO readBusinessMember(Long recruitId);

    void modify(RecruitDTO recruitDTO);

    void remove(Long recruitId);

    PageResponseDTO<RecruitDTO> list1(PageRequestDTO pageRequestDTO);

    PageResponseDTO<RecruitListAllDTO> list(PageRequestDTO pageRequestDTO);

    PageResponseDTO<RecruitListAllDTO> listWithAll(PageRequestDTO pageRequestDTO);

    default Recruit_Register dtoToEntity(RecruitDTO recruitDTO) {

        Recruit_Register recruit_register = Recruit_Register.builder()
                .recruitId(recruitDTO.getRecruitId())
                .reMainAddress(recruitDTO.getReMainAddress())
                .reDetailAddress(recruitDTO.getReDetailAddress())
                .reTitle(recruitDTO.getReTitle())
                .reCompany(recruitDTO.getReCompany())
                .reJobTypeFull(recruitDTO.getReJobTypeFull())
                .reJobTypePart(recruitDTO.getReJobTypePart())
                .reJobTypeFree(recruitDTO.getReJobTypeFree())
                .reJobTypeTrainee(recruitDTO.getReJobTypeTrainee())
                .reJobTypeAlba(recruitDTO.getReJobTypeAlba())
                .reIndustry(recruitDTO.getReIndustry())
                .reNumHiring(recruitDTO.getReNumHiring())
                .reWorkDays(recruitDTO.getReWorkDays())
                .reDutyDays(recruitDTO.getReDutyDays())
                .reWorkStartTime(recruitDTO.getReWorkStartTime())
                .reWorkEndTime(recruitDTO.getReWorkEndTime())
                .reTimeNegotiable(recruitDTO.getReTimeNegotiable())
                .reSalaryType(recruitDTO.getReSalaryType())
                .reSalaryValue(recruitDTO.getReSalaryValue())
                .reSalaryCheckAgree(recruitDTO.getReSalaryCheckAgree())
                .reSalaryCheckMeal(recruitDTO.getReSalaryCheckMeal())
                .reSalaryCheckDuty(recruitDTO.getReSalaryCheckDuty())
                .reSalaryCheckProb(recruitDTO.getReSalaryCheckProb())
                .reSalaryDetail(recruitDTO.getReSalaryDetail())
                .reGender(recruitDTO.getReGender())
                .reAgeType(recruitDTO.getReAgeType())
                .reMinAge(recruitDTO.getReMinAge())
                .reMaxAge(recruitDTO.getReMaxAge())
                .reJobHistory(recruitDTO.getReJobHistory())
                .reEducation(recruitDTO.getReEducation())
                .rePreference(recruitDTO.getRePreference())
                .reDeadline(recruitDTO.getReDeadline())
                .reApplyMethodEmail(recruitDTO.getReApplyMethodEmail())
                .reApplyMethodOnline(recruitDTO.getReApplyMethodOnline())
                .reApplyMethodMsg(recruitDTO.getReApplyMethodMsg())
                .reApplyMethodTel(recruitDTO.getReApplyMethodTel())
                .reAdminName(recruitDTO.getReAdminName())
                .reAdminEmail(recruitDTO.getReAdminEmail())
                .reAdminPhone(recruitDTO.getReAdminPhone())
                .business_member(recruitDTO.getBusiness_member()).build();

        if(recruitDTO.getFileNames() != null){
            recruitDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_",2);
                recruit_register.addImage(arr[0], arr[1]);
            });
        }
        return recruit_register;
    }

    default RecruitDTO entityToDTO(Recruit_Register recruit_register) {

        RecruitDTO recruitDTO = RecruitDTO.builder()
                .recruitId(recruit_register.getRecruitId())
                .regDate(recruit_register.getRegDate())
                .reMainAddress(recruit_register.getReMainAddress())
                .reDetailAddress(recruit_register.getReDetailAddress())
                .reTitle(recruit_register.getReTitle())
                .reCompany(recruit_register.getReCompany())
                .reJobTypeFull(recruit_register.getReJobTypeFull())
                .reJobTypePart(recruit_register.getReJobTypePart())
                .reJobTypeFree(recruit_register.getReJobTypeFree())
                .reJobTypeTrainee(recruit_register.getReJobTypeTrainee())
                .reJobTypeAlba(recruit_register.getReJobTypeAlba())
                .reIndustry(recruit_register.getReIndustry())
                .reNumHiring(recruit_register.getReNumHiring())
                .reWorkDays(recruit_register.getReWorkDays())
                .reDutyDays(recruit_register.getReDutyDays())
                .reWorkStartTime(recruit_register.getReWorkStartTime())
                .reWorkEndTime(recruit_register.getReWorkEndTime())
                .reTimeNegotiable(recruit_register.getReTimeNegotiable())
                .reSalaryType(recruit_register.getReSalaryType())
                .reSalaryValue(recruit_register.getReSalaryValue())
                .reSalaryCheckAgree(recruit_register.getReSalaryCheckAgree())
                .reSalaryCheckMeal(recruit_register.getReSalaryCheckMeal())
                .reSalaryCheckDuty(recruit_register.getReSalaryCheckDuty())
                .reSalaryCheckProb(recruit_register.getReSalaryCheckProb())
                .reSalaryDetail(recruit_register.getReSalaryDetail())
                .reGender(recruit_register.getReGender())
                .reAgeType(recruit_register.getReAgeType())
                .reMinAge(recruit_register.getReMinAge())
                .reMaxAge(recruit_register.getReMaxAge())
                .reJobHistory(recruit_register.getReJobHistory())
                .reEducation(recruit_register.getReEducation())
                .rePreference(recruit_register.getRePreference())
                .reDeadline(recruit_register.getReDeadline())
                .reApplyMethodEmail(recruit_register.getReApplyMethodEmail())
                .reApplyMethodOnline(recruit_register.getReApplyMethodOnline())
                .reApplyMethodMsg(recruit_register.getReApplyMethodMsg())
                .reApplyMethodTel(recruit_register.getReApplyMethodTel())
                .reAdminName(recruit_register.getReAdminName())
                .reAdminEmail(recruit_register.getReAdminEmail())
                .reAdminPhone(recruit_register.getReAdminPhone())
                .business_member(recruit_register.getBusiness_member()).build();

        List<String> fileNames = recruit_register.getImageSet().stream().sorted().map(recruitRegisterImage ->
                recruitRegisterImage.getRe_img_id()+"_"+recruitRegisterImage.getRe_img_title()).collect(Collectors.toList());

        recruitDTO.setFileNames(fileNames);
        return recruitDTO;
    }
}
