package org.zerock.b01.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.board.Notice_Board;
import org.zerock.b01.domain.board.Qna_Board;
import org.zerock.b01.domain.recruit.Recruit_Register;
import org.zerock.b01.domain.recruit.Recruit_Register_Image;
import org.zerock.b01.domain.trainer.Trainer;
import org.zerock.b01.domain.trainer.Trainer_Thumbnails;
import org.zerock.b01.domain.transaction.Product;

import java.util.List;
import java.util.Optional;

public interface All_MemberRepository extends JpaRepository<All_Member, String> {
    @EntityGraph(attributePaths = "roleSet")
    @Query("select m from All_Member m where m.allId = :allId and m.aSocial = false")
    Optional<All_Member> getWithRoles(@Param("allId") String allId);

    @EntityGraph(attributePaths = "roleSet")
    Optional<All_Member> findByEmail(String email);

    Optional<All_Member> findByAllId(String allId);

    @Modifying
    @Transactional
    @Query("update All_Member m set m.aPsw =:aPsw, m.aPhone =:aPhone, m.memberType =:memberType where m.allId =:allId")
    void updateMember(@Param("aPsw") String aPsw, @Param("aPhone") String aPhone, @Param("memberType") String memberType, @Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from All_Member m where m.allId =:allId")
    void removeMember(@Param("allId") String allId);
    
    @Query("select am from All_Member am")
    List<All_Member> getAllMembers();

    @Query("select n from Notice_Board n where n.allMember.allId =:allId")
    List<Notice_Board> findNoticeForAllId(String allId);

    @Query("select q from Qna_Board q where q.allMember.allId =:allId")
    List<Qna_Board> findQnaForAllId(String allId);

    @Query("select q from Product q where q.allMember.allId =:allId")
    List<Product> findProductForAllId(String allId);

    @Query("select q from Trainer q where q.userMember.allMember.allId =:allId")
    List<Trainer> findTrainerForAllId(String allId);

    @Query("select q from Trainer_Thumbnails q where q.trainer.userMember.allMember.allId =:allId")
    List<Trainer_Thumbnails> findTrainerThumbnailsForAllId(String allId);

    @Query("select q from Recruit_Register q where q.business_member.allMember.allId =:allId")
    List<Recruit_Register> findRecruitForAllId(String allId);

    @Query("select q from Recruit_Register_Image q where q.recruit_register.business_member.allMember.allId =:allId")
    List<Recruit_Register_Image> findRecruitImageForAllId(String allId);

    @Modifying
    @Transactional
    @Query("delete from Notice_Board b where b.allMember.allId =:allId")
    void removeNoticeBoard(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Qna_Board b where b.allMember.allId =:allId")
    void removeQnaBoard(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Product b where b.allMember.allId =:allId")
    void removeProduct(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Recruit_Register_Image r where r.recruit_register.business_member.allMember.allId =:allId")
    void removeRecruitImage(@Param("allId") String allId);

    @Modifying
    @Transactional
    @Query("delete from Trainer_Thumbnails r where r.trainer.userMember.allMember.allId =:allId")
    void removeTrainerThumbnails(@Param("allId") String allId);
}
