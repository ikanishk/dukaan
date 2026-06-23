package com.dukaan.entity;

import com.dukaan.entity.enums.ExpenseCategory;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "expenses", indexes = {
    @Index(name = "idx_expense_date", columnList = "expenseDate"),
    @Index(name = "idx_expense_category", columnList = "category")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ExpenseCategory category;
    
    @Column(nullable = false, length = 200)
    private String description;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDate expenseDate;
    
    @Column(length = 50)
    private String paymentMode;
    
    @Column(length = 50)
    private String referenceNumber;
    
    @Column(length = 100)
    private String vendor;
    
    @Column(length = 1000)
    private String notes;
}
