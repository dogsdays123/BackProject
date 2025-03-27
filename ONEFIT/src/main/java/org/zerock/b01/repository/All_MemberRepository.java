package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.All_Member;

import java.util.List;
import java.util.Optional;

public interface All_MemberRepository extends JpaRepository<All_Member, String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from All_Member m where m.allId = :allId and m.aSocial = false")
    Optional<All_Member> getWithRoles(@Param("allId") String allId);

    @EntityGraph(attributePaths = "roleSet")
    Optional<All_Member> findByEmail(String email);

    Optional<All_Member> findByAllId(String allId);

    @Modifying
    @Transactional
    @Query("update All_Member m set m.aPsw =:aPsw, m.aPhone =:aPhone, m.memberType =:memberType where m.allId =:allId")
    void updateMember(@Param("aPsw") String aPsw, @Param("aPhone") String aPhone, @Param("memberType") String memberType, @Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from All_Member m where m.allId =:allId")
    void removeMember(@Param("allId") String allId);
    
    @Query("select am from All_Member am")
    List<All_Member> getAllMembers();
}
