package com.example.gympool.controller;

import com.example.gympool.entity.PTPackageIssued;
import com.example.gympool.entity.PTPackageIssued;
import com.example.gympool.service.PTPackageIssuedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/packageissued")
@RequiredArgsConstructor
public class PTPackageIssuedController {
    private final PTPackageIssuedService ptPackageIssuedService;
    @GetMapping()
    public List<PTPackageIssued> findAll() {
        return ptPackageIssuedService.getAllPTPackageIssued();
    }
    @GetMapping("/{id}")
    public PTPackageIssued findPTPackageIssuedById(@PathVariable("id") Long id) {
        return ptPackageIssuedService.getPTPackageIssuedById(id);
    }
    @GetMapping("/member")
    public PTPackageIssued findPTPackageIssuedByCustomerName(@RequestParam("name") String name) {
        return ptPackageIssuedService.getPTPackageIssuedByCustomerName(name);
    }
    @GetMapping("/staff")
    public PTPackageIssued findPTPackageIssuedByStaffName(@RequestParam("name") String name) {
        return ptPackageIssuedService.getPTPackageIssuedByStaffName(name);
    }
    @PostMapping()
    public void addPTPackageIssued(@RequestBody PTPackageIssued PTPackageIssued) {
        ptPackageIssuedService.registerPTPackage(PTPackageIssued);
    }

    @PutMapping("/{id}")
    public void updatePTPackageIssued(@PathVariable("id") Long id,
                                     @RequestBody PTPackageIssued PTPackageIssued) {
        ptPackageIssuedService.updatePTPackageIssued(id, PTPackageIssued);
    }
}
