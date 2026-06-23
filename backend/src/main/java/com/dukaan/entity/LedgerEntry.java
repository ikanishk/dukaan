package com.dukaan.entity;

import com.dukaan.entity.enums.LedgerEntryType;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "ledger_entries", indexes = {
    @Index(name = "idx_ledger_party", columnList = "party_id"),
    @Index(name = "idx_ledger_date", columnList = "entryDate"),
    @Index(name = "idx_ledger_type", columnList = "entryType")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LedgerEntry extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id", nullable = false)
    private Party party;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private LedgerEntryType entryType;
    
    @Column(nullable = false)
    private LocalDate entryDate;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal debit = BigDecimal.ZERO;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal credit = BigDecimal.ZERO;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 50)
    private String referenceNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "return_id")
    private Return returnRecord;
    
    @Column(length = 1000)
    private String notes;
}
