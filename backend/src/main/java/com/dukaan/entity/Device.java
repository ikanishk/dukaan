package com.dukaan.entity;

import com.dukaan.entity.enums.DeviceStatus;
import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "devices", indexes = {
    @Index(name = "idx_device_sku", columnList = "sku", unique = true),
    @Index(name = "idx_device_imei", columnList = "imei", unique = true),
    @Index(name = "idx_device_status", columnList = "status"),
    @Index(name = "idx_device_model", columnList = "model")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 20)
    private String sku;
    
    @Column(nullable = false, unique = true, length = 20)
    private String imei;
    
    @Column(nullable = false, length = 100)
    private String model;
    
    @Column(length = 50)
    private String brand;
    
    @Column(length = 50)
    private String color;
    
    @Column(length = 50)
    private String storage;
    
    @Column(length = 50)
    private String ram;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "supplier_id", nullable = false)
    private Supplier supplier;
    
    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal purchasePrice;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private DeviceStatus status = DeviceStatus.IN_STOCK;
    
    @Column(length = 500)
    private String barcodeData;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(length = 100)
    private String condition;
    
    @Column(length = 500)
    private String accessories;
}
