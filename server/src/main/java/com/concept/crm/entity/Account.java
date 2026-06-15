package com.concept.crm.entity;

import com.concept.crm.shared.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "industry")
    private String industry;

    @Column(name = "website")
    private String website;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "employees")
    private Integer employees;

    @Column(name = "annual_revenue")
    private Double annualRevenue;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Contact> contacts;

    @OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
    private List<Opportunity> opportunities;
}
