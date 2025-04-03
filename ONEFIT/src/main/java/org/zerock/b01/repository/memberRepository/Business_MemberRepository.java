package org.zerock.b01.repository.memberRepository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.recruit.Recruit_Register;

import java.util.List;
import java.util.Optional;

// 채용 담당 테스트를 위해 임시 생성했습니다.
public interface Business_MemberRepository extends JpaRepository<Business_Member, Long> {
    @Query("select b from Business_Member b where b.allMember.allId = :allId")
    Optional<Business_Member> findBusinessMember(@Param("allId") String allId);

    @Query("select r from Recruit_Register r where r.business_member.businessId = :businessId")
    List<Recruit_Register> findRecruitForBusinessId(@Param("businessId") Long businessId);

    @Modifying
    @Transactional
    @Query("delete from Business_Member b where b.allMember.allId =:allId")
    void removeBusinessMember(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Recruit_Register r where r.business_member.allMember.allId =:allId")
    void removeRecruit(@Param("allId") String allId);
}
