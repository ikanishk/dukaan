package com.dukaan.util;

import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class InvoiceGenerator {
    
    private static final String PREFIX = "INV";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");
    
    public synchronized String generateInvoiceNumber() {
        String datePart = LocalDate.now().format(DATE_FORMATTER);
        long timestamp = System.currentTimeMillis() % 100000;
        return String.format("%s-%s-%05d", PREFIX, datePart, timestamp);
    }
}
