package com.dukaan.repository;

import com.dukaan.entity.Sale;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface SaleRepository extends JpaRepository<Sale, Long> {
    Optional<Sale> findByInvoiceNumber(String invoiceNumber);
    List<Sale> findByDeletedFalse();
    Page<Sale> findByDeletedFalse(Pageable pageable);
    Page<Sale> findBySaleDateBetweenAndDeletedFalse(LocalDate startDate, LocalDate endDate, Pageable pageable);
    
    @Query("SELECT s FROM Sale s WHERE s.party.id = :partyId AND s.deleted = false ORDER BY s.saleDate DESC")
    List<Sale> findByPartyId(@Param("partyId") Long partyId);
    
    @Query("SELECT s FROM Sale s WHERE s.device.id = :deviceId AND s.deleted = false")
    Optional<Sale> findByDeviceId(@Param("deviceId") Long deviceId);
    
    @Query("SELECT SUM(s.profit) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate AND s.returned = false AND s.deleted = false")
    BigDecimal calculateTotalProfit(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT SUM(s.salePrice) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate AND s.returned = false AND s.deleted = false")
    BigDecimal calculateTotalSales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT COUNT(s) FROM Sale s WHERE s.saleDate BETWEEN :startDate AND :endDate AND s.returned = false AND s.deleted = false")
    long countSales(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT s.invoiceNumber FROM Sale s WHERE s.deleted = false ORDER BY s.id DESC")
    List<String> findAllInvoiceNumbers(Pageable pageable);
}
