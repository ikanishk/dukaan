package com.dukaan.service;

import com.dukaan.dto.ReturnRequest;
import com.dukaan.entity.Device;
import com.dukaan.entity.Return;
import com.dukaan.entity.Sale;
import com.dukaan.entity.enums.DeviceStatus;
import com.dukaan.entity.enums.LedgerEntryType;
import com.dukaan.entity.enums.ReturnStatus;
import com.dukaan.entity.enums.ReturnType;
import com.dukaan.exception.BusinessException;
import com.dukaan.exception.ResourceNotFoundException;
import com.dukaan.repository.DeviceRepository;
import com.dukaan.repository.ReturnRepository;
import com.dukaan.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ReturnService {
    
    private final ReturnRepository returnRepository;
    private final DeviceRepository deviceRepository;
    private final SaleRepository saleRepository;
    private final LedgerService ledgerService;
    private final AuditService auditService;
    
    @Transactional
    public Return createReturn(ReturnRequest request) {
        Device device = deviceRepository.findById(request.getDeviceId())
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        
        Sale sale = null;
        if (request.getSaleId() != null) {
            sale = saleRepository.findById(request.getSaleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Sale not found"));
            
            if (sale.getReturned()) {
                throw new BusinessException("This sale has already been returned");
            }
        }
        
        ReturnType returnType = ReturnType.valueOf(request.getReturnType());
        
        Return returnRecord = Return.builder()
                .device(device)
                .sale(sale)
                .returnType(returnType)
                .status(ReturnStatus.PENDING)
                .returnDate(request.getReturnDate())
                .reason(request.getReason())
                .refundAmount(request.getRefundAmount() != null ? request.getRefundAmount() : BigDecimal.ZERO)
                .notes(request.getNotes())
                .processed(false)
                .build();
        
        returnRecord = returnRepository.save(returnRecord);
        
        auditService.log("CREATE", "Return", returnRecord.getId(), null, 
                "Created return for device: " + device.getSku());
        
        return returnRecord;
    }
    
    @Transactional
    public Return processReturn(Long returnId) {
        Return returnRecord = returnRepository.findById(returnId)
                .orElseThrow(() -> new ResourceNotFoundException("Return not found"));
        
        if (returnRecord.getProcessed()) {
            throw new BusinessException("Return has already been processed");
        }
        
        Device device = returnRecord.getDevice();
        Sale sale = returnRecord.getSale();
        
        if (returnRecord.getReturnType() == ReturnType.CUSTOMER_RETURN && sale != null) {
            // Mark sale as returned
            sale.setReturned(true);
            sale.setReturnDate(returnRecord.getReturnDate());
            sale.setReturnReason(returnRecord.getReason());
            saleRepository.save(sale);
            
            // Create reversal ledger entry
            if (sale.getParty() != null) {
                ledgerService.createLedgerEntry(
                        sale.getParty().getId(),
                        LedgerEntryType.RETURN,
                        returnRecord.getReturnDate(),
                        BigDecimal.ZERO,
                        returnRecord.getRefundAmount(),
                        "Return: " + device.getSku() + " - " + returnRecord.getReason(),
                        "RET-" + returnRecord.getId(),
                        null,
                        returnRecord.getId()
                );
            }
            
            // Update device status
            device.setStatus(DeviceStatus.RETURNED_BY_CUSTOMER);
        } else if (returnRecord.getReturnType() == ReturnType.SUPPLIER_RETURN) {
            device.setStatus(DeviceStatus.RETURNED_TO_SUPPLIER);
        }
        
        deviceRepository.save(device);
        
        returnRecord.setProcessed(true);
        returnRecord.setProcessedDate(LocalDate.now());
        returnRecord.setStatus(ReturnStatus.REFUNDED);
        returnRecord = returnRepository.save(returnRecord);
        
        auditService.log("PROCESS", "Return", returnRecord.getId(), null, 
                "Processed return for device: " + device.getSku());
        
        return returnRecord;
    }
    
    @Transactional(readOnly = true)
    public Page<Return> getAllReturns(Pageable pageable) {
        return returnRepository.findByDeletedFalse(pageable);
    }
}
