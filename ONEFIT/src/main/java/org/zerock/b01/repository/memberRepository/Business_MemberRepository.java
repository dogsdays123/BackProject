package org.zerock.b01.repository.memberRepository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.member.User_Member;

import java.util.Optional;

// 채용 담당 테스트를 위해 임시 생성했습니다.
public interface Business_MemberRepository extends JpaRepository<Business_Member, Long> {
    @Query("select b from Business_Member b where b.allMember.allId = :allId")
    Optional<Business_Member> findBusinessMember(@Param("allId") String allId);
}
