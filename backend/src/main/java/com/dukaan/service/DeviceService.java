package com.dukaan.service;

import com.dukaan.dto.DeviceRequest;
import com.dukaan.dto.DeviceResponse;
import com.dukaan.entity.Device;
import com.dukaan.entity.Supplier;
import com.dukaan.entity.enums.DeviceStatus;
import com.dukaan.exception.DuplicateResourceException;
import com.dukaan.exception.ResourceNotFoundException;
import com.dukaan.repository.DeviceRepository;
import com.dukaan.repository.SupplierRepository;
import com.dukaan.util.BarcodeGenerator;
import com.dukaan.util.SkuGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceService {
    
    private final DeviceRepository deviceRepository;
    private final SupplierRepository supplierRepository;
    private final SkuGenerator skuGenerator;
    private final BarcodeGenerator barcodeGenerator;
    private final AuditService auditService;
    
    @Transactional
    public DeviceResponse createDevice(DeviceRequest request) {
        // Check for duplicate IMEI
        if (deviceRepository.existsByImei(request.getImei())) {
            throw new DuplicateResourceException("Device with IMEI " + request.getImei() + " already exists");
        }
        
        // Get supplier (optional)
        Supplier supplier = null;
        if (request.getSupplierId() != null) {
            supplier = supplierRepository.findById(request.getSupplierId()).orElse(null);
        }
        
        // Generate SKU
        String sku = skuGenerator.generateSku();
        
        // Create device
        Device device = Device.builder()
                .sku(sku)
                .imei(request.getImei())
                .model(request.getModel())
                .brand(request.getBrand())
                .color(request.getColor())
                .storage(request.getStorage())
                .ram(request.getRam())
                .supplier(supplier)
                .purchasePrice(request.getPurchasePrice())
                .status(DeviceStatus.IN_STOCK)
                .notes(request.getNotes())
                .condition(request.getCondition())
                .accessories(request.getAccessories())
                .build();
        
        // Generate barcode
        String barcodeData = barcodeGenerator.generateBarcode(sku);
        device.setBarcodeData(barcodeData);
        
        device = deviceRepository.save(device);
        
        auditService.log("CREATE", "Device", device.getId(), null, 
                "Created device: " + sku + " - " + request.getModel());
        
        return mapToResponse(device);
    }
    
    @Transactional(readOnly = true)
    public DeviceResponse getDeviceById(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        return mapToResponse(device);
    }
    
    @Transactional(readOnly = true)
    public DeviceResponse getDeviceBySku(String sku) {
        Device device = deviceRepository.findBySku(sku)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with SKU: " + sku));
        return mapToResponse(device);
    }
    
    @Transactional(readOnly = true)
    public DeviceResponse getDeviceByImei(String imei) {
        Device device = deviceRepository.findByImei(imei)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found with IMEI: " + imei));
        return mapToResponse(device);
    }
    
    @Transactional(readOnly = true)
    public Page<DeviceResponse> getAllDevices(Pageable pageable) {
        return deviceRepository.findByDeletedFalse(pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<DeviceResponse> getDevicesByStatus(DeviceStatus status, Pageable pageable) {
        return deviceRepository.findByStatusAndDeletedFalse(status, pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional(readOnly = true)
    public Page<DeviceResponse> searchDevices(String search, Pageable pageable) {
        return deviceRepository.searchDevices(search, pageable)
                .map(this::mapToResponse);
    }
    
    @Transactional
    public DeviceResponse updateDevice(Long id, DeviceRequest request) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        
        String oldValue = device.toString();
        
        // Update fields (SKU and IMEI are immutable)
        device.setModel(request.getModel());
        device.setBrand(request.getBrand());
        device.setColor(request.getColor());
        device.setStorage(request.getStorage());
        device.setRam(request.getRam());
        device.setNotes(request.getNotes());
        device.setCondition(request.getCondition());
        device.setAccessories(request.getAccessories());
        
        device = deviceRepository.save(device);
        
        auditService.log("UPDATE", "Device", device.getId(), oldValue, device.toString());
        
        return mapToResponse(device);
    }
    
    @Transactional
    public void updateDeviceStatus(Long id, DeviceStatus status) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        
        DeviceStatus oldStatus = device.getStatus();
        device.setStatus(status);
        deviceRepository.save(device);
        
        auditService.log("STATUS_CHANGE", "Device", device.getId(), 
                oldStatus.name(), status.name());
    }
    
    @Transactional
    public void deleteDevice(Long id) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        
        device.setDeleted(true);
        deviceRepository.save(device);
        
        auditService.log("DELETE", "Device", device.getId(), null, "Soft deleted device");
    }
    
    private DeviceResponse mapToResponse(Device device) {
        return DeviceResponse.builder()
                .id(device.getId())
                .sku(device.getSku())
                .imei(device.getImei())
                .model(device.getModel())
                .brand(device.getBrand())
                .color(device.getColor())
                .storage(device.getStorage())
                .ram(device.getRam())
                .supplierId(device.getSupplier().getId())
                .supplierName(device.getSupplier().getName())
                .purchasePrice(device.getPurchasePrice())
                .status(device.getStatus().name())
                .barcodeData(device.getBarcodeData())
                .notes(device.getNotes())
                .condition(device.getCondition())
                .accessories(device.getAccessories())
                .createdAt(device.getCreatedAt())
                .updatedAt(device.getUpdatedAt())
                .build();
    }
}
