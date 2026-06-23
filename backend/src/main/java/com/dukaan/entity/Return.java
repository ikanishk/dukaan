package com.dukaan.entity;

import com.dukaan.entity.enums.ReturnStatus;
import com.dukaan.entity.enums.ReturnType;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "returns", indexes = {
    @Index(name = "idx_return_date", columnList = "returnDate"),
    @Index(name = "idx_return_device", columnList = "device_id"),
    @Index(name = "idx_return_sale", columnList = "sale_id")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Return extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sale_id")
    private Sale sale;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReturnType returnType;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private ReturnStatus status;
    
    @Column(nullable = false)
    private LocalDate returnDate;
    
    @Column(nullable = false, length = 500)
    private String reason;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal refundAmount = BigDecimal.ZERO;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private Boolean processed = false;
    
    private LocalDate processedDate;
}
