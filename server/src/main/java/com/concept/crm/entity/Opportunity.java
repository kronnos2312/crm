package com.concept.crm.entity;

import com.concept.crm.enums.OpportunityStage;
import com.concept.crm.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "opportunities")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Opportunity extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "probability")
    private Integer probability;

    @Enumerated(EnumType.STRING)
    @Column(name = "stage", nullable = false)
    private OpportunityStage stage;

    @Column(name = "close_date")
    private LocalDateTime closeDate;

    @Column(name = "closed_at")
    private LocalDateTime closedAt;

    @Column(name = "lost_reason")
    private String lostReason;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id")
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id")
    private Lead lead;

    @OneToMany(mappedBy = "opportunity", fetch = FetchType.LAZY)
    private List<Activity> activities;
}
