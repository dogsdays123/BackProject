package org.zerock.b01.repository.transactionRepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zerock.b01.domain.transaction.Category;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
