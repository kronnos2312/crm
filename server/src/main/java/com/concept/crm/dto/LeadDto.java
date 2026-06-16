package com.concept.crm.dto;

import com.concept.crm.enums.LeadStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LeadDto {
    private Long id;
    private String title;
    private String description;
    private String source;
    private Double estimatedValue;
    private LeadStatus status;
    private LocalDateTime expectedCloseDate;
    private LocalDateTime convertedAt;
    private Long contactId;
    private String contactName;
    private Long assignedToId;
    private String assignedToName;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
