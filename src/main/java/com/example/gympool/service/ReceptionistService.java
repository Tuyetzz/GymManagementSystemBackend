package com.example.gympool.service;

import com.example.gympool.entity.Receptionist;

import java.util.List;
import java.util.Optional;

public interface ReceptionistService {
    List<Receptionist> getAllReceptionists();
    Receptionist getReceptionistById(Long id);
    Optional<Receptionist> getReceptionistByEmail(String email);
    Receptionist createReceptionist(Receptionist receptionist);
    Receptionist updateReceptionist(Long id, Receptionist receptionist);
    void deleteReceptionist(Long id);
}
