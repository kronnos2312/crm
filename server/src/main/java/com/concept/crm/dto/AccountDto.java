package com.concept.crm.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
