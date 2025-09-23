package com.example.gympool.service.impl;

import com.example.gympool.entity.TicketType;
import com.example.gympool.repository.TicketTypeRepository;
import com.example.gympool.service.TicketTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketTypeServiceImpl implements TicketTypeService {

    private final TicketTypeRepository ticketTypeRepository;

    public TicketTypeServiceImpl(TicketTypeRepository ticketTypeRepository) {
        this.ticketTypeRepository = ticketTypeRepository;
    }

    @Override
    public TicketType createTicketType(TicketType ticketType) {
        return ticketTypeRepository.save(ticketType);
    }

    @Override
    public TicketType updateTicketType(Long id, TicketType ticketType) {
        TicketType existing = ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TicketType not found with id " + id));
        existing.setName(ticketType.getName());
        existing.setServiceType(ticketType.getServiceType());
        existing.setDurationDays(ticketType.getDurationDays());
        existing.setPrice(ticketType.getPrice());
        return ticketTypeRepository.save(existing);
    }

    @Override
    public void deleteTicketType(Long id) {
        if (!ticketTypeRepository.existsById(id)) {
            throw new RuntimeException("TicketType not found with id " + id);
        }
        ticketTypeRepository.deleteById(id);
    }

    @Override
    public TicketType getTicketTypeById(Long id) {
        return ticketTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TicketType not found with id " + id));
    }

    @Override
    public List<TicketType> getAllTicketTypes() {
        return ticketTypeRepository.findAll();
    }
}
