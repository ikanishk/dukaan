package com.dukaan.repository;

import com.dukaan.entity.Return;
import com.dukaan.entity.enums.ReturnStatus;
import com.dukaan.entity.enums.ReturnType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReturnRepository extends JpaRepository<Return, Long> {
    List<Return> findByDeletedFalse();
    Page<Return> findByDeletedFalse(Pageable pageable);
    Page<Return> findByReturnTypeAndDeletedFalse(ReturnType returnType, Pageable pageable);
    Page<Return> findByStatusAndDeletedFalse(ReturnStatus status, Pageable pageable);
    
    @Query("SELECT r FROM Return r WHERE r.device.id = :deviceId AND r.deleted = false ORDER BY r.returnDate DESC")
    List<Return> findByDeviceId(@Param("deviceId") Long deviceId);
    
    @Query("SELECT COUNT(r) FROM Return r WHERE r.returnDate BETWEEN :startDate AND :endDate AND r.deleted = false")
    long countReturns(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
