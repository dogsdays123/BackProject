package org.zerock.b01.repository.recruitRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;
import org.zerock.b01.domain.recruit.Recruit_Apply;
import org.zerock.b01.domain.recruit.Recruit_Register;

import java.util.List;
import java.util.Optional;

public interface RecruitApplyRepository extends JpaRepository<Recruit_Apply, Long> {
    List<Recruit_Apply> findAll();
    Optional<Recruit_Apply> findByRecruitRegister_RecruitId(Long recruitId);
    List<Recruit_Apply> findByBusinessMember_BusinessId(Long businessId);
}
