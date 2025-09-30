package com.example.gympool.service.impl;

import com.example.gympool.entity.Receptionist;
import com.example.gympool.repository.ReceptionistRepository;
import com.example.gympool.service.ReceptionistService;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class ReceptionistServiceImpl implements ReceptionistService {

    private final ReceptionistRepository receptionistRepository;

    public ReceptionistServiceImpl(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }

    @Override
    public List<Receptionist> getAllReceptionists() {
        return receptionistRepository.findAll();
    }

    @Override
    public Receptionist getReceptionistById(Long id) {
        return receptionistRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Receptionist not found with id: " + id));
    }

    @Override
    public Optional<Receptionist> getReceptionistByEmail(String email) {
        return receptionistRepository.findByEmail(email);
    }

    @Override
    public Receptionist createReceptionist(Receptionist receptionist) {
        return receptionistRepository.save(receptionist);
    }

    @Override
    public Receptionist updateReceptionist(Long id, Receptionist receptionistDetails) {
        Receptionist receptionist = getReceptionistById(id);

        // cập nhật field chung từ User
        if (receptionistDetails.getEmail() != null) receptionist.setEmail(receptionistDetails.getEmail());
        if (receptionistDetails.getPassword() != null) receptionist.setPassword(receptionistDetails.getPassword());
        if (receptionistDetails.getFullName() != null) receptionist.setFullName(receptionistDetails.getFullName());
        if (receptionistDetails.getDob() != null) receptionist.setDob(receptionistDetails.getDob());
        if (receptionistDetails.getGender() != null) receptionist.setGender(receptionistDetails.getGender());
        if (receptionistDetails.getPhone() != null) receptionist.setPhone(receptionistDetails.getPhone());

        return receptionistRepository.save(receptionist);
    }

    @Override
    public void deleteReceptionist(Long id) {
        if (!receptionistRepository.existsById(id)) {
            throw new IllegalArgumentException("Receptionist not found with id: " + id);
        }
        receptionistRepository.deleteById(id);
    }
}
