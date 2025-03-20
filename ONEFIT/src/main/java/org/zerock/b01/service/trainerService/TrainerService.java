package org.zerock.b01.service.trainerService;

import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.dto.trainerDTO.TrainerDTO;

public interface TrainerService {
    Long registerTrainer(TrainerDTO trainerDTO);
    TrainerDTO viewOne(Long trainerId);
}
