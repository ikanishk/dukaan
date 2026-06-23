package com.dukaan.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardResponse {
    private long totalDevices;
    private long devicesInStock;
    private long devicesSold;
    private long devicesUnderRepair;
    private long totalSalesToday;
    private long totalSalesThisMonth;
    private BigDecimal revenueToday;
    private BigDecimal revenueThisMonth;
    private BigDecimal profitToday;
    private BigDecimal profitThisMonth;
    private BigDecimal expensesToday;
    private BigDecimal expensesThisMonth;
    private BigDecimal netProfitToday;
    private BigDecimal netProfitThisMonth;
    private long returnsToday;
    private long returnsThisMonth;
}
