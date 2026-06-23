package com.dukaan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaleResponse {
    private Long id;
    private String invoiceNumber;
    private Long deviceId;
    private String deviceSku;
    private String deviceModel;
    private Long partyId;
    private String partyName;
    private BigDecimal salePrice;
    private BigDecimal purchasePrice;
    private BigDecimal profit;
    private LocalDate saleDate;
    private String paymentMode;
    private BigDecimal amountPaid;
    private BigDecimal amountDue;
    private String notes;
    private Boolean returned;
    private LocalDate returnDate;
    private String returnReason;
    private LocalDateTime createdAt;
}
