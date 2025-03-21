package org.zerock.b01.service.trainerService;

import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;
import org.zerock.b01.dto.trainerDTO.TrainerViewDTO;

public interface TrainerService {
    Long registerTrainer(TrainerDTO trainerDTO);
    TrainerViewDTO viewOne(Long trainerId);
}
