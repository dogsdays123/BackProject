package org.zerock.b01.domain.board;
import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;

@Entity
@Table(name = "Board_Reply", indexes = {
        @Index(name = "idx_board_reply_noticeId", columnList = "notice_id"),
        @Index(name = "idx_board_reply_qnaId", columnList = "qna_id")
})
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "qna_id")
    private Qna_Board qnaBoard;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private Notice_Board noticeBoard;

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity

    public void changeText(String text) {
        this.replyText = text;
    }
}
