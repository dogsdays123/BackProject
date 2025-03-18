package org.zerock.b01.service.recruitService;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.repository.recruitRepository.RecruitRepository;


@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class RecruitServiceImpl implements RecruitService {

    private final ModelMapper modelMapper;
    private final RecruitRepository recruitRepository;

    // 초기화 시에 매핑 설정을 추가
//    @PostConstruct
//    public void init() {
//        modelMapper.addMappings(new PropertyMap<RecruitDTO, Recruit_Register>() {
//            @Override
//            protected void configure() {
//                map(source.getRe_admin_phone(), destination.getRe_admin_phone());  // 전화번호 매핑
//                map(source.getRe_max_age(), destination.getRe_max_age());
//                map(source.getRe_min_age(), destination.getRe_min_age());
//                map(source.getRe_salary_value(), destination.getRe_salary_value());
//            }
//        });
//    }
    @Override
    public Long register(RecruitDTO recruitDTO) {

        Recruit_Register recruit_register = modelMapper.map(recruitDTO, Recruit_Register.class);

        Long recruitId = recruitRepository.save(recruit_register).getRecruitId();

        return recruitId;
    }

}
