package org.zerock.b01.repository.search.transaction;

import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.domain.transaction.QProduct;
import org.zerock.b01.domain.transaction.QProductReply;
import org.zerock.b01.dto.transactionDTO.ImageFileDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;
import org.zerock.b01.repository.search.transaction.ProductSearch;


import java.util.List;
import java.util.stream.Collectors;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }


    @Override
    public Page<ProductListAllDTO> searchWithAllProducts(String[] types, String keyword, Pageable pageable) {
        QProduct product = QProduct.product;
        QProductReply reply = QProductReply.productReply;

        // Product 엔터티를 기준으로 JQLQuery 생성
        JPQLQuery<Product> productJPQLQuery = from(product);
        // Board와 Reply를 left join
        productJPQLQuery.leftJoin(reply).on(reply.product.eq(product));

        if (types != null && types.length > 0 && keyword != null) { // 검색 조건과 키워드가 있다면

        }

        productJPQLQuery.groupBy(product);

        getQuerydsl().applyPagination(pageable, productJPQLQuery);

        JPQLQuery<Tuple> tupleJPQLQuery = productJPQLQuery.select(product, reply.countDistinct());

        List<Tuple> tupleList = tupleJPQLQuery.fetch();

        List<ProductListAllDTO> dtoList = tupleList.stream().map(tuple -> {
            Product product1 = (Product) tuple.get(product);
            long replyCount = tuple.get(1, Long.class);

            // 게시글 정보 DTO로 변환
            assert product1 != null;
            ProductListAllDTO dto = ProductListAllDTO.builder()
                    .productId(product1.getProductId()) // 상품 ID
                    .allId(product1.getAllMember().getAllId()) // 회원 ID
                    .pTitle(product1.getPTitle()) // 제목
                    .pRoles(product1.getPRoles()) // 상품 구분
                    .pStatus(product1.getPStatus()) // 거래 상태
                    .pPrice(product1.getPPrice()) // 가격
                    .pAddr(product1.getPAddr()) // 거래 장소
                    .regDate(product1.getRegDate())  // 등록 시각
                    .replyCount(replyCount) // 댓글 수
                    .build();

            // 게시글 이미지 정보를 DTO로 변환
            List<ImageFileDTO> imageDTOS = product1.getImageSet().stream()
                    .sorted()
                    .map(productImage -> ImageFileDTO.builder()
                            .imageUuid(productImage.getImageUuid())
                            .imageFileName(productImage.getImageFileName())
                            .ord(productImage.getOrd())
                            .build()

                    ).collect(Collectors.toList());
            // 변환된 이미지 리스트를 DTO에 설정
            dto.setImageFileList(imageDTOS);

            return dto;
        }).collect(Collectors.toList());

        // 전체 게시글 수 가져오기
        long totalCount = tupleJPQLQuery.fetchCount();
        // 페이지 객체 생성 후 반환
        return new PageImpl<>(dtoList, pageable, totalCount);

    }
}
