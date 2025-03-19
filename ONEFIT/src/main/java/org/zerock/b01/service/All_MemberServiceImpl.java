package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.MemberRole;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.repository.All_MemberRepository;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class All_MemberServiceImpl implements All_MemberService {

    private final ModelMapper modelMapper;
    private final All_MemberRepository all_MemberRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String register(All_MemberDTO all_MemberDTO) {
        All_Member all_Member = modelMapper.map(all_MemberDTO, All_Member.class);
        String allId = all_MemberRepository.save(all_Member).getAllId();
        return allId;
    }

    @Override
    public All_MemberDTO readOne(String allId) {
        Optional<All_Member> result = all_MemberRepository.findById(allId);
        log.info("Service@@@@" + result.toString());
        All_Member all_Member = result.orElseThrow();
        All_MemberDTO all_MemberDTO = modelMapper.map(all_Member, All_MemberDTO.class);
        return all_MemberDTO;
    }

    @Override
    public void modify(All_MemberDTO all_MemberDTO) {
        Optional<All_Member> result = all_MemberRepository.findById(all_MemberDTO.getAllId());
        All_Member all_Member = result.orElseThrow();
        //all_Member.change(all_MemberDTO.getName(), all_MemberDTO.getEmail());
        all_MemberRepository.save(all_Member);
    }

    @Override
    public void remove(String allId) {
        all_MemberRepository.deleteById(allId);
    }

    @Override
    public void join(All_MemberDTO all_memberDTO) throws MidExistException{
        String allId = all_memberDTO.getAllId();
        log.info("look at me @@@@@@@@@@   " + allId);
        boolean exist = all_MemberRepository.existsById(allId);

        if(exist){
            throw new MidExistException();
        }

        All_Member all_member = modelMapper.map(all_memberDTO, All_Member.class);
        all_member.changeAPsw(passwordEncoder.encode(all_memberDTO.getAPsw()));
        all_member.addRole(MemberRole.USER);

        log.info("=======================");
        log.info(all_member);
        log.info(all_member.getRoleSet());

        all_MemberRepository.save(all_member);
    }
}
