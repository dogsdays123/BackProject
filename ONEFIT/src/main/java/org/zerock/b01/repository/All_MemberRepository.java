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

public interface All_MemberRepository extends JpaRepository<All_Member, Long> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from All_Member m where m.a_member_id = :mid and m.a_social = false")
    Optional<All_Member> getWithRoles(String a_member_id);

    @EntityGraph(attributePaths = "roleSet")
    Optional<All_Member> findByEmail(String email);

    @Modifying
    @Transactional
    @Query("update All_Member m set m.a_psw =:a_psw where m.a_member_id =:a_member_id")
    void updatePassword(@Param("a_psw") String password, @Param("a_member_id") String a_member_id);
}
