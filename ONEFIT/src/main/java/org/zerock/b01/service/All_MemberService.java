package org.zerock.b01.service;

import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface All_MemberService {
    Long register(All_MemberDTO all_MemberDTO);
    All_MemberDTO readOne(Long all_id);
    void modify(All_MemberDTO all_MemberDTO);
    void remove(Long all_id);
    PageResponseDTO<All_MemberDTO> list(PageRequestDTO pageRequestDTO);
}
