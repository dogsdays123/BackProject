package org.zerock.b01.repository.search.recruit;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.recruit.Recruit_Register;

public interface RecruitSearch {

    Page<Recruit_Register> search1(Pageable pageable);

    Page<Recruit_Register> searchAll(String[] types, String keyword, String gender, String age, Pageable pageable);

//    Page<Recruit_Register> searchDetailFilters(String[] types, String keyword, Pageable pageable);
}
