package com.dukaan.service;

import com.dukaan.entity.LedgerEntry;
import com.dukaan.entity.Party;
import com.dukaan.entity.Return;
import com.dukaan.entity.Sale;
import com.dukaan.entity.enums.LedgerEntryType;
import com.dukaan.exception.ResourceNotFoundException;
import com.dukaan.repository.LedgerEntryRepository;
import com.dukaan.repository.PartyRepository;
import com.dukaan.repository.ReturnRepository;
import com.dukaan.repository.SaleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LedgerService {
    
    private final LedgerEntryRepository ledgerEntryRepository;
    private final PartyRepository partyRepository;
    private final SaleRepository saleRepository;
    private final ReturnRepository returnRepository;
    private final AuditService auditService;
    
    @Transactional
    public LedgerEntry createLedgerEntry(Long partyId, LedgerEntryType entryType, LocalDate entryDate,
                                         BigDecimal debitAmount, BigDecimal creditAmount, String description,
                                         String referenceNumber, Long saleId, Long returnId) {
        Party party = partyRepository.findById(partyId)
                .orElseThrow(() -> new ResourceNotFoundException("Party not found"));
        
        // Get last balance
        List<LedgerEntry> lastEntries = ledgerEntryRepository.findByPartyIdOrderByEntryDate(partyId);
        BigDecimal lastBalance = lastEntries.isEmpty() ? BigDecimal.ZERO : 
                lastEntries.get(lastEntries.size() - 1).getBalance();
        
        // Calculate new balance
        BigDecimal debit = debitAmount != null ? debitAmount : BigDecimal.ZERO;
        BigDecimal credit = creditAmount != null ? creditAmount : BigDecimal.ZERO;
        BigDecimal newBalance = lastBalance.add(debit).subtract(credit);
        
        // Get related entities
        Sale sale = saleId != null ? saleRepository.findById(saleId).orElse(null) : null;
        Return returnRecord = returnId != null ? returnRepository.findById(returnId).orElse(null) : null;
        
        // Create ledger entry
        LedgerEntry entry = LedgerEntry.builder()
                .party(party)
                .entryType(entryType)
                .entryDate(entryDate)
                .debit(debit)
                .credit(credit)
                .balance(newBalance)
                .description(description)
                .referenceNumber(referenceNumber)
                .sale(sale)
                .returnRecord(returnRecord)
                .build();
        
        entry = ledgerEntryRepository.save(entry);
        
        // Update party outstanding balance
        party.setOutstandingBalance(newBalance);
        partyRepository.save(party);
        
        auditService.log("CREATE", "LedgerEntry", entry.getId(), null, 
                "Created ledger entry for party: " + party.getName());
        
        return entry;
    }
    
    @Transactional(readOnly = true)
    public List<LedgerEntry> getLedgerEntriesByParty(Long partyId) {
        return ledgerEntryRepository.findByPartyIdOrderByEntryDate(partyId);
    }
    
    @Transactional(readOnly = true)
    public List<LedgerEntry> getLedgerEntriesByPartyAndDateRange(Long partyId, LocalDate startDate, LocalDate endDate) {
        return ledgerEntryRepository.findByPartyIdAndDateRange(partyId, startDate, endDate);
    }
}
