package org.zerock.b01.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.repository.search.All_MemberSearch;

public interface All_MemberRepository extends JpaRepository<All_Member, Long>, All_MemberSearch {
    @Query(value = "select now()", nativeQuery = true)
    String now();
}
