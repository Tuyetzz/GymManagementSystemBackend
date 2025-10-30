package com.example.gympool.controller;

import com.example.gympool.entity.PTPackage;
import com.example.gympool.entity.PTPackage;
import com.example.gympool.service.PTPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ptpackage")
@RequiredArgsConstructor
public class PTPackageController {
    private final PTPackageService ptPackageService;
    @GetMapping()
    public List<PTPackage> findAll() {
        return ptPackageService.getAllPTPackage();
    }
    @GetMapping("/{id}")
    public PTPackage findPTPackageById(@PathVariable("id") Long id) {
        return ptPackageService.getPTPackageById(id);
    }

    @PostMapping()
    public void addPTPackage(@RequestBody PTPackage PTPackage) {
        ptPackageService.addPTPackage(PTPackage);
    }

    @PutMapping("/{id}")
    public void updatePTPackage(@PathVariable("id") Long id,
                                         @RequestBody PTPackage PTPackage) {
        ptPackageService.updatePTPackage(id, PTPackage);
    }
}
