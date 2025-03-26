package org.zerock.b01.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.MemberRole;
import org.zerock.b01.repository.All_MemberRepository;
import org.zerock.b01.security.dto.MemberSecurityDTO;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final All_MemberRepository all_memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException{
        log.info("userRequest.......");
        log.info(userRequest);

        log.info("oauth2 user..........................");
        //클라이언트 이름 구하기
        ClientRegistration clientRegistration = userRequest.getClientRegistration();
        log.info(clientRegistration.getClientName());
        String clientName = clientRegistration.getClientName();

        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> paramMap = oAuth2User.getAttributes();
        log.info("@@param@@ : " + paramMap);

        String email = null;
        String memberName = "";

        //여기서 정보 가져오기
        switch (clientName){
            case "kakao":
                email = getKakao(paramMap)[0];
                memberName = getKakao(paramMap)[1];
                break;
            case "google":
                email = getGoogle(paramMap)[0];
                memberName = getGoogle(paramMap)[1];
                break;
        }

        return generateDTO(email, paramMap, memberName);
    }

    //회원추가도 DTO 객체랑 맞춰야됨 싀....
    private MemberSecurityDTO generateDTO(String email, Map<String, Object> params, String memberName){
        Optional<All_Member> result = all_memberRepository.findByEmail(email);

        //데이터베이스에 해당 이메일 사용자가 없다면
        if(result.isEmpty()){
            //회원 추가 -- all_id는 이메일 주소? / 패스워드는 1111
            All_Member all_member = All_Member.builder()
                    .allId(email)
                    .name(memberName) //나중에 카카오에게 받은 이름
                    .aPsw(passwordEncoder.encode("1111"))
                    .email(email)
                    .aPhone("010")//나중에 카카오에게 받은 폰번호로
                    .memberType("default") //나중에 회원전환 시 변경
                    .aSocial(true)
                    .build();

            all_member.addRole(MemberRole.USER);
             all_memberRepository.save(all_member);

            //MemberSecurityDTO 구성 및 반환
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(email, "1111", email, memberName, "010",
                            "default", false, true,
                            Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));
            memberSecurityDTO.setProps(params);

            return memberSecurityDTO;

        } else {
            All_Member all_member = result.get();
            MemberSecurityDTO memberSecurityDTO =
                    new MemberSecurityDTO(
                            all_member.getAllId(),
                            all_member.getAPsw(),
                            all_member.getEmail(),
                            all_member.getName(),
                            all_member.getAPhone(),
                            all_member.getMemberType(),
                            all_member.isDel(),
                            all_member.isASocial(),
                            all_member.getRoleSet()
                                    .stream().map(memberRole ->
                                            new SimpleGrantedAuthority("ROLE_"+memberRole.name()))
                                    .collect(Collectors.toList())
                    );
            return memberSecurityDTO;
        }
    }

    //paramMap은 Oth2에서 사용하는 키-값 쌍의 형식 데이터 구조
    //기업마다 데이터구조가 다름
    private String[] getKakao(Map<String, Object> paramMap){
        log.info("KAKAO-------------------------------");
        Object value = paramMap.get("kakao_account");
        log.info(value);

        LinkedHashMap accountMap = (LinkedHashMap) value;
        String[] kakaoData = new String[2];
        kakaoData[0] = (String) accountMap.get("email");

        Map<String, Object> profile = (Map<String, Object>) accountMap.get("profile");
        kakaoData[1] = (String) profile.get("nickname");
        log.info("Email: " + kakaoData[0] + "  Name: " + kakaoData[1]);
        return kakaoData;
    }

    private String[] getGoogle(Map<String, Object> paramMap) {
        log.info("GOOGLE-------------------------------");
        String[] googleData = new String[2];
        googleData[0] = (String) paramMap.get("email");
        googleData[1] = (String) paramMap.get("name");
        log.info("Email: " + googleData[0] + "Name: " + googleData[1]);
        return googleData;
    }
}
