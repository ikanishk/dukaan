package com.dukaan.entity;

import javax.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "parties", indexes = {
    @Index(name = "idx_party_name", columnList = "name"),
    @Index(name = "idx_party_mobile", columnList = "mobile")
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Party extends BaseEntity {
    
    @Column(nullable = false, length = 100)
    private String name;
    
    @Column(nullable = false, length = 15)
    private String mobile;
    
    @Column(length = 100)
    private String email;
    
    @Column(length = 500)
    private String address;
    
    @Column(precision = 15, scale = 2)
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
    
    @Column(length = 1000)
    private String notes;
    
    @Column(nullable = false)
    private Boolean active = true;
}
