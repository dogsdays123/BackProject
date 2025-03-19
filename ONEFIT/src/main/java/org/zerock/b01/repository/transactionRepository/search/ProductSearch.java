package org.zerock.b01.repository.transactionRepository.search;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;

public interface ProductSearch {
    Page<ProductListAllDTO> searchWithAllProducts(String[] types, String keyword, Pageable pageable);
}
