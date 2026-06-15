package com.concept.crm.repository;

import com.concept.crm.entity.Opportunity;
import com.concept.crm.enums.OpportunityStage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Long> {
    List<Opportunity> findByStage(OpportunityStage stage);
    List<Opportunity> findByAssignedToId(Long userId);
    List<Opportunity> findByAccountId(Long accountId);
    List<Opportunity> findByActiveTrue();
    long countByStage(OpportunityStage stage);

    @Query("SELECT COALESCE(SUM(o.amount), 0) FROM Opportunity o WHERE o.stage = :stage AND o.active = true")
    Double sumAmountByStage(OpportunityStage stage);
}
