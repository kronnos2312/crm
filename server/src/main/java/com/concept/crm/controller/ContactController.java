package com.concept.crm.controller;

import com.concept.crm.dto.ContactDto;
import com.concept.crm.enums.ContactType;
import com.concept.crm.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@RequiredArgsConstructor
public class ContactController {

    private final ContactService contactService;

    @GetMapping
    public ResponseEntity<List<ContactDto>> findAll() {
        return ResponseEntity.ok(contactService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContactDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(contactService.findById(id));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ContactDto>> search(@RequestParam String q) {
        return ResponseEntity.ok(contactService.search(q));
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<ContactDto>> findByType(@PathVariable ContactType type) {
        return ResponseEntity.ok(contactService.findByType(type));
    }

    @PostMapping
    public ResponseEntity<ContactDto> create(@RequestBody ContactDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContactDto> update(@PathVariable Long id, @RequestBody ContactDto dto) {
        return ResponseEntity.ok(contactService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
