package com.dukaan.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DeviceRequest {
    @NotBlank(message = "IMEI is required")
    private String imei;
    
    @NotBlank(message = "Model is required")
    private String model;
    
    private String brand;
    private String color;
    private String storage;
    private String ram;
    
    @NotNull(message = "Supplier ID is required")
    private Long supplierId;
    
    @NotNull(message = "Purchase price is required")
    @Positive(message = "Purchase price must be positive")
    private BigDecimal purchasePrice;
    
    private String notes;
    private String condition;
    private String accessories;
}
