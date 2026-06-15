package com.concept.crm.repository;

import com.concept.crm.entity.Lead;
import com.concept.crm.enums.LeadStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    List<Lead> findByStatus(LeadStatus status);
    List<Lead> findByAssignedToId(Long userId);
    List<Lead> findByContactId(Long contactId);
    List<Lead> findByActiveTrue();
    long countByStatus(LeadStatus status);
}
