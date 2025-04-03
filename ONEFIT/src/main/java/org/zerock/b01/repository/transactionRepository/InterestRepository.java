package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.All_Member;
import org.zerock.b01.domain.transaction.Interest;
import org.zerock.b01.domain.transaction.Product;

import java.util.List;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
    Long countByProduct(Product product);
    boolean existsByProductAndAllMember(Product product, All_Member allMember);
    void deleteByProductAndAllMember(Product product, All_Member allMember);
    void deleteByProduct(Product product);
}
