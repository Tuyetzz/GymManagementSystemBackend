package com.example.gympool.controller;

import com.example.gympool.entity.Receptionist;
import com.example.gympool.service.ReceptionistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/receptionist")
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    public ReceptionistController(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }

    @GetMapping
    public List<Receptionist> getAllReceptionists() {
        return receptionistService.getAllReceptionists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receptionist> getReceptionistById(@PathVariable Long id) {
        return ResponseEntity.ok(receptionistService.getReceptionistById(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Receptionist> getReceptionistByEmail(@PathVariable String email) {
        return receptionistService.getReceptionistByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Receptionist> createReceptionist(@RequestBody Receptionist receptionist) {
        return ResponseEntity.ok(receptionistService.createReceptionist(receptionist));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Receptionist> updateReceptionist(@PathVariable Long id, @RequestBody Receptionist receptionist) {
        return ResponseEntity.ok(receptionistService.updateReceptionist(id, receptionist));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReceptionist(@PathVariable Long id) {
        receptionistService.deleteReceptionist(id);
        return ResponseEntity.noContent().build();
    }
}
