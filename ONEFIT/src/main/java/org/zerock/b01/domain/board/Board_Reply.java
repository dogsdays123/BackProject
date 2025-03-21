package org.zerock.b01.domain.board;
import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Board_Reply extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(length = 1000, nullable = false)
    private String replyText;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    @ManyToOne
    @JoinColumn(name = "qna_id")
    private Qna_Board qnaBoard;

    @ManyToOne
    @JoinColumn(name = "notice_id")
    private Notice_Board noticeBoard;

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
