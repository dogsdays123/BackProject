package org.zerock.b01.repository.recruitRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.repository.search.recruit.RecruitSearch;

public interface RecruitRepository extends JpaRepository<Recruit_Register, Long>, RecruitSearch {

    @Query(value="select  now()", nativeQuery = true)
    String getTime();
}
