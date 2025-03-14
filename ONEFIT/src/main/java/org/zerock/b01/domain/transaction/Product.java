package org.zerock.b01.domain.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;

import java.math.BigDecimal;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long product_id;

    @Column(nullable = false)
    private String p_addr;

    @Column(nullable = false)
    private int p_roles;

    @Column(nullable = false)
    private String p_status;

    @Column(nullable = false, precision = 10, scale = 0)
    private BigDecimal p_price;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String p_content;

    @Column(length = 255, nullable = false)
    private String p_title;

    @Column(length = 255, nullable = false)
    private String p_chat_url;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member all_member;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
