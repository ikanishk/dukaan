package com.dukaan.util;

import com.dukaan.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class SkuGenerator {
    
    private final DeviceRepository deviceRepository;
    
    @Value("${app.sku.prefix:S}")
    private String skuPrefix;
    
    @Value("${app.sku.start-number:1}")
    private int startNumber;
    
    public synchronized String generateSku() {
        List<String> existingSkus = deviceRepository.findAllSkus(PageRequest.of(0, 1));
        
        if (existingSkus.isEmpty()) {
            return String.format("%s-%03d", skuPrefix, startNumber);
        }
        
        String lastSku = existingSkus.get(0);
        int lastNumber = extractNumber(lastSku);
        int nextNumber = lastNumber + 1;
        
        return String.format("%s-%03d", skuPrefix, nextNumber);
    }
    
    private int extractNumber(String sku) {
        try {
            String numberPart = sku.substring(sku.lastIndexOf('-') + 1);
            return Integer.parseInt(numberPart);
        } catch (Exception e) {
            return startNumber;
        }
    }
}
