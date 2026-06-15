package com.concept.crm.service;

import com.concept.crm.dto.AccountDto;
import com.concept.crm.entity.Account;
import com.concept.crm.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    public List<AccountDto> findAll() {
        return accountRepository.findByActiveTrue().stream().map(this::toDto).toList();
    }

    public AccountDto findById(Long id) {
        return toDto(accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id)));
    }

    public List<AccountDto> search(String name) {
        return accountRepository.findByNameContainingIgnoreCase(name).stream().map(this::toDto).toList();
    }

    @Transactional
    public AccountDto create(AccountDto dto) {
        Account account = toEntity(dto);
        return toDto(accountRepository.save(account));
    }

    @Transactional
    public AccountDto update(Long id, AccountDto dto) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        account.setName(dto.getName());
        account.setIndustry(dto.getIndustry());
        account.setWebsite(dto.getWebsite());
        account.setPhone(dto.getPhone());
        account.setEmail(dto.getEmail());
        account.setAddress(dto.getAddress());
        account.setCity(dto.getCity());
        account.setCountry(dto.getCountry());
        account.setEmployees(dto.getEmployees());
        account.setAnnualRevenue(dto.getAnnualRevenue());
        account.setDescription(dto.getDescription());
        return toDto(accountRepository.save(account));
    }

    @Transactional
    public void delete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Account not found: " + id));
        account.setActive(false);
        accountRepository.save(account);
    }

    private AccountDto toDto(Account a) {
        return AccountDto.builder()
                .id(a.getId())
                .name(a.getName())
                .industry(a.getIndustry())
                .website(a.getWebsite())
                .phone(a.getPhone())
                .email(a.getEmail())
                .address(a.getAddress())
                .city(a.getCity())
                .country(a.getCountry())
                .employees(a.getEmployees())
                .annualRevenue(a.getAnnualRevenue())
                .description(a.getDescription())
                .active(a.isActive())
                .createdAt(a.getCreatedAt())
                .updatedAt(a.getUpdatedAt())
                .build();
    }

    private Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setName(dto.getName());
        account.setIndustry(dto.getIndustry());
        account.setWebsite(dto.getWebsite());
        account.setPhone(dto.getPhone());
        account.setEmail(dto.getEmail());
        account.setAddress(dto.getAddress());
        account.setCity(dto.getCity());
        account.setCountry(dto.getCountry());
        account.setEmployees(dto.getEmployees());
        account.setAnnualRevenue(dto.getAnnualRevenue());
        account.setDescription(dto.getDescription());
        return account;
    }
}
