package com.concept.crm.service;

import com.concept.crm.dto.ActivityDto;
import com.concept.crm.entity.Activity;
import com.concept.crm.repository.ActivityRepository;
import com.concept.crm.repository.ContactRepository;
import com.concept.crm.repository.OpportunityRepository;
import com.concept.crm.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final ContactRepository contactRepository;
    private final OpportunityRepository opportunityRepository;
    private final UserRepository userRepository;

    public List<ActivityDto> findAll() {
        return activityRepository.findAll().stream().map(this::toDto).toList();
    }

    public ActivityDto findById(Long id) {
        return toDto(activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found: " + id)));
    }

    public List<ActivityDto> findByContact(Long contactId) {
        return activityRepository.findByContactId(contactId).stream().map(this::toDto).toList();
    }

    public List<ActivityDto> findByOpportunity(Long opportunityId) {
        return activityRepository.findByOpportunityId(opportunityId).stream().map(this::toDto).toList();
    }

    public List<ActivityDto> findPending() {
        return activityRepository.findByCompletedAtIsNull().stream().map(this::toDto).toList();
    }

    public List<ActivityDto> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return activityRepository.findByScheduledAtBetween(from, to).stream().map(this::toDto).toList();
    }

    @Transactional
    public ActivityDto create(ActivityDto dto) {
        Activity activity = toEntity(dto);
        return toDto(activityRepository.save(activity));
    }

    @Transactional
    public ActivityDto update(Long id, ActivityDto dto) {
        Activity activity = activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found: " + id));
        activity.setSubject(dto.getSubject());
        activity.setDescription(dto.getDescription());
        activity.setType(dto.getType());
        activity.setScheduledAt(dto.getScheduledAt());
        activity.setCompletedAt(dto.getCompletedAt());
        activity.setDurationMinutes(dto.getDurationMinutes());
        activity.setOutcome(dto.getOutcome());
        return toDto(activityRepository.save(activity));
    }

    @Transactional
    public void delete(Long id) {
        activityRepository.deleteById(id);
    }

    private ActivityDto toDto(Activity a) {
        return ActivityDto.builder()
                .id(a.getId())
                .subject(a.getSubject())
                .description(a.getDescription())
                .type(a.getType())
                .scheduledAt(a.getScheduledAt())
                .completedAt(a.getCompletedAt())
                .durationMinutes(a.getDurationMinutes())
                .outcome(a.getOutcome())
                .contactId(a.getContact() != null ? a.getContact().getId() : null)
                .contactName(a.getContact() != null ? a.getContact().getFirstName() + " " + a.getContact().getLastName() : null)
                .opportunityId(a.getOpportunity() != null ? a.getOpportunity().getId() : null)
                .opportunityName(a.getOpportunity() != null ? a.getOpportunity().getName() : null)
                .createdById(a.getCreatedBy() != null ? a.getCreatedBy().getId() : null)
                .createdByName(a.getCreatedBy() != null ? a.getCreatedBy().getFirstName() + " " + a.getCreatedBy().getLastName() : null)
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }

    private Activity toEntity(ActivityDto dto) {
        Activity activity = new Activity();
        activity.setSubject(dto.getSubject());
        activity.setDescription(dto.getDescription());
        activity.setType(dto.getType());
        activity.setScheduledAt(dto.getScheduledAt());
        activity.setDurationMinutes(dto.getDurationMinutes());
        if (dto.getContactId() != null) {
            activity.setContact(contactRepository.findById(dto.getContactId()).orElse(null));
        }
        if (dto.getOpportunityId() != null) {
            activity.setOpportunity(opportunityRepository.findById(dto.getOpportunityId()).orElse(null));
        }
        if (dto.getCreatedById() != null) {
            activity.setCreatedBy(userRepository.findById(dto.getCreatedById()).orElse(null));
        }
        return activity;
    }
}
