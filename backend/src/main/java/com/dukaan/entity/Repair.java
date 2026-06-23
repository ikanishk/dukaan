package com.dukaan.entity;

import com.dukaan.entity.enums.RepairStatus;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "repairs", indexes = {
    @Index(name = "idx_repair_device", columnList = "device_id"),
    @Index(name = "idx_repair_status", columnList = "status"),
    @Index(name = "idx_repair_date", columnList = "repairDate")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repair extends BaseEntity {
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
    
    @Column(nullable = false, length = 100)
    private String vendorName;
    
    @Column(length = 15)
    private String vendorMobile;
    
    @Column(nullable = false, length = 500)
    private String repairReason;
    
    @Column(nullable = false)
    private LocalDate repairDate;
    
    private LocalDate expectedReturnDate;
    
    private LocalDate actualReturnDate;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal repairCost;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private RepairStatus status;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(length = 500)
    private String workDone;
}
