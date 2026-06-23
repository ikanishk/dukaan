package com.dukaan.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "sales", indexes = {
    @Index(name = "idx_sale_date", columnList = "saleDate"),
    @Index(name = "idx_sale_invoice", columnList = "invoiceNumber"),
    @Index(name = "idx_sale_device", columnList = "device_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sale extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 50)
    private String invoiceNumber;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    private Party party;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal salePrice;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal purchasePrice;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal profit;
    
    @Column(nullable = false)
    private LocalDate saleDate;
    
    @Column(length = 50)
    private String paymentMode;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal amountPaid = BigDecimal.ZERO;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal amountDue = BigDecimal.ZERO;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private Boolean returned = false;
    
    private LocalDate returnDate;
    
    @Column(length = 500)
    private String returnReason;
}
