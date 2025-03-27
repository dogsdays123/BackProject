package org.zerock.b01.service;

import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;

import java.util.List;

public interface All_MemberService {
    String register(All_MemberDTO all_MemberDTO);
    All_MemberDTO readOne(String allId);
    All_MemberDTO readOneForEmail(String email);
    void modify(All_MemberDTO all_MemberDTO);
    void remove(String allId);
    List<All_MemberDTO> readAllMember();

    static class MidExistException extends Exception {

    }

    void join(All_MemberDTO all_memberDTO) throws MidExistException;
}
