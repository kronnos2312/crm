package com.concept.crm.repository;

import com.concept.crm.entity.Activity;
import com.concept.crm.enums.ActivityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByType(ActivityType type);
    List<Activity> findByContactId(Long contactId);
    List<Activity> findByOpportunityId(Long opportunityId);
    List<Activity> findByCreatedById(Long userId);
    List<Activity> findByScheduledAtBetween(LocalDateTime from, LocalDateTime to);
    List<Activity> findByCompletedAtIsNull();
}
