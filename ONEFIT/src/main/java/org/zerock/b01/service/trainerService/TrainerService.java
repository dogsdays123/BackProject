package org.zerock.b01.service.trainerService;

import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageRequestDTO;
import org.zerock.b01.dto.trainerDTO.TrainerPageResponseDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;

import java.util.List;

public interface TrainerService {
    Long registerTrainer(TrainerDTO trainerDTO);
    TrainerViewDTO viewOne(Long trainerId);
    void removeTrainer(Long trainerId);
    TrainerPageResponseDTO<TrainerViewDTO> list(TrainerPageRequestDTO pageRequestDTO);
    int trainerCount(Long uid);

    TrainerDTO getTrainerByUserId(Long userId);
    List<TrainerDTO> getTrainersByUserIds(List<Long> userId);
}
