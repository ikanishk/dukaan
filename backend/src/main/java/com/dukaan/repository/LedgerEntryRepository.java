package com.dukaan.repository;

import com.dukaan.entity.LedgerEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LedgerEntryRepository extends JpaRepository<LedgerEntry, Long> {
    List<LedgerEntry> findByDeletedFalse();
    
    @Query("SELECT l FROM LedgerEntry l WHERE l.party.id = :partyId AND l.deleted = false ORDER BY l.entryDate ASC, l.id ASC")
    List<LedgerEntry> findByPartyIdOrderByEntryDate(@Param("partyId") Long partyId);
    
    @Query("SELECT l FROM LedgerEntry l WHERE l.party.id = :partyId AND l.entryDate BETWEEN :startDate AND :endDate AND l.deleted = false ORDER BY l.entryDate ASC")
    List<LedgerEntry> findByPartyIdAndDateRange(@Param("partyId") Long partyId, 
                                                  @Param("startDate") LocalDate startDate, 
                                                  @Param("endDate") LocalDate endDate);
    
    Page<LedgerEntry> findByPartyIdAndDeletedFalse(Long partyId, Pageable pageable);
}
