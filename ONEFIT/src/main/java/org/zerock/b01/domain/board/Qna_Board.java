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
public class Qna_Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 400, nullable = false)
    private Long qnaId;

    @Column(length = 400, nullable = false)
    private String qTitle;

    @Column(length = 4000, nullable = false)
    private String qContent;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int qHits;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    public void changeQna(String qTitle, String qContent) {
        this.qTitle = qTitle;
        this.qContent = qContent;
    }

    public void increaseQnaHits() {
        this.qHits++;
    }

    //날짜는 BaseEntity
    //날짜는 BaseEntity
    //날짜는 BaseEntity
}
