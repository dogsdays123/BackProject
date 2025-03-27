package org.zerock.b01.repository.search.recruit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.dto.recruitDTO.RecruitListAllDTO;

public interface RecruitSearch {

    Page<Recruit_Register> search1(Pageable pageable);

    Page<Recruit_Register> searchAll1(String[] types, String keyword, String gender, String age, String jobTypeFull,
                                     String jobTypeFree, String jobTypePart, String jobTypeAlba, String jobTypeTrainee,
                                     String workDays, String dutyDays, String startTime, String endTime, String timeNegotiable, String industry,
                                     String regDateFilter, String addressDoFilter, String addressCityFilter, Pageable pageable);

    Page<RecruitListAllDTO> searchAll(String[] types, String keyword,    String gender, String age, String jobTypeFull,
                                      String jobTypeFree, String jobTypePart, String jobTypeAlba, String jobTypeTrainee,
                                      String workDays, String dutyDays, String startTime, String endTime, String timeNegotiable, String industry,
                                      String regDateFilter,  Pageable pageable);
//    String gender, String age, String jobTypeFull,
//    String jobTypeFree, String jobTypePart, String jobTypeAlba, String jobTypeTrainee,
//    String workDays, String dutyDays, String startTime, String endTime, String timeNegotiable, String industry,
//    String regDateFilter,

//    Page<Recruit_Register> searchDetailFilters(String[] types, String keyword, Pageable pageable);


}
