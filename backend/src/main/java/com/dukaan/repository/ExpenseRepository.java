package com.dukaan.repository;

import com.dukaan.entity.Expense;
import com.dukaan.entity.enums.ExpenseCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByDeletedFalse();
    Page<Expense> findByDeletedFalse(Pageable pageable);
    Page<Expense> findByCategoryAndDeletedFalse(ExpenseCategory category, Pageable pageable);
    Page<Expense> findByExpenseDateBetweenAndDeletedFalse(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate AND e.deleted = false")
    BigDecimal calculateTotalExpenses(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT e.category, SUM(e.amount) FROM Expense e WHERE e.expenseDate BETWEEN :startDate AND :endDate AND e.deleted = false GROUP BY e.category")
    List<Object[]> getExpensesByCategory(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
