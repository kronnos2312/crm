package com.concept.crm.service;

import com.concept.crm.dto.LeadDto;
import com.concept.crm.entity.Lead;
import com.concept.crm.enums.LeadStatus;
import com.concept.crm.repository.ContactRepository;
import com.concept.crm.repository.LeadRepository;
import com.concept.crm.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LeadService {

    private final LeadRepository leadRepository;
    private final ContactRepository contactRepository;
    private final UserRepository userRepository;

    public List<LeadDto> findAll() {
        return leadRepository.findByActiveTrue().stream().map(this::toDto).toList();
    }

    public LeadDto findById(Long id) {
        return toDto(leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found: " + id)));
    }

    public List<LeadDto> findByStatus(LeadStatus status) {
        return leadRepository.findByStatus(status).stream().map(this::toDto).toList();
    }

    public List<LeadDto> findByAssignedUser(Long userId) {
        return leadRepository.findByAssignedToId(userId).stream().map(this::toDto).toList();
    }

    public Map<String, Long> getStatusCounts() {
        return Map.of(
                "NUEVO", leadRepository.countByStatus(LeadStatus.NUEVO),
                "CONTACTADO", leadRepository.countByStatus(LeadStatus.CONTACTADO),
                "CALIFICADO", leadRepository.countByStatus(LeadStatus.CALIFICADO),
                "CONVERTIDO", leadRepository.countByStatus(LeadStatus.CONVERTIDO)
        );
    }

    @Transactional
    public LeadDto create(LeadDto dto) {
        Lead lead = toEntity(dto);
        return toDto(leadRepository.save(lead));
    }

    @Transactional
    public LeadDto update(Long id, LeadDto dto) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found: " + id));
        lead.setTitle(dto.getTitle());
        lead.setDescription(dto.getDescription());
        lead.setSource(dto.getSource());
        lead.setEstimatedValue(dto.getEstimatedValue());
        lead.setStatus(dto.getStatus());
        lead.setExpectedCloseDate(dto.getExpectedCloseDate());
        if (dto.getContactId() != null) {
            lead.setContact(contactRepository.findById(dto.getContactId()).orElse(null));
        }
        if (dto.getAssignedToId() != null) {
            lead.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        return toDto(leadRepository.save(lead));
    }

    @Transactional
    public void delete(Long id) {
        Lead lead = leadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Lead not found: " + id));
        lead.setActive(false);
        leadRepository.save(lead);
    }

    private LeadDto toDto(Lead l) {
        return LeadDto.builder()
                .id(l.getId())
                .title(l.getTitle())
                .description(l.getDescription())
                .source(l.getSource())
                .estimatedValue(l.getEstimatedValue())
                .status(l.getStatus())
                .expectedCloseDate(l.getExpectedCloseDate())
                .convertedAt(l.getConvertedAt())
                .contactId(l.getContact() != null ? l.getContact().getId() : null)
                .contactName(l.getContact() != null ? l.getContact().getFirstName() + " " + l.getContact().getLastName() : null)
                .assignedToId(l.getAssignedTo() != null ? l.getAssignedTo().getId() : null)
                .assignedToName(l.getAssignedTo() != null ? l.getAssignedTo().getFirstName() + " " + l.getAssignedTo().getLastName() : null)
                .active(l.isActive())
                .createdAt(l.getCreatedAt())
                .updatedAt(l.getUpdatedAt())
                .build();
    }

    private Lead toEntity(LeadDto dto) {
        Lead lead = new Lead();
        lead.setTitle(dto.getTitle());
        lead.setDescription(dto.getDescription());
        lead.setSource(dto.getSource());
        lead.setEstimatedValue(dto.getEstimatedValue());
        lead.setStatus(dto.getStatus() != null ? dto.getStatus() : LeadStatus.NUEVO);
        lead.setExpectedCloseDate(dto.getExpectedCloseDate());
        if (dto.getContactId() != null) {
            lead.setContact(contactRepository.findById(dto.getContactId()).orElse(null));
        }
        if (dto.getAssignedToId() != null) {
            lead.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        return lead;
    }
}
