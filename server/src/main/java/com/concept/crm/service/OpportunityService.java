package com.concept.crm.service;

import com.concept.crm.dto.OpportunityDto;
import com.concept.crm.entity.Opportunity;
import com.concept.crm.enums.OpportunityStage;
import com.concept.crm.repository.AccountRepository;
import com.concept.crm.repository.OpportunityRepository;
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
public class OpportunityService {

    private final OpportunityRepository opportunityRepository;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public List<OpportunityDto> findAll() {
        return opportunityRepository.findByActiveTrue().stream().map(this::toDto).toList();
    }

    public OpportunityDto findById(Long id) {
        return toDto(opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Opportunity not found: " + id)));
    }

    public List<OpportunityDto> findByStage(OpportunityStage stage) {
        return opportunityRepository.findByStage(stage).stream().map(this::toDto).toList();
    }

    public Map<String, Object> getPipelineSummary() {
        return Map.of(
                "totalWon", opportunityRepository.sumAmountByStage(OpportunityStage.CERRADO_GANADO),
                "totalInProgress", opportunityRepository.countByStage(OpportunityStage.PROPUESTA),
                "totalLost", opportunityRepository.countByStage(OpportunityStage.CERRADO_PERDIDO)
        );
    }

    @Transactional
    public OpportunityDto create(OpportunityDto dto) {
        Opportunity opp = toEntity(dto);
        return toDto(opportunityRepository.save(opp));
    }

    @Transactional
    public OpportunityDto update(Long id, OpportunityDto dto) {
        Opportunity opp = opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Opportunity not found: " + id));
        opp.setName(dto.getName());
        opp.setDescription(dto.getDescription());
        opp.setAmount(dto.getAmount());
        opp.setProbability(dto.getProbability());
        opp.setStage(dto.getStage());
        opp.setCloseDate(dto.getCloseDate());
        opp.setLostReason(dto.getLostReason());
        if (dto.getAccountId() != null) {
            opp.setAccount(accountRepository.findById(dto.getAccountId()).orElse(null));
        }
        if (dto.getAssignedToId() != null) {
            opp.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        return toDto(opportunityRepository.save(opp));
    }

    @Transactional
    public void delete(Long id) {
        Opportunity opp = opportunityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Opportunity not found: " + id));
        opp.setActive(false);
        opportunityRepository.save(opp);
    }

    private OpportunityDto toDto(Opportunity o) {
        return OpportunityDto.builder()
                .id(o.getId())
                .name(o.getName())
                .description(o.getDescription())
                .amount(o.getAmount())
                .probability(o.getProbability())
                .stage(o.getStage())
                .closeDate(o.getCloseDate())
                .closedAt(o.getClosedAt())
                .lostReason(o.getLostReason())
                .accountId(o.getAccount() != null ? o.getAccount().getId() : null)
                .accountName(o.getAccount() != null ? o.getAccount().getName() : null)
                .assignedToId(o.getAssignedTo() != null ? o.getAssignedTo().getId() : null)
                .assignedToName(o.getAssignedTo() != null ? o.getAssignedTo().getFirstName() + " " + o.getAssignedTo().getLastName() : null)
                .leadId(o.getLead() != null ? o.getLead().getId() : null)
                .active(o.isActive())
                .createdAt(o.getCreatedAt())
                .updatedAt(o.getUpdatedAt())
                .build();
    }

    private Opportunity toEntity(OpportunityDto dto) {
        Opportunity opp = new Opportunity();
        opp.setName(dto.getName());
        opp.setDescription(dto.getDescription());
        opp.setAmount(dto.getAmount());
        opp.setProbability(dto.getProbability());
        opp.setStage(dto.getStage() != null ? dto.getStage() : OpportunityStage.PROSPECTO);
        opp.setCloseDate(dto.getCloseDate());
        if (dto.getAccountId() != null) {
            opp.setAccount(accountRepository.findById(dto.getAccountId()).orElse(null));
        }
        if (dto.getAssignedToId() != null) {
            opp.setAssignedTo(userRepository.findById(dto.getAssignedToId()).orElse(null));
        }
        return opp;
    }
}
