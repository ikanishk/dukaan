package com.dukaan.repository;

import com.dukaan.entity.FinanceTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinanceTransactionRepository extends JpaRepository<FinanceTransaction, Long> {
    List<FinanceTransaction> findByDeletedFalse();
    
    @Query("SELECT ft FROM FinanceTransaction ft WHERE ft.finance.id = :financeId AND ft.deleted = false ORDER BY ft.transactionDate DESC")
    List<FinanceTransaction> findByFinanceId(@Param("financeId") Long financeId);
    
    Page<FinanceTransaction> findByFinanceIdAndDeletedFalse(Long financeId, Pageable pageable);
}
