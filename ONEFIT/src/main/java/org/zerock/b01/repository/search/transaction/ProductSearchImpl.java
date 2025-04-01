package org.zerock.b01.repository.search.transaction;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.domain.transaction.QInterest;
import org.zerock.b01.domain.transaction.QProduct;
import org.zerock.b01.domain.transaction.QProductReply;
import org.zerock.b01.dto.transactionDTO.ImageFileDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;
import org.zerock.b01.repository.search.transaction.ProductSearch;


import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ProductSearchImpl extends QuerydslRepositorySupport implements ProductSearch {
    public ProductSearchImpl() {
        super(Product.class);
    }


    @Override
    public Page<ProductListAllDTO> searchWithAllProducts(String[] types, String keyword, Integer minPrice, Integer maxPrice,
                                                         String categoryType, String categoryName,
                                                         Boolean onSale, Boolean reserved, Boolean soldOut, String allId,
                                                         Boolean interested, String metroGov, String muniGov,
                                                         Pageable pageable) {
        QProduct product = QProduct.product;
        QProductReply reply = QProductReply.productReply;
        QInterest interest = QInterest.interest;

        // Product 엔터티를 기준으로 JQLQuery 생성
        JPQLQuery<Product> productJPQLQuery = from(product);
        // Board와 Reply를 left join
        productJPQLQuery.leftJoin(reply).on(reply.product.eq(product));

        BooleanBuilder booleanBuilder = new BooleanBuilder();

        if (types != null && types.length > 0 && keyword != null) { // 검색 조건과 키워드가 있다면

            for (String type : types) {
                switch (type) {
                    case "t": // 제목  검색
                        booleanBuilder.or(product.pTitle.contains(keyword));
                        break;
                    case "c": // 내용 검색
                        booleanBuilder.or(product.pContent.contains(keyword));
                        break;
                    case "w": // 작성자 검색
                        booleanBuilder.or(product.allMember.allId.contains(keyword));
                        break;
                }
            } // end for

        }

        // 카테고리 필터
        if (categoryType != null) {
            if (categoryType.equals("eq")) {
                booleanBuilder.and(product.pRoles.eq(1));
            } else if (categoryType.equals("fa")) {
                booleanBuilder.and(product.pRoles.eq(2));
            }
            if (categoryName != null) {
                booleanBuilder.and(product.category.cCategory.contains(categoryName));
            }
        }

        // 가격 필터
        if (minPrice != null && maxPrice != null) {
            booleanBuilder.and(product.pPrice.between(minPrice, maxPrice));
        } else if (minPrice != null) {
            booleanBuilder.and(product.pPrice.goe(minPrice)); // minPrice 이상
        } else if (maxPrice != null) {
            booleanBuilder.and(product.pPrice.loe(maxPrice)); // maxPrice 이하
        }

        // 판매 상태 필터
        BooleanBuilder saleStatusBuilder = new BooleanBuilder();

        if (onSale != null) {
            saleStatusBuilder.or(product.pStatus.contains("판매중"));
        }
        if (reserved != null) {
            saleStatusBuilder.or(product.pStatus.contains("예약중"));
        }
        if (soldOut != null) {
            saleStatusBuilder.or(product.pStatus.contains("판매완료"));
        }

        // 판매 상태 필터 - 그 OR 묶인 조건을 전체 AND에 추가
        booleanBuilder.and(saleStatusBuilder);

        // 작성자 ID 필터 (관심상품이 아닐 경우)
        if (allId != null && (interested == null || !interested)) {
            booleanBuilder.and(product.allMember.allId.eq(allId));
        }

        // 관심상품 필터
        if (allId != null && Boolean.TRUE.equals(interested)) {
            productJPQLQuery.innerJoin(interest).on(interest.product.eq(product)); // 관심상품 조인 추가
            booleanBuilder.and(interest.allMember.allId.eq(allId));
        }

        // 지역 필터
        if (metroGov != null) {
//            String transformedMetroGov = metroMap.getOrDefault(metroGov, metroGov);
            booleanBuilder.and(product.pAddrMetroGov.contains(metroGov));

            if (muniGov != null) {
                booleanBuilder.and(product.pAddrMuniGov.contains(muniGov));
            }
        }

        // 조건을 쿼리에 적용
        productJPQLQuery.where(booleanBuilder);

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
