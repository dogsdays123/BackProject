package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.dto.transactionDTO.ProductListAllDTO;
import org.zerock.b01.repository.search.transaction.ProductSearch;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long>, ProductSearch {
    @EntityGraph(attributePaths = {"imageSet"})
    @Query("select p from Product p where p.productId =:productId")
    Optional<Product> findByIdWithImages(Long productId);
}
