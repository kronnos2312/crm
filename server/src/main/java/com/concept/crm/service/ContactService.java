package com.concept.crm.service;

import com.concept.crm.dto.ContactDto;
import com.concept.crm.entity.Contact;
import com.concept.crm.enums.ContactType;
import com.concept.crm.repository.AccountRepository;
import com.concept.crm.repository.ContactRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContactService {

    private final ContactRepository contactRepository;
    private final AccountRepository accountRepository;

    public List<ContactDto> findAll() {
        return contactRepository.findByActiveTrue().stream().map(this::toDto).toList();
    }

    public ContactDto findById(Long id) {
        return toDto(contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + id)));
    }

    public List<ContactDto> findByType(ContactType type) {
        return contactRepository.findByType(type).stream().map(this::toDto).toList();
    }

    public List<ContactDto> search(String query) {
        return contactRepository.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(query, query)
                .stream().map(this::toDto).toList();
    }

    @Transactional
    public ContactDto create(ContactDto dto) {
        Contact contact = toEntity(dto);
        return toDto(contactRepository.save(contact));
    }

    @Transactional
    public ContactDto update(Long id, ContactDto dto) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + id));
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        contact.setMobile(dto.getMobile());
        contact.setJobTitle(dto.getJobTitle());
        contact.setDepartment(dto.getDepartment());
        contact.setType(dto.getType());
        contact.setLinkedinUrl(dto.getLinkedinUrl());
        contact.setNotes(dto.getNotes());
        if (dto.getAccountId() != null) {
            contact.setAccount(accountRepository.findById(dto.getAccountId()).orElse(null));
        }
        return toDto(contactRepository.save(contact));
    }

    @Transactional
    public void delete(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Contact not found: " + id));
        contact.setActive(false);
        contactRepository.save(contact);
    }

    private ContactDto toDto(Contact c) {
        return ContactDto.builder()
                .id(c.getId())
                .firstName(c.getFirstName())
                .lastName(c.getLastName())
                .email(c.getEmail())
                .phone(c.getPhone())
                .mobile(c.getMobile())
                .jobTitle(c.getJobTitle())
                .department(c.getDepartment())
                .type(c.getType())
                .linkedinUrl(c.getLinkedinUrl())
                .notes(c.getNotes())
                .accountId(c.getAccount() != null ? c.getAccount().getId() : null)
                .accountName(c.getAccount() != null ? c.getAccount().getName() : null)
                .active(c.isActive())
                .createdAt(c.getCreatedAt())
                .updatedAt(c.getUpdatedAt())
                .build();
    }

    private Contact toEntity(ContactDto dto) {
        Contact contact = new Contact();
        contact.setFirstName(dto.getFirstName());
        contact.setLastName(dto.getLastName());
        contact.setEmail(dto.getEmail());
        contact.setPhone(dto.getPhone());
        contact.setMobile(dto.getMobile());
        contact.setJobTitle(dto.getJobTitle());
        contact.setDepartment(dto.getDepartment());
        contact.setType(dto.getType() != null ? dto.getType() : ContactType.PROSPECTO);
        contact.setLinkedinUrl(dto.getLinkedinUrl());
        contact.setNotes(dto.getNotes());
        if (dto.getAccountId() != null) {
            contact.setAccount(accountRepository.findById(dto.getAccountId()).orElse(null));
        }
        return contact;
    }
}
