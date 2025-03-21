package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.transaction.Facility;

import java.util.Optional;

public interface FacilityRepository extends JpaRepository<Facility, Long> {
    Optional<Facility> findByProduct_ProductId(Long productId);
}
