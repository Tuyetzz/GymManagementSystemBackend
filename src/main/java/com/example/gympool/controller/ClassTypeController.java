package com.example.gympool.controller;

import com.example.gympool.entity.ClassType;
import com.example.gympool.service.ClassTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classtypes")
public class ClassTypeController {

    private final ClassTypeService classTypeService;

    public ClassTypeController(ClassTypeService classTypeService) {
        this.classTypeService = classTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ClassType>> getAll() {
        return ResponseEntity.ok(classTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassType> getById(@PathVariable Long id) {
        return classTypeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassType> create(@RequestBody ClassType classType) {
        return ResponseEntity.ok(classTypeService.create(classType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassType> update(@PathVariable Long id, @RequestBody ClassType classType) {
        return ResponseEntity.ok(classTypeService.update(id, classType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
