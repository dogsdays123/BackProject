package org.zerock.b01.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.domain.member.MemberRole;
import org.zerock.b01.dto.All_MemberDTO;
import org.zerock.b01.dto.boardDTO.NoticeBoardDTO;
import org.zerock.b01.dto.boardDTO.QnaBoardDTO;
import org.zerock.b01.dto.memberDTO.AllBoardSearchDTO;
import org.zerock.b01.repository.All_MemberRepository;

import java.util.Collections;
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
        log.info("ServiceAllId@@@@" + result.toString());

        if(result.isPresent()){
            All_Member all_Member = result.orElseThrow();
            All_MemberDTO all_MemberDTO = modelMapper.map(all_Member, All_MemberDTO.class);
            return all_MemberDTO;
        } else {
            log.info("널!!!!!");
            return null;
        }
    }

    @Override
    public All_MemberDTO readOneForEmail(String email) {
        Optional<All_Member> result = all_MemberRepository.findByEmail(email);
        log.info("ServiceEmail@@@@" + result.toString());

        if(result.isPresent()){
            All_Member all_Member = result.orElseThrow();
            All_MemberDTO all_MemberDTO = modelMapper.map(all_Member, All_MemberDTO.class);
            return all_MemberDTO;
        } else {
            log.info("널!!!!!");
            return null;
        }
    }

    @Override
    public void modify(All_MemberDTO all_MemberDTO) {
        log.info("AllId@@@@" + all_MemberDTO.getAllId());
        Optional<All_Member> result = all_MemberRepository.findById(all_MemberDTO.getAllId());
        All_Member all_Member = result.orElseThrow();
        all_Member.modifyMember(passwordEncoder.encode(all_MemberDTO.getAPsw()), all_MemberDTO.getAPhone(), all_MemberDTO.getMemberType());

        all_MemberRepository.save(all_Member);
        log.info("modify@@@@" + all_Member);
    }

    @Transactional
    @Override
    public void remove(String allId) {all_MemberRepository.removeMember(allId);}

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

    @Override
    public List<All_MemberDTO> readAllMember(){
        List<All_Member> result = all_MemberRepository.findAll();
        if(result.isEmpty()){
            return Collections.emptyList(); // 결과가 없으면 빈 리스트 반환
        }

        List<All_MemberDTO> all_MemberDTOList = result.stream()
                .map(allMember -> modelMapper.map(allMember, All_MemberDTO.class)).collect(Collectors.toList());
        return all_MemberDTOList;
    }

    @Override
    public AllBoardSearchDTO boardReadForAllMember(String allId) {
        List<Notice_Board> resultN = all_MemberRepository.findNoticeForAllId(allId);
        List<Qna_Board> resultQ = all_MemberRepository.findQnaForAllId(allId);

        AllBoardSearchDTO allBoardSearchDTO = new AllBoardSearchDTO();

        if (resultN.isEmpty() && resultQ.isEmpty()) {
            return null;
        } else if (!resultN.isEmpty() && resultQ.isEmpty()) {
            allBoardSearchDTO.setNoticeBoardDTO(findNoticeBoard(resultN));
        } else if (!resultQ.isEmpty() && resultN.isEmpty()) {
            allBoardSearchDTO.setQnaBoardDTO(findQnaBoard(resultQ));
        } else {
            allBoardSearchDTO.setNoticeBoardDTO(findNoticeBoard(resultN));
            allBoardSearchDTO.setQnaBoardDTO(findQnaBoard(resultQ));
        }

        return allBoardSearchDTO;
    }

    public List<NoticeBoardDTO> findNoticeBoard(List<Notice_Board> result) {
        return result.stream()
                .map(noticeBoard -> modelMapper.map(noticeBoard, NoticeBoardDTO.class))
                .collect(Collectors.toList());
    }

    public List<QnaBoardDTO> findQnaBoard(List<Qna_Board> result) {
        return result.stream()
                .map(qnaBoard -> modelMapper.map(qnaBoard, QnaBoardDTO.class))
                .collect(Collectors.toList());
    }
}
