package org.zerock.b01.repository.recruitRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.recruit.Recruit_Register;

public interface RecruitRepository extends JpaRepository<Recruit_Register, Long> {

}
