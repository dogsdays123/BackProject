package org.zerock.b01.service.memberService;

import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;

import java.util.List;

public interface Member_Set_Type_Service {
    Long UserRegister(User_MemberDTO user_MemberDTO);
    Long BusinessRegister(Business_MemberDTO business_memberDTO);
    User_MemberDTO userRead(String allId);
    Business_MemberDTO BusinessRead(String allId);
    void userModify(User_MemberDTO user_MemberDTO);
    void businessModify(Business_MemberDTO business_memberDTO);
    TrainerDTO trainerReadForUser(Long userId);
    List<RecruitDTO> recruitReadForBusiness(Long businessId);

    String encodeUserForURL(User_MemberDTO user_memberDTO);
}
