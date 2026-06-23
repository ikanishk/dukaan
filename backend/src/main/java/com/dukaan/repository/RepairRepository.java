package com.dukaan.repository;

import com.dukaan.entity.Repair;
import com.dukaan.entity.enums.RepairStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepairRepository extends JpaRepository<Repair, Long> {
    List<Repair> findByDeletedFalse();
    Page<Repair> findByDeletedFalse(Pageable pageable);
    Page<Repair> findByStatusAndDeletedFalse(RepairStatus status, Pageable pageable);
    
    @Query("SELECT r FROM Repair r WHERE r.device.id = :deviceId AND r.deleted = false ORDER BY r.repairDate DESC")
    List<Repair> findByDeviceId(@Param("deviceId") Long deviceId);
    
    @Query("SELECT SUM(r.repairCost) FROM Repair r WHERE r.repairDate BETWEEN :startDate AND :endDate AND r.deleted = false")
    BigDecimal calculateTotalRepairCost(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
