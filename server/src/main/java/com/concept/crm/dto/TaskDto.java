package com.concept.crm.dto;

import com.concept.crm.enums.TaskPriority;
import com.concept.crm.enums.TaskStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime dueDate;
    private LocalDateTime completedAt;
    private Long assignedToId;
    private String assignedToName;
    private Long createdById;
    private Long contactId;
    private String contactName;
    private Long opportunityId;
    private String opportunityName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
