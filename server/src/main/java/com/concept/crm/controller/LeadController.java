package com.concept.crm.controller;

import com.concept.crm.dto.LeadDto;
import com.concept.crm.enums.LeadStatus;
import com.concept.crm.service.LeadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leads")
@RequiredArgsConstructor
public class LeadController {

    private final LeadService leadService;

    @GetMapping
    public ResponseEntity<List<LeadDto>> findAll() {
        return ResponseEntity.ok(leadService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeadDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(leadService.findById(id));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<LeadDto>> findByStatus(@PathVariable LeadStatus status) {
        return ResponseEntity.ok(leadService.findByStatus(status));
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Long>> getStats() {
        return ResponseEntity.ok(leadService.getStatusCounts());
    }

    @PostMapping
    public ResponseEntity<LeadDto> create(@RequestBody LeadDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(leadService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LeadDto> update(@PathVariable Long id, @RequestBody LeadDto dto) {
        return ResponseEntity.ok(leadService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        leadService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
