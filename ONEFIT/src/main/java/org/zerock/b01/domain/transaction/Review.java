package org.zerock.b01.domain.transaction;
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
public class Review extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long review_id;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String r_content;

    @Column(nullable = false)
    private byte r_score;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
