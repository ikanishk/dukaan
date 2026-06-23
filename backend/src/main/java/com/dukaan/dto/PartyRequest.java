package com.dukaan.dto;

import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PartyRequest {
    @NotBlank(message = "Name is required")
    private String name;
    
    @NotBlank(message = "Mobile number is required")
    private String mobile;
    
    private String email;
    private String address;
    private String notes;
}
