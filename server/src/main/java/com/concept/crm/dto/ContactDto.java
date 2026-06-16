package com.concept.crm.dto;

import com.concept.crm.enums.ContactType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String mobile;
    private String jobTitle;
    private String department;
    private ContactType type;
    private String linkedinUrl;
    private String notes;
    private Long accountId;
    private String accountName;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
