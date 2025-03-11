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
    private Long notice_id;

    @Column(length = 400, nullable = false)
    private String n_title;

    @Column(length = 4000, nullable = false)
    private String n_content;

    @Column(nullable = false)
    private int n_hits;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
