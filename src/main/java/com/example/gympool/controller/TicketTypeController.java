package com.example.gympool.controller;

import com.example.gympool.entity.TicketType;
import com.example.gympool.service.TicketTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ticket-types")
public class TicketTypeController {

    private final TicketTypeService ticketTypeService;

    public TicketTypeController(TicketTypeService ticketTypeService) {
        this.ticketTypeService = ticketTypeService;
    }

    @PostMapping
    public ResponseEntity<TicketType> createTicketType(@RequestBody TicketType ticketType) {
        return ResponseEntity.ok(ticketTypeService.createTicketType(ticketType));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TicketType> updateTicketType(@PathVariable Long id, @RequestBody TicketType ticketType) {
        return ResponseEntity.ok(ticketTypeService.updateTicketType(id, ticketType));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTicketType(@PathVariable Long id) {
        ticketTypeService.deleteTicketType(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketType> getTicketTypeById(@PathVariable Long id) {
        return ResponseEntity.ok(ticketTypeService.getTicketTypeById(id));
    }

    @GetMapping
    public ResponseEntity<List<TicketType>> getAllTicketTypes() {
        return ResponseEntity.ok(ticketTypeService.getAllTicketTypes());
    }
}
