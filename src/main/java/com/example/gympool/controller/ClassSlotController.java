package com.example.gympool.controller;

import com.example.gympool.entity.ClassSlot;
import com.example.gympool.entity.FollowClass;
import com.example.gympool.entity.TeachingRegistration;
import com.example.gympool.service.ClassSlotService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classslots")
public class ClassSlotController {

    private final ClassSlotService classSlotService;

    public ClassSlotController(ClassSlotService classSlotService) {
        this.classSlotService = classSlotService;
    }

    // CRUD
    @GetMapping
    public ResponseEntity<List<ClassSlot>> getAll() {
        return ResponseEntity.ok(classSlotService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassSlot> getById(@PathVariable Long id) {
        return classSlotService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassSlot> create(@RequestBody ClassSlot classSlot) {
        return ResponseEntity.ok(classSlotService.create(classSlot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassSlot> update(@PathVariable Long id, @RequestBody ClassSlot classSlot) {
        return ResponseEntity.ok(classSlotService.update(id, classSlot));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classSlotService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
