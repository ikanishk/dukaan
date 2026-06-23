package com.dukaan.service;

import com.dukaan.dto.SaleRequest;
import com.dukaan.dto.SaleResponse;
import com.dukaan.entity.Device;
import com.dukaan.entity.Party;
import com.dukaan.entity.Sale;
import com.dukaan.entity.enums.DeviceStatus;
import com.dukaan.entity.enums.LedgerEntryType;
import com.dukaan.exception.BusinessException;
import com.dukaan.exception.ResourceNotFoundException;
import com.dukaan.repository.DeviceRepository;
import com.dukaan.repository.PartyRepository;
import com.dukaan.repository.SaleRepository;
import com.dukaan.util.InvoiceGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaleService {
    
    private final SaleRepository saleRepository;
    private final DeviceRepository deviceRepository;
    private final PartyRepository partyRepository;
    private final LedgerService ledgerService;
    private final InvoiceGenerator invoiceGenerator;
    private final AuditService auditService;
    
    @Transactional
    public SaleResponse createSale(SaleRequest request) {
        // Get device
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        
        // Validate device status
        if (device.getStatus() != DeviceStatus.IN_STOCK) {
            throw new BusinessException("Device is not available for sale. Current status: " + device.getStatus());
        }
        
        // Get party if provided
        Party party = null;
        if (request.getPartyId() != null) {
            party = partyRepository.findById(request.getPartyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        }
        
        // Calculate profit
        BigDecimal profit = request.getSalePrice().subtract(device.getPurchasePrice());
        
        // Calculate amount due
        BigDecimal amountDue = request.getSalePrice().subtract(request.getAmountPaid());
        
        // Generate invoice number
        String invoiceNumber = invoiceGenerator.generateInvoiceNumber();
        
        // Create sale
        Sale sale = Sale.builder()
                .invoiceNumber(invoiceNumber)
                .device(device)
                .party(party)
                .salePrice(request.getSalePrice())
                .purchasePrice(device.getPurchasePrice())
                .profit(profit)
                .saleDate(request.getSaleDate())
                .paymentMode(request.getPaymentMode())
                .amountPaid(request.getAmountPaid())
                .amountDue(amountDue)
                .notes(request.getNotes())
                .returned(false)
                .build();
        
        sale = saleRepository.save(sale);
        
        // Update device status
        device.setStatus(DeviceStatus.SOLD);
        deviceRepository.save(device);
        
        // Create ledger entry if party is provided
        if (party != null) {
            ledgerService.createLedgerEntry(
                    party.getId(),
                    LedgerEntryType.SALE,
                    request.getSaleDate(),
                    request.getSalePrice(),
                    request.getAmountPaid(),
                    "Sale: " + device.getSku() + " - " + device.getModel(),
                    invoiceNumber,
                    sale.getId(),
                    null
            );
        }
        
        auditService.log("CREATE", "Sale", sale.getId(), null, 
                "Created sale: " + invoiceNumber + " for device " + device.getSku());
        
        return mapToResponse(sale);
    }
    
    @Transactional(readOnly = true)
    public SaleResponse getSaleById(Long id) {
        Sale sale = saleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
        return mapToResponse(sale);
    }
    
    @Transactional(readOnly = true)
    public SaleResponse getSaleByInvoiceNumber(String invoiceNumber) {
        Sale sale = saleRepository.findByInvoiceNumber(invoiceNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Sale not found with invoice: " + invoiceNumber));
        return mapToResponse(sale);
    }
    
    @Transactional(readOnly = true)
    public Page<SaleResponse> getAllSales(Pageable pageable) {
        return saleRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<SaleResponse> getSalesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return saleRepository.findBySaleDateBetweenAndDeletedFalse(startDate, endDate, pageable)
                .map(this::mapToResponse);
    }
    
    private SaleResponse mapToResponse(Sale sale) {
        return SaleResponse.builder()
                .id(sale.getId())
                .invoiceNumber(sale.getInvoiceNumber())
                .deviceId(sale.getDevice().getId())
                .deviceSku(sale.getDevice().getSku())
                .deviceModel(sale.getDevice().getModel())
                .partyId(sale.getParty() != null ? sale.getParty().getId() : null)
                .partyName(sale.getParty() != null ? sale.getParty().getName() : null)
                .salePrice(sale.getSalePrice())
                .purchasePrice(sale.getPurchasePrice())
                .profit(sale.getProfit())
                .saleDate(sale.getSaleDate())
                .paymentMode(sale.getPaymentMode())
                .amountPaid(sale.getAmountPaid())
                .amountDue(sale.getAmountDue())
                .notes(sale.getNotes())
                .returned(sale.getReturned())
                .returnDate(sale.getReturnDate())
                .returnReason(sale.getReturnReason())
                .createdAt(sale.getCreatedAt())
                .build();
    }
}
