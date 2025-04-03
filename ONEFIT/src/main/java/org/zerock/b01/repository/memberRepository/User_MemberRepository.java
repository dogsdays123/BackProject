package org.zerock.b01.repository.memberRepository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.member.User_Member;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;

import java.util.Optional;

public interface User_MemberRepository extends JpaRepository<User_Member, Long> {
    @Query("select u from User_Member u where u.allMember.allId = :allId")
    Optional<User_Member> findUserMember(@Param("allId") String allId);

    @Query("select t from Trainer t where t.userMember.userId = :userId")
    Optional<Trainer> findTrainerForUserId(@Param("userId") Long userId);

    @Query("select tt from Trainer_Thumbnails tt where tt.trainer.trainerId = :trainerId")
    Optional<Trainer_Thumbnails> searchThumbnailsForTrainer(@Param("trainerId") Long trainerId);

    @Modifying
    @Transactional
    @Query("delete from User_Member u where u.allMember.allId =:allId")
    void removeUserMember(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Trainer t where t.userMember.allMember.allId =:allId")
    void removeTrainer(@Param("allId") String allId);
}
