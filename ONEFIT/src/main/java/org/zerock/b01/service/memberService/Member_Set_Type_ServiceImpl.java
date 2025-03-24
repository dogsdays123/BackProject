package org.zerock.b01.service.memberService;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.memberRepository.Business_MemberRepository;
import org.zerock.b01.repository.memberRepository.User_MemberRepository;

import java.util.Optional;

@Log4j2
@Service
public class Member_Set_Type_ServiceImpl implements Member_Set_Type_Service {

    private final Business_MemberRepository business_MemberRepository;
    private final User_MemberRepository user_MemberRepository;
    private final All_MemberRepository all_MemberRepository;
    ModelMapper modelMapper;

    public Member_Set_Type_ServiceImpl(ModelMapper modelMapper, All_MemberRepository all_MemberRepository, User_MemberRepository user_MemberRepository, Business_MemberRepository business_MemberRepository) {
        this.modelMapper = modelMapper;
        this.all_MemberRepository = all_MemberRepository;
        this.user_MemberRepository = user_MemberRepository;
        this.business_MemberRepository = business_MemberRepository;
    }

    @Override
    public Long UserRegister(User_MemberDTO user_MemberDTO) {

        // DTO에서 allId로 All_Member 객체를 조회
        All_Member allMember = all_MemberRepository.findByAllId(user_MemberDTO.getAllId())
                .orElseThrow(() -> new RuntimeException("All_Member not found"));
        allMember.changeMemberType("user");

        // DTO를 Entity로 변환
        User_Member userMember = modelMapper.map(user_MemberDTO, User_Member.class);

        // all_member 필드에 All_Member 객체를 설정
        userMember.setAllMember(allMember);

        // User_Member 저장
        Long userId = user_MemberRepository.save(userMember).getUserId();

        return userId;
    }

    @Override
    public Long BusinessRegister(Business_MemberDTO business_MemberDTO) {
        // DTO에서 allId로 All_Member 객체를 조회
        All_Member allMember = all_MemberRepository.findByAllId(business_MemberDTO.getAllId())
                .orElseThrow(() -> new RuntimeException("All_Member not found"));
        allMember.changeMemberType("business");

        // DTO를 Entity로 변환
        Business_Member business_member = modelMapper.map(business_MemberDTO, Business_Member.class);

        // all_member 필드에 All_Member 객체를 설정
        business_member.setAllMember(allMember);

        // User_Member 저장
        Long businessId = business_MemberRepository.save(business_member).getBusinessId();

        return businessId;
    }

    @Override
    public User_MemberDTO userRead(String allId){
        Optional<User_Member> result = user_MemberRepository.findUserMember(allId);
        User_Member user_member = result.orElse(null);  // null로 반환하도록 수정
        if (user_member == null) {
            return null;  // user_member가 null인 경우 null 반환
        }
        User_MemberDTO user_memberDTO = modelMapper.map(user_member, User_MemberDTO.class);
        user_memberDTO.setAllId(allId);
        return user_memberDTO;
    }

    @Override
    public Business_MemberDTO BusinessRead(String allId){
        Optional<Business_Member> result = business_MemberRepository.findBusinessMember(allId);
        Business_Member business_member = result.orElse(null);  // null로 반환하도록 수정

        if (business_member == null) {
            return null;  // business_member가 null인 경우 null 반환
        }
        Business_MemberDTO business_memberDTO = modelMapper.map(business_member, Business_MemberDTO.class);
        business_memberDTO.setAllId(allId);
        log.info("@#@#@#" + business_memberDTO);
        return business_memberDTO;
    }
}
