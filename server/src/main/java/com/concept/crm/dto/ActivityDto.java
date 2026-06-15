package com.concept.crm.dto;

import com.concept.crm.enums.ActivityType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ActivityDto {
    private Long id;
    private String subject;
    private String description;
    private ActivityType type;
    private LocalDateTime scheduledAt;
    private LocalDateTime completedAt;
    private Integer durationMinutes;
    private String outcome;
    private Long contactId;
    private String contactName;
    private Long opportunityId;
    private String opportunityName;
    private Long createdById;
    private String createdByName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
