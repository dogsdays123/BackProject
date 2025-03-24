package org.zerock.b01.domain.transaction;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.BaseEntity;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Product extends BaseEntity { // (거래) 상품
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId; // 상품 ID

    @Column(name = "p_addr", nullable = false)
    private String pAddr; // 1. 거래 장소

    @Column(name = "p_roles", nullable = false)
    private int pRoles; // 2. 상품 구분 - (1: 기구, 2: 시설)

    @Column(name = "p_status", nullable = false)
    private String pStatus; // 3. 거래 상태 (판매중 / 예약중 / 판매완료)

    @Column(name = "p_price", nullable = false, precision = 10, scale = 0)
    private BigDecimal pPrice; // 4. 판매가

    @Column(name = "p_content", columnDefinition = "TEXT", nullable = false)
    private String pContent; // 5. 판매 내용

    @Column(name = "p_title", length = 255, nullable = false)
    private String pTitle; // 6. 제목

    @Column(name = "p_chat_url", length = 255, nullable = false)
    private String pChatUrl; // 7. 오픈 채팅 URL

    @ManyToOne
    @JoinColumn(name = "all_id", nullable = false)
    private All_Member allMember; // (외래키) 회원 ID

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category; // (외래키) 카테고리 ID

    @OneToMany(mappedBy = "product",
            cascade = {CascadeType.ALL}, fetch = FetchType.LAZY,
            orphanRemoval = true)
    @Builder.Default
    @BatchSize(size = 20)
    private Set<ImageFile> imageSet = new HashSet<>();

    public void change(EquipmentDTO equipmentDTO) {
        this.pTitle = equipmentDTO.getPTitle();
        this.pChatUrl = equipmentDTO.getPChatUrl();
        this.pStatus = equipmentDTO.getPStatus();
        this.pPrice = equipmentDTO.getPPrice();
        this.pContent = equipmentDTO.getPContent();
        this.pAddr = equipmentDTO.getPAddr();
    }

    public void change(FacilityDTO facilityDTO) {
        this.pTitle = facilityDTO.getPTitle();
        this.pChatUrl = facilityDTO.getPChatUrl();
        this.pStatus = facilityDTO.getPStatus();
        this.pPrice = facilityDTO.getPPrice();
        this.pContent = facilityDTO.getPContent();
        this.pAddr = facilityDTO.getPAddr();
    }

    public void addImageFile(String imageUuid, String imageFileName) {
        ImageFile imageFile = ImageFile.builder()
                .imageUuid(imageUuid)
                .imageFileName(imageFileName)
                .product(this)
                .ord(imageSet.size())
                .build();

        this.imageSet.add(imageFile);
    }

    public void clearImageFiles() {
        imageSet.forEach(imageFile -> imageFile.changeProduct(null));

        this.imageSet.clear();
    }

    public void setAllMember(All_Member allMember) {
        this.allMember = allMember;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    //날짜는 baseEntity
    //날짜는 baseEntity
    //날짜는 baseEntity
}
