package com.dukaan.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finance_transactions", indexes = {
    @Index(name = "idx_finance_trans_finance", columnList = "finance_id"),
    @Index(name = "idx_finance_trans_date", columnList = "transactionDate")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinanceTransaction extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finance_id", nullable = false)
    private Finance finance;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;
    
    @Column(nullable = false)
    private LocalDate transactionDate;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 50)
    private String referenceNumber;
    
    @Column(length = 1000)
    private String notes;
}
