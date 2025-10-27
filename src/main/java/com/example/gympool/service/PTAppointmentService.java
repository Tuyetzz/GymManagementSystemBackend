package com.example.gympool.service;

import com.example.gympool.entity.PTAppointment;
import com.example.gympool.entity.PTAppointment;

import java.util.List;

public interface PTAppointmentService {
    List<PTAppointment> getAllPTAppointment();
    PTAppointment getPTAppointmentById(Long id);
    PTAppointment getPTAppointmentByCustomerName(String name);
    PTAppointment getPTAppointmentByStaffName(String name);
    PTAppointment addPTAppointment(PTAppointment PTAppointment);
    PTAppointment updatePTAppointment(Long id, PTAppointment PTAppointment);
}
