package org.zerock.b01.repository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.domain.All_Member;

public interface All_MemberSearch {
    Page<All_Member> search2(Pageable pageable);
    Page<All_Member> searchMember(String[] types, String keyword, Pageable pageable);
}
