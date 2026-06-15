package com.concept.crm.service;

import com.concept.crm.dto.TaskDto;
import com.concept.crm.entity.Task;
import com.concept.crm.enums.TaskStatus;
import com.concept.crm.repository.ContactRepository;
import com.concept.crm.repository.OpportunityRepository;
import com.concept.crm.repository.TaskRepository;
import com.concept.crm.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ContactRepository contactRepository;
    private final OpportunityRepository opportunityRepository;

    public List<TaskDto> findAll() {
        return taskRepository.findAll().stream().map(this::toDto).toList();
    }

    public TaskDto findById(Long id) {
        return toDto(taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id)));
    }

    public List<TaskDto> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    public List<TaskDto> findByAssignedUser(Long userId) {
        return taskRepository.findByAssignedToId(userId).stream().map(this::toDto).toList();
    }

    public List<TaskDto> findOverdue() {
        return taskRepository.findByDueDateBeforeAndStatusNot(LocalDateTime.now(), TaskStatus.COMPLETADA)
                .stream().map(this::toDto).toList();
    }

    public Map<String, Long> getStatusCounts() {
        return Map.of(
                "PENDIENTE", taskRepository.countByStatus(TaskStatus.PENDIENTE),
                "EN_PROGRESO", taskRepository.countByStatus(TaskStatus.EN_PROGRESO),
                "COMPLETADA", taskRepository.countByStatus(TaskStatus.COMPLETADA)
        );
    }

    @Transactional
    public TaskDto create(TaskDto dto) {
        Task task = toEntity(dto);
        return toDto(taskRepository.save(task));
    }

    @Transactional
    public TaskDto update(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found: " + id));
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus());
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        if (dto.getStatus() == TaskStatus.COMPLETADA && task.getCompletedAt() == null) {
            task.setCompletedAt(LocalDateTime.now());
        }
        if (dto.getAssignedToId() != null) {
            task.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        return toDto(taskRepository.save(task));
    }

    @Transactional
    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDto toDto(Task t) {
        return TaskDto.builder()
                .id(t.getId())
                .title(t.getTitle())
                .description(t.getDescription())
                .status(t.getStatus())
                .priority(t.getPriority())
                .dueDate(t.getDueDate())
                .completedAt(t.getCompletedAt())
                .assignedToId(t.getAssignedTo() != null ? t.getAssignedTo().getId() : null)
                .assignedToName(t.getAssignedTo() != null ? t.getAssignedTo().getFirstName() + " " + t.getAssignedTo().getLastName() : null)
                .createdById(t.getCreatedBy() != null ? t.getCreatedBy().getId() : null)
                .contactId(t.getContact() != null ? t.getContact().getId() : null)
                .contactName(t.getContact() != null ? t.getContact().getFirstName() + " " + t.getContact().getLastName() : null)
                .opportunityId(t.getOpportunity() != null ? t.getOpportunity().getId() : null)
                .opportunityName(t.getOpportunity() != null ? t.getOpportunity().getName() : null)
                .createdAt(t.getCreatedAt())
                .updatedAt(t.getUpdatedAt())
                .build();
    }

    private Task toEntity(TaskDto dto) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setStatus(dto.getStatus() != null ? dto.getStatus() : TaskStatus.PENDIENTE);
        task.setPriority(dto.getPriority());
        task.setDueDate(dto.getDueDate());
        if (dto.getAssignedToId() != null) {
            task.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        if (dto.getCreatedById() != null) {
            task.setCreatedBy(userRepository.findById(dto.getCreatedById()).orElse(null));
        }
        if (dto.getContactId() != null) {
            task.setContact(contactRepository.findById(dto.getContactId()).orElse(null));
        }
        if (dto.getOpportunityId() != null) {
            task.setOpportunity(opportunityRepository.findById(dto.getOpportunityId()).orElse(null));
        }
        return task;
    }
}
