package com.concept.crm.repository;

import com.concept.crm.entity.Contact;
import com.concept.crm.enums.ContactType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {
    Optional<Contact> findByEmail(String email);
    List<Contact> findByType(ContactType type);
    List<Contact> findByAccountId(Long accountId);
    List<Contact> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);
    List<Contact> findByActiveTrue();
}
