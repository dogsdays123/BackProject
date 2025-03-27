package org.zerock.b01.repository.trainerRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;

public interface TrainerSearch {
    Page<TrainerViewDTO> search(String[] filters, Pageable pageable);
}
