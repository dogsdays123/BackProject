package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.repository.search.All_MemberSearch;

import java.util.Optional;

public interface All_MemberRepository extends JpaRepository<All_Member, String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from All_Member m where m.allId = :allId and m.aSocial = false")
    Optional<All_Member> getWithRoles(String allId);

    @EntityGraph(attributePaths = "roleSet")
    Optional<All_Member> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update All_Member m set m.aPsw =:a_psw where m.allId =:aMemberId")
    void updatePassword(@Param("aPsw") String password, @Param("allId") String allId);
}
