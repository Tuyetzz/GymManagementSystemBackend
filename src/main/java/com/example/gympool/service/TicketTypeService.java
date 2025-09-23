package com.example.gympool.service;

import com.example.gympool.entity.TicketType;
import java.util.List;

public interface TicketTypeService {
    TicketType createTicketType(TicketType ticketType);
    TicketType updateTicketType(Long id, TicketType ticketType);
    void deleteTicketType(Long id);
    TicketType getTicketTypeById(Long id);
    List<TicketType> getAllTicketTypes();
}
