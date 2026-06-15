package com.concept.crm.entity;

import com.concept.crm.enums.UserRole;
import com.concept.crm.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Column(name = "phone")
    private String phone;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<Lead> assignedLeads;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<Opportunity> assignedOpportunities;

    @OneToMany(mappedBy = "assignedTo", fetch = FetchType.LAZY)
    private List<Task> assignedTasks;
}
