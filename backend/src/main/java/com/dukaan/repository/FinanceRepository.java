package com.dukaan.repository;

import com.dukaan.entity.Finance;
import com.dukaan.entity.enums.FinanceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceRepository extends JpaRepository<Finance, Long> {
    List<Finance> findByActiveTrue();
    List<Finance> findByDeletedFalse();
    Page<Finance> findByDeletedFalse(Pageable pageable);
    List<Finance> findByFinanceTypeAndDeletedFalse(FinanceType financeType);
}
