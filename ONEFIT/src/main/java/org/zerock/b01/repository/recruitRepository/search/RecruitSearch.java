package org.zerock.b01.repository.recruitRepository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.recruit.Recruit_Register;

public interface RecruitSearch {

    Page<Recruit_Register> search1(Pageable pageable);

    Page<Recruit_Register> searchAll(String[] types, String keyword, Pageable pageable);
}
