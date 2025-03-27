package org.zerock.b01.domain.transaction;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.zerock.b01.domain.All_Member;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Interest { // 관심상품
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interest_id")
    private Long interestId;

    @Column(nullable = false)
    private LocalDateTime regDate;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember;

    public void setRegDate(LocalDateTime regDate) {
        this.regDate = regDate;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setAllMember(All_Member allMember) {
        this.allMember = allMember;
    }
}
