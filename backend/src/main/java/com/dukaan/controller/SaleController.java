package com.dukaan.controller;

import com.dukaan.dto.SaleRequest;
import com.dukaan.dto.SaleResponse;
import com.dukaan.service.SaleService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    
    private final SaleService saleService;
    
    @PostMapping
    public ResponseEntity<SaleResponse> createSale(@Valid @RequestBody SaleRequest request) {
        SaleResponse response = saleService.createSale(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<SaleResponse> getSaleById(@PathVariable Long id) {
        SaleResponse response = saleService.getSaleById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/invoice/{invoiceNumber}")
    public ResponseEntity<SaleResponse> getSaleByInvoiceNumber(@PathVariable String invoiceNumber) {
        SaleResponse response = saleService.getSaleByInvoiceNumber(invoiceNumber);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<SaleResponse>> getAllSales(Pageable pageable) {
        Page<SaleResponse> response = saleService.getAllSales(pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<Page<SaleResponse>> getSalesByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Pageable pageable) {
        Page<SaleResponse> response = saleService.getSalesByDateRange(startDate, endDate, pageable);
        return ResponseEntity.ok(response);
    }
}
