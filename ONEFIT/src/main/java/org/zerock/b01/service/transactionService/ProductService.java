package org.zerock.b01.service.transactionService;

import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;
import org.zerock.b01.dto.transactionDTO.ProductDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public interface ProductService {
    Long registerEquipment(EquipmentDTO equipmentDTO);

    Long registerFacility(FacilityDTO facilityDTO);

    EquipmentDTO readEquipmentOne(Long productId);

    FacilityDTO readFacilityOne(Long productId);

    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<ProductListAllDTO> listWithAllProducts(PageRequestDTO pageRequestDTO);

    default ProductDTO entityToDto(Product product) {

        ProductDTO productDTO = ProductDTO.builder()
                .productId(product.getProductId())
                .categoryId(product.getCategory().getCategoryId())
                .allId(product.getAllMember().getAllId())
                .pAddr(product.getPAddr())
                .pRoles(product.getPRoles())
                .pStatus(product.getPStatus())
                .pPrice(product.getPPrice())
                .pContent(product.getPContent())
                .pTitle(product.getPTitle())
                .pChatUrl(product.getPChatUrl())
                .cCategory(product.getCategory().getCCategory())
                .regDate(product.getRegDate())
                .modDate(product.getModDate())
                .build();

        // 게시글에 포함된 이미지 파일명을 리스트로 변환
        List<String> fileNames =
                product.getImageSet().stream()
                        .sorted()
                        .map(boardImage ->
                                boardImage.getImageUuid() + "_" + boardImage.getImageFileName())
                        .collect(Collectors.toList());

        // 변환된 파일명 리스트를 DTO에 설정
        productDTO.setImageFileNames(fileNames);

        return productDTO;
    }

    default void productDTOIntoEqDto(ProductDTO productDTO, EquipmentDTO equipmentDTO) {

        equipmentDTO.setProductId(productDTO.getProductId());
        equipmentDTO.setCategoryId(productDTO.getCategoryId());
        equipmentDTO.setAllId(productDTO.getAllId());
        equipmentDTO.setPAddr(productDTO.getPAddr());
        equipmentDTO.setPRoles(productDTO.getPRoles());
        equipmentDTO.setPStatus(productDTO.getPStatus());
        equipmentDTO.setPPrice(productDTO.getPPrice());
        equipmentDTO.setPContent(productDTO.getPContent());
        equipmentDTO.setPTitle(productDTO.getPTitle());
        equipmentDTO.setPChatUrl(productDTO.getPChatUrl());
        equipmentDTO.setCCategory(productDTO.getCCategory());
        equipmentDTO.setImageFileNames(productDTO.getImageFileNames());
        equipmentDTO.setRegDate(productDTO.getRegDate());
        equipmentDTO.setModDate(productDTO.getModDate());
    }

    default void productDTOIntoFaDto(ProductDTO productDTO, FacilityDTO facilityDTO) {
        facilityDTO.setProductId(productDTO.getProductId());
        facilityDTO.setCategoryId(productDTO.getCategoryId());
        facilityDTO.setAllId(productDTO.getAllId());
        facilityDTO.setPAddr(productDTO.getPAddr());
        facilityDTO.setPRoles(productDTO.getPRoles());
        facilityDTO.setPStatus(productDTO.getPStatus());
        facilityDTO.setPPrice(productDTO.getPPrice());
        facilityDTO.setPContent(productDTO.getPContent());
        facilityDTO.setPTitle(productDTO.getPTitle());
        facilityDTO.setPChatUrl(productDTO.getPChatUrl());
        facilityDTO.setCCategory(productDTO.getCCategory());
        facilityDTO.setImageFileNames(productDTO.getImageFileNames());
        facilityDTO.setRegDate(productDTO.getRegDate());
        facilityDTO.setModDate(productDTO.getModDate());
    }

}
