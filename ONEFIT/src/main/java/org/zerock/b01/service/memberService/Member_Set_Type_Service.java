package org.zerock.b01.service.memberService;

import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;

public interface Member_Set_Type_Service {
    Long UserRegister(User_MemberDTO user_MemberDTO);
    Long BusinessRegister(Business_MemberDTO business_memberDTO);
    User_MemberDTO userRead(String allId);
    Business_MemberDTO BusinessRead(String allId);
}
