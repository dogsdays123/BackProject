package org.zerock.b01.service.memberService;

import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.Business_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.memberDTO.Business_MemberDTO;
import org.zerock.b01.dto.memberDTO.User_MemberDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.repository.memberRepository.Business_MemberRepository;
import org.zerock.b01.repository.memberRepository.User_MemberRepository;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public void userModify(User_MemberDTO user_memberDTO) {
        log.info("User modify@@@@" + user_memberDTO);
        Optional<User_Member> result = user_MemberRepository.findById(user_memberDTO.getUserId());
        User_Member user_Member = result.orElseThrow();
        user_Member.modifyMember(user_memberDTO.getUNickname(), user_memberDTO.getUBirthday(),
                user_memberDTO.getUAddress(), user_memberDTO.getUAddressExtra(), user_memberDTO.getUResident());

        user_MemberRepository.save(user_Member);
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
    public void businessModify(Business_MemberDTO business_memberDTO) {
        log.info("Business modify@@@@" + business_memberDTO);
        Optional<Business_Member> result = business_MemberRepository.findById(business_memberDTO.getBusinessId());
        Business_Member business_member = result.orElseThrow();
        business_member.modifyMember(business_memberDTO.getBName(), business_memberDTO.getBExponent(),
                business_memberDTO.getBAddress(), business_memberDTO.getBAddressExtra(),
                business_memberDTO.getBPhone(), business_memberDTO.getBHomepage(),
                business_memberDTO.getBEmployees(), business_memberDTO.getBAverage(),
                business_memberDTO.getBAssets());

        business_MemberRepository.save(business_member);
    }

    @Override
    public User_MemberDTO userRead(String allId) {
        Optional<User_Member> result = user_MemberRepository.findUserMember(allId);
        User_Member user_member = result.orElse(null);  // null로 반환하도록 수정
        if (user_member == null) {
            return null;  // user_member가 null인 경우 null 반환
        }
        User_MemberDTO user_memberDTO = modelMapper.map(user_member, User_MemberDTO.class);
        user_memberDTO.setAllId(allId);
        log.info("&&&& User" + user_memberDTO);
        return user_memberDTO;
    }

    @Override
    public Business_MemberDTO BusinessRead(String allId) {
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

    @Override
    public TrainerDTO trainerReadForUser(Long userId) {
        Optional<Trainer> result = user_MemberRepository.findTrainerForUserId(userId);
        Trainer trainer = result.orElse(null);
        if (trainer == null) {
            return null;
        }

        TrainerDTO trainerDTO = modelMapper.map(trainer, TrainerDTO.class);
        return trainerDTO;
    }

    @Override
    public List<RecruitDTO> recruitReadForBusiness(Long businessId) {
        List<Recruit_Register> result = business_MemberRepository.findRecruitForBusinessId(businessId); // List로 조회
        if (result.isEmpty()) {
            return Collections.emptyList(); // 결과가 없으면 빈 리스트 반환
        }

        // List<Recruit_Register> 를 List<RecruitDTO>로 변환
        List<RecruitDTO> recruitDTOList = result.stream()
                .map(recruit -> modelMapper.map(recruit, RecruitDTO.class))
                .collect(Collectors.toList());

        return recruitDTOList;
    }

    //사실상 필요없음 = 어차피 id만 가지고 read 할건데 멍청한 짓 함 ㅋ
    @Override
    public String encodeUserForURL(User_MemberDTO user_memberDTO) {

        String link;

        StringBuilder builder = new StringBuilder();

        try {
            builder.append("userId=" + user_memberDTO.getUserId() + "&");
            builder.append("uNickname=" +
                    (user_memberDTO.getUNickname() != null ? URLEncoder.encode(user_memberDTO.getUNickname(), "UTF-8") : "") + "&");
            builder.append("UBirthday=" +
                    (user_memberDTO.getUBirthday() != null ? URLEncoder.encode(String.valueOf(user_memberDTO.getUBirthday()), "UTF-8") : "") + "&");
            builder.append("uResident=" +
                    (user_memberDTO.getUResident() != null ? URLEncoder.encode(String.valueOf(user_memberDTO.getUResident()), "UTF-8") : "") + "&");
            builder.append("uAddress=" +
                    (user_memberDTO.getUAddress() != null ? URLEncoder.encode(user_memberDTO.getUAddress(), "UTF-8") : "") + "&");
            builder.append("uAddressExtra=" +
                    (user_memberDTO.getUAddressExtra() != null ? URLEncoder.encode(user_memberDTO.getUAddressExtra(), "UTF-8") : "") + "&");
            builder.append("allId=" + user_memberDTO.getAllId() + "&");
        } catch (UnsupportedEncodingException e) {
            log.info("뭔가 잘못됨 ㅋ");
            e.printStackTrace();
        }
        link = builder.toString();
        return link;
    }
}
