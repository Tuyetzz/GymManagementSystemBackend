package com.example.gympool.controller;

import com.example.gympool.entity.ClassTemplate;
import com.example.gympool.service.ClassTemplateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/classtemplate")
public class ClassTemplateController {

    private final ClassTemplateService classTypeService;

    public ClassTemplateController(ClassTemplateService classTypeService) {
        this.classTypeService = classTypeService;
    }

    @GetMapping
    public ResponseEntity<List<ClassTemplate>> getAll() {
        return ResponseEntity.ok(classTypeService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassTemplate> getById(@PathVariable Long id) {
        return classTypeService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ClassTemplate> create(@RequestBody ClassTemplate classTemplate) {
        return ResponseEntity.ok(classTypeService.create(classTemplate));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassTemplate> update(@PathVariable Long id, @RequestBody ClassTemplate classTemplate) {
        return ResponseEntity.ok(classTypeService.update(id, classTemplate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        classTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
