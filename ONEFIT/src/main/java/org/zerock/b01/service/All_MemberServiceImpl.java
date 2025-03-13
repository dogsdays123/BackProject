package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.repository.All_MemberRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class All_MemberServiceImpl implements All_MemberService {

    private final ModelMapper modelMapper;
    private final All_MemberRepository all_MemberRepository;

    @Override
    public Long register(All_MemberDTO all_MemberDTO) {
        All_Member all_Member = modelMapper.map(all_MemberDTO, All_Member.class);
        Long all_id = all_MemberRepository.save(all_Member).getAll_id();
        return all_id;
    }

    @Override
    public All_MemberDTO readOne(Long bno) {
        Optional<All_Member> result = all_MemberRepository.findById(bno);
        All_Member all_Member = result.orElseThrow();
        All_MemberDTO all_MemberDTO = modelMapper.map(all_Member, All_MemberDTO.class);
        return all_MemberDTO;
    }

    @Override
    public void modify(All_MemberDTO all_MemberDTO) {
        Optional<All_Member> result = all_MemberRepository.findById(all_MemberDTO.getAll_id());
        All_Member all_Member = result.orElseThrow();
        //all_Member.change(all_MemberDTO.getName(), all_MemberDTO.getEmail());
        all_MemberRepository.save(all_Member);
    }

    @Override
    public void remove(Long all_id) {
        all_MemberRepository.deleteById(all_id);
    }
}
