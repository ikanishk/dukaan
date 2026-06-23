package com.dukaan.repository;

import com.dukaan.entity.Device;
import com.dukaan.entity.enums.DeviceStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    Optional<Device> findBySku(String sku);
    Optional<Device> findByImei(String imei);
    boolean existsBySku(String sku);
    boolean existsByImei(String imei);
    List<Device> findByStatus(DeviceStatus status);
    List<Device> findByDeletedFalse();
    Page<Device> findByDeletedFalse(Pageable pageable);
    Page<Device> findByStatusAndDeletedFalse(DeviceStatus status, Pageable pageable);
    
    @Query("SELECT d FROM Device d WHERE d.deleted = false AND " +
           "(LOWER(d.sku) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.imei) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.model) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
           "LOWER(d.brand) LIKE LOWER(CONCAT('%', :search, '%')))")
    Page<Device> searchDevices(@Param("search") String search, Pageable pageable);
    
    @Query("SELECT COUNT(d) FROM Device d WHERE d.status = :status AND d.deleted = false")
    long countByStatus(@Param("status") DeviceStatus status);
    
    @Query("SELECT d.sku FROM Device d WHERE d.deleted = false ORDER BY d.id DESC")
    List<String> findAllSkus(Pageable pageable);
}
