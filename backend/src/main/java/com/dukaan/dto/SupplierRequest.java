package com.dukaan.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SupplierRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    private String mobile;
    private String email;
    private String address;
    private String gstNumber;
    private String notes;
}
