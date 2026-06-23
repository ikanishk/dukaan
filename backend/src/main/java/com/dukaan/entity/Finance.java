package com.dukaan.entity;

import com.dukaan.entity.enums.FinanceType;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "finances", indexes = {
    @Index(name = "idx_finance_type", columnList = "financeType")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Finance extends BaseEntity {
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private FinanceType financeType;
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 500)
    private String description;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal targetAmount;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal currentAmount = BigDecimal.ZERO;
    
    @Column(nullable = false)
    private LocalDate startDate;
    
    private LocalDate endDate;
    
    @Column(length = 100)
    private String institution;
    
    @Column(precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private Boolean active = true;
}
