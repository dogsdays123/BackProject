package org.zerock.b01.repository.trainerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.trainer.Trainer;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
}
