package com.dukaan.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SaleRequest {
    @NotNull(message = "Device ID is required")
    private Long deviceId;
    
    private Long partyId;
    
    @NotNull(message = "Sale price is required")
    @Positive(message = "Sale price must be positive")
    private BigDecimal salePrice;
    
    @NotNull(message = "Sale date is required")
    private LocalDate saleDate;
    
    private String paymentMode;
    
    @NotNull(message = "Amount paid is required")
    private BigDecimal amountPaid;
    
    private String notes;
}
