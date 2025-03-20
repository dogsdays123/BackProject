package org.zerock.b01.repository.memberRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.member.User_Member;

import java.util.Optional;

public interface User_MemberRepository extends JpaRepository<User_Member, Long> {
    @Query("select u from User_Member u where u.allMember.allId = :allId")
    Optional<User_Member> findUserMember(@Param("allId") String allId);
}
