package org.zerock.b01.service.recruitService;

import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.recruitDTO.RecruitDTO;

public interface RecruitService {

    Long register(RecruitDTO recruitDTO);

    RecruitDTO readOne(Long recruitId);

    void modify(RecruitDTO recruitDTO);

    void remove(Long recruitId);

    PageResponseDTO<RecruitDTO> list(PageRequestDTO pageRequestDTO);
}
