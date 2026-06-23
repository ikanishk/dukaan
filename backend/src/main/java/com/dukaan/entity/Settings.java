package com.dukaan.entity;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "settings", indexes = {
    @Index(name = "idx_settings_key", columnList = "settingKey", unique = true)
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Settings extends BaseEntity {
    
    @Column(nullable = false, unique = true, length = 100)
    private String settingKey;
    
    @Column(nullable = false, columnDefinition = "TEXT")
    private String settingValue;
    
    @Column(length = 500)
    private String description;
    
    @Column(length = 50)
    private String category;
}
