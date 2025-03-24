package org.zerock.b01.repository.trainerRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;

import java.util.List;
import java.util.Optional;

public interface Trainer_ThumbnailsRepository extends JpaRepository<Trainer_Thumbnails, String> {
    @Query("SELECT t FROM Trainer_Thumbnails t WHERE t.trainer.trainerId = :tid")
    List<Optional<Trainer_Thumbnails>> findByTid(Long tid);

    @Modifying
    @Query("DELETE FROM Trainer_Thumbnails t WHERE t.trainer.trainerId = :tid")
    void deleteByTid(Long tid);
}
