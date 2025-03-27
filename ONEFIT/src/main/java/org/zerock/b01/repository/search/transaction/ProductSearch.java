package org.zerock.b01.repository.search.transaction;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;

public interface ProductSearch {
    Page<ProductListAllDTO> searchWithAllProducts(String[] types, String keyword, Integer minPrice, Integer maxPrice,
                                                  String categoryType, String categoryName,
                                                  Boolean onSale, Boolean reserved, Boolean soldOut, String allId, Boolean interested,
                                                  String metroGov, String muniGov,
                                                  Pageable pageable);
}
