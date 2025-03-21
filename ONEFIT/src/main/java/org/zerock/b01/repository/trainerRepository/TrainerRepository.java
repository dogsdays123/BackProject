package org.zerock.b01.repository.trainerRepository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.trainer.Trainer;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("SELECT t FROM Trainer t WHERE t.trainerId = :tid")
    Optional<Trainer> findTrainerById(Long tid);

    @Query("SELECT u FROM User_Member u WHERE u.userId = :uid")
    Optional<User_Member> findUserMemberById(Long uid);

    @Query("SELECT a FROM All_Member a WHERE a.allId = :aid")
    Optional<All_Member> findAllMemberById(String aid);
}
