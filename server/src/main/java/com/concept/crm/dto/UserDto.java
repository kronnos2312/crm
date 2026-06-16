package com.concept.crm.dto;

import com.concept.crm.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private String avatarUrl;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
