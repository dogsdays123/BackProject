package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.transaction.Interest;

public interface InterestRepository extends JpaRepository<Interest, Integer> {
}
