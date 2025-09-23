package com.example.gympool.service.impl;

import com.example.gympool.entity.Ticket;
import com.example.gympool.repository.TicketRepository;
import com.example.gympool.service.TicketService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    @Override
    public Ticket updateTicket(Long id, Ticket ticket) {
        Ticket existing = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
        existing.setPurchaseDate(ticket.getPurchaseDate());
        existing.setStatus(ticket.getStatus());
        existing.setCardId(ticket.getCardId());
        existing.setTicketType(ticket.getTicketType());
        existing.setMember(ticket.getMember());
        return ticketRepository.save(existing);
    }

    @Override
    public void deleteTicket(Long id) {
        if (!ticketRepository.existsById(id)) {
            throw new RuntimeException("Ticket not found with id " + id);
        }
        ticketRepository.deleteById(id);
    }

    @Override
    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));
    }

    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> getTicketsByMember(Long memberId) {
        return ticketRepository.findByMemberId(memberId);
    }


}
