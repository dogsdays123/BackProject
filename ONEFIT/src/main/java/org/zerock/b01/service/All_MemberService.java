package org.zerock.b01.service;

import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

public interface All_MemberService {
    String register(All_MemberDTO all_MemberDTO);
    All_MemberDTO readOne(String all_id);
    void modify(All_MemberDTO all_MemberDTO);
    void remove(String all_id);

    static class MidExistException extends Exception {

    }

    void join(All_MemberDTO all_memberDTO) throws MidExistException;
}
