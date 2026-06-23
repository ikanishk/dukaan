package com.dukaan.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "suppliers", indexes = {
    @Index(name = "idx_supplier_name", columnList = "name"),
    @Index(name = "idx_supplier_mobile", columnList = "mobile")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier extends BaseEntity {
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(length = 15)
    private String mobile;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 500)
    private String address;
    
    @Column(length = 50)
    private String gstNumber;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private Boolean active = true;
}
