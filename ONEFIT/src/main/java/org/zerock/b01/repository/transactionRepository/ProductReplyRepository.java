package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerock.b01.domain.transaction.Product;
import org.zerock.b01.domain.transaction.ProductReply;

import java.util.List;

public interface ProductReplyRepository extends JpaRepository<ProductReply, Long> {
    @Query("select pr from  ProductReply pr where pr.product.productId = :productId")
    Page<ProductReply> listOfProduct(Long productId, Pageable pageable);
    void deleteByProduct(Product product);

}
