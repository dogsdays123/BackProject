package org.zerock.b01.repository.trainerRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.trainer.Trainer;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT t FROM Trainer t WHERE t.trainerId =: trainerId")
    Optional<Trainer> findTrainerById(Long trainerId);
}
