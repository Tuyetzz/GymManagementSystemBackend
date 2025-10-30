package com.example.gympool.controller;

import com.example.gympool.entity.PTAppointment;
import com.example.gympool.entity.PTAppointment;
import com.example.gympool.entity.StudentProfile;
import com.example.gympool.service.PTAppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointment")
@RequiredArgsConstructor
public class PTAppointmentController {
    private final PTAppointmentService ptAppointmentService;
    @GetMapping()
    public List<PTAppointment> findAll() {
        return ptAppointmentService.getAllPTAppointment();
    }
    @GetMapping("/{id}")
    public PTAppointment findPTAppointmentById(@PathVariable("id") Long id) {
        return ptAppointmentService.getPTAppointmentById(id);
    }
    @GetMapping("/member")
    public PTAppointment findPTAppointmentByCustomerName(@RequestParam("name") String name) {
        return ptAppointmentService.getPTAppointmentByCustomerName(name);
    }
    @GetMapping("/staff")
    public PTAppointment findPTAppointmentByStaffName(@RequestParam("name") String name) {
        return ptAppointmentService.getPTAppointmentByStaffName(name);
    }
    @PostMapping()
    public void addPTAppointment(@RequestBody PTAppointment PTAppointment) {
        ptAppointmentService.addPTAppointment(PTAppointment);
    }
    @PutMapping("/{id}")
    public void updateStudentProfile(@PathVariable("id") Long id,
                                     @RequestBody PTAppointment PTAppointment) {
        ptAppointmentService.updatePTAppointment(id, PTAppointment);
    }
}
