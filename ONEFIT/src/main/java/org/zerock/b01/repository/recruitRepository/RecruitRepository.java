package org.zerock.b01.repository.recruitRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.repository.search.recruit.RecruitSearch;

import java.util.Optional;

public interface RecruitRepository extends JpaRepository<Recruit_Register, Long>, RecruitSearch {

    @Query(value="select  now()", nativeQuery = true)
    String getTime();

    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select b from Recruit_Register b where b.recruitId =:recruitId")
    Optional<Recruit_Register> findByIdWithImages(Long recruitId);
}
