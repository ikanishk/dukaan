package com.dukaan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceResponse {
    private Long id;
    private String sku;
    private String imei;
    private String model;
    private String brand;
    private String color;
    private String storage;
    private String ram;
    private Long supplierId;
    private String supplierName;
    private BigDecimal purchasePrice;
    private String status;
    private String barcodeData;
    private String notes;
    private String condition;
    private String accessories;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
