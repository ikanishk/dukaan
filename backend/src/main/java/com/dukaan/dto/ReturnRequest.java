package com.dukaan.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReturnRequest {
    @NotNull(message = "Device ID is required")
    private Long deviceId;
    
    private Long saleId;
    
    @NotBlank(message = "Return type is required")
    private String returnType;
    
    @NotNull(message = "Return date is required")
    private LocalDate returnDate;
    
    @NotBlank(message = "Reason is required")
    private String reason;
    
    private BigDecimal refundAmount;
    private String notes;
}
