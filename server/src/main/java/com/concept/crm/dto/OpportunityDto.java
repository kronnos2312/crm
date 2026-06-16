package com.concept.crm.dto;

import com.concept.crm.enums.OpportunityStage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpportunityDto {
    private Long id;
    private String name;
    private String description;
    private Double amount;
    private Integer probability;
    private OpportunityStage stage;
    private LocalDateTime closeDate;
    private LocalDateTime closedAt;
    private String lostReason;
    private Long accountId;
    private String accountName;
    private Long assignedToId;
    private String assignedToName;
    private Long leadId;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
