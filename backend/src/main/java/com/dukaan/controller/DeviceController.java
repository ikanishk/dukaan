package com.dukaan.controller;

import com.dukaan.dto.DeviceRequest;
import com.dukaan.dto.DeviceResponse;
import com.dukaan.entity.enums.DeviceStatus;
import com.dukaan.service.DeviceService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/devices")
@RequiredArgsConstructor
public class DeviceController {
    
    private final DeviceService deviceService;
    
    @PostMapping
    public ResponseEntity<DeviceResponse> createDevice(@Valid @RequestBody DeviceRequest request) {
        DeviceResponse response = deviceService.createDevice(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DeviceResponse> getDeviceById(@PathVariable Long id) {
        DeviceResponse response = deviceService.getDeviceById(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/sku/{sku}")
    public ResponseEntity<DeviceResponse> getDeviceBySku(@PathVariable String sku) {
        DeviceResponse response = deviceService.getDeviceBySku(sku);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/imei/{imei}")
    public ResponseEntity<DeviceResponse> getDeviceByImei(@PathVariable String imei) {
        DeviceResponse response = deviceService.getDeviceByImei(imei);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<DeviceResponse>> getAllDevices(Pageable pageable) {
        Page<DeviceResponse> response = deviceService.getAllDevices(pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<Page<DeviceResponse>> getDevicesByStatus(
            @PathVariable DeviceStatus status, Pageable pageable) {
        Page<DeviceResponse> response = deviceService.getDevicesByStatus(status, pageable);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/search")
    public ResponseEntity<Page<DeviceResponse>> searchDevices(
            @RequestParam String query, Pageable pageable) {
        Page<DeviceResponse> response = deviceService.searchDevices(query, pageable);
        return ResponseEntity.ok(response);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<DeviceResponse> updateDevice(
            @PathVariable Long id, @Valid @RequestBody DeviceRequest request) {
        DeviceResponse response = deviceService.updateDevice(id, request);
        return ResponseEntity.ok(response);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> updateDeviceStatus(
            @PathVariable Long id, @RequestParam DeviceStatus status) {
        deviceService.updateDeviceStatus(id, status);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }
}
