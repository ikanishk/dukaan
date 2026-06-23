package com.dukaan.controller;

import com.dukaan.dto.ReturnRequest;
import com.dukaan.entity.Return;
import com.dukaan.service.ReturnService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/returns")
@RequiredArgsConstructor
public class ReturnController {
    
    private final ReturnService returnService;
    
    @PostMapping
    public ResponseEntity<Return> createReturn(@Valid @RequestBody ReturnRequest request) {
        Return response = returnService.createReturn(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    @PostMapping("/{id}/process")
    public ResponseEntity<Return> processReturn(@PathVariable Long id) {
        Return response = returnService.processReturn(id);
        return ResponseEntity.ok(response);
    }
    
    @GetMapping
    public ResponseEntity<Page<Return>> getAllReturns(Pageable pageable) {
        Page<Return> response = returnService.getAllReturns(pageable);
        return ResponseEntity.ok(response);
    }
}
