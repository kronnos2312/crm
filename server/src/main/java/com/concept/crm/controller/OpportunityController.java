package com.concept.crm.controller;

import com.concept.crm.dto.OpportunityDto;
import com.concept.crm.enums.OpportunityStage;
import com.concept.crm.service.OpportunityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/opportunities")
@RequiredArgsConstructor
public class OpportunityController {

    private final OpportunityService opportunityService;

    @GetMapping
    public ResponseEntity<List<OpportunityDto>> findAll() {
        return ResponseEntity.ok(opportunityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OpportunityDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(opportunityService.findById(id));
    }

    @GetMapping("/stage/{stage}")
    public ResponseEntity<List<OpportunityDto>> findByStage(@PathVariable OpportunityStage stage) {
        return ResponseEntity.ok(opportunityService.findByStage(stage));
    }

    @GetMapping("/pipeline")
    public ResponseEntity<Map<String, Object>> getPipeline() {
        return ResponseEntity.ok(opportunityService.getPipelineSummary());
    }

    @PostMapping
    public ResponseEntity<OpportunityDto> create(@RequestBody OpportunityDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(opportunityService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OpportunityDto> update(@PathVariable Long id, @RequestBody OpportunityDto dto) {
        return ResponseEntity.ok(opportunityService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        opportunityService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
