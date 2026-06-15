package com.concept.crm.repository;

import com.concept.crm.entity.Task;
import com.concept.crm.enums.TaskPriority;
import com.concept.crm.enums.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findByAssignedToId(Long userId);
    List<Task> findByContactId(Long contactId);
    List<Task> findByOpportunityId(Long opportunityId);
    List<Task> findByDueDateBeforeAndStatusNot(LocalDateTime date, TaskStatus status);
    long countByStatus(TaskStatus status);
}
