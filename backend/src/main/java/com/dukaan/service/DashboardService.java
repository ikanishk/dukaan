package com.dukaan.service;

import com.dukaan.dto.DashboardResponse;
import com.dukaan.entity.enums.DeviceStatus;
import com.dukaan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DashboardService {
    
    private final DeviceRepository deviceRepository;
    private final SaleRepository saleRepository;
    private final ExpenseRepository expenseRepository;
    private final ReturnRepository returnRepository;
    
    @Transactional(readOnly = true)
    public DashboardResponse getDashboardData() {
        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate endOfMonth = today.withDayOfMonth(today.lengthOfMonth());
        
        // Device counts
        long totalDevices = deviceRepository.count();
        long devicesInStock = deviceRepository.countByStatus(DeviceStatus.IN_STOCK);
        long devicesSold = deviceRepository.countByStatus(DeviceStatus.SOLD);
        long devicesUnderRepair = deviceRepository.countByStatus(DeviceStatus.UNDER_REPAIR);
        
        // Sales today
        long totalSalesToday = saleRepository.countSales(today, today);
        BigDecimal revenueToday = saleRepository.calculateTotalSales(today, today);
        BigDecimal profitToday = saleRepository.calculateTotalProfit(today, today);
        
        // Sales this month
        long totalSalesThisMonth = saleRepository.countSales(startOfMonth, endOfMonth);
        BigDecimal revenueThisMonth = saleRepository.calculateTotalSales(startOfMonth, endOfMonth);
        BigDecimal profitThisMonth = saleRepository.calculateTotalProfit(startOfMonth, endOfMonth);
        
        // Expenses
        BigDecimal expensesToday = expenseRepository.calculateTotalExpenses(today, today);
        BigDecimal expensesThisMonth = expenseRepository.calculateTotalExpenses(startOfMonth, endOfMonth);
        
        // Net profit
        BigDecimal netProfitToday = (profitToday != null ? profitToday : BigDecimal.ZERO)
                .subtract(expensesToday != null ? expensesToday : BigDecimal.ZERO);
        BigDecimal netProfitThisMonth = (profitThisMonth != null ? profitThisMonth : BigDecimal.ZERO)
                .subtract(expensesThisMonth != null ? expensesThisMonth : BigDecimal.ZERO);
        
        // Returns
        long returnsToday = returnRepository.countReturns(today, today);
        long returnsThisMonth = returnRepository.countReturns(startOfMonth, endOfMonth);
        
        return DashboardResponse.builder()
                .totalDevices(totalDevices)
                .devicesInStock(devicesInStock)
                .devicesSold(devicesSold)
                .devicesUnderRepair(devicesUnderRepair)
                .totalSalesToday(totalSalesToday)
                .totalSalesThisMonth(totalSalesThisMonth)
                .revenueToday(revenueToday != null ? revenueToday : BigDecimal.ZERO)
                .revenueThisMonth(revenueThisMonth != null ? revenueThisMonth : BigDecimal.ZERO)
                .profitToday(profitToday != null ? profitToday : BigDecimal.ZERO)
                .profitThisMonth(profitThisMonth != null ? profitThisMonth : BigDecimal.ZERO)
                .expensesToday(expensesToday != null ? expensesToday : BigDecimal.ZERO)
                .expensesThisMonth(expensesThisMonth != null ? expensesThisMonth : BigDecimal.ZERO)
                .netProfitToday(netProfitToday)
                .netProfitThisMonth(netProfitThisMonth)
                .returnsToday(returnsToday)
                .returnsThisMonth(returnsThisMonth)
                .build();
    }
}
