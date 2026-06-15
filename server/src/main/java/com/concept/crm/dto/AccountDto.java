package com.concept.crm.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class AccountDto {
    private Long id;
    private String name;
    private String industry;
    private String website;
    private String phone;
    private String email;
    private String address;
    private String city;
    private String country;
    private Integer employees;
    private Double annualRevenue;
    private String description;
    private boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
