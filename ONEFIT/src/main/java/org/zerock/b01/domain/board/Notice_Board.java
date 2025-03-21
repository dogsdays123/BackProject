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
public class Notice_Board extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long noticeId;

    @Column(length = 400, nullable = false)
    private String nTitle;

    @Column(length = 4000, nullable = false)
    private String nContent;

    @Column(nullable = false, columnDefinition = "int default 0")
    private int nHits;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    public void changeNotice(String nTitle, String nContent) {
        this.nTitle = nTitle;
        this.nContent = nContent;
    }

    public void increaseNoticeHits() {

        this.nHits++;
    }

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
