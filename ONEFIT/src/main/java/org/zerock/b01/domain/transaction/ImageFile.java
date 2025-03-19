package org.zerock.b01.domain.transaction;

import jakarta.persistence.*;
import lombok.*;

@Entity(name = "image_file")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "product")
public class ImageFile implements Comparable<ImageFile>{
    @Id
    @Column(name = "image_uuid", length = 100, nullable = false)
    private String imageUuid; // 이미지 파일 ID

    @Column(name = "image_file_name", length = 100, nullable = false)
    private String imageFileName; // 파일 이름

    @Column(nullable = false)
    private int ord; // 파일 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product; // (외래키) 상품 ID

    @Override
    public int compareTo(ImageFile other) {
        return ord - other.ord;
    }

    public void changeProduct(Product product) {
        this.product = product;
    }
}
