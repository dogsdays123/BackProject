package org.zerock.b01.service.transactionService;

import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.dto.PageRequestDTO;
import org.zerock.b01.dto.PageResponseDTO;
import org.zerock.b01.dto.transactionDTO.EquipmentDTO;
import org.zerock.b01.dto.transactionDTO.FacilityDTO;
import org.zerock.b01.dto.transactionDTO.ProductDTO;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;

import java.math.BigDecimal;

public interface ProductService {
    Long registerEquipment(EquipmentDTO equipmentDTO);
    Long registerFacility(FacilityDTO facilityDTO);

    // 게시글의 이미지와 댓글의 숫자까지 처리
    PageResponseDTO<ProductListAllDTO> listWithAllProducts(PageRequestDTO pageRequestDTO);

}
