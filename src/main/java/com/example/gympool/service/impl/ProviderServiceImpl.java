package com.example.gympool.service.impl;

import com.example.gympool.entity.Provider;
import com.example.gympool.repository.ProviderRepository;
import com.example.gympool.service.ProviderService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;

    public ProviderServiceImpl(ProviderRepository providerRepository) {
        this.providerRepository = providerRepository;
    }

    @Override
    public void addProvider(Provider provider) {
        providerRepository.save(provider);
    }

    @Override
    public void updateProvider(Provider provider) {
        providerRepository.save(provider);
    }

    @Override
    public void deleteProvider(Long id) {
        providerRepository.deleteById(id);
    }

    @Override
    public Provider getProviderByName(String name) {
        return providerRepository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    @Override
    public Provider getProviderById(Long id) {
        return providerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Provider not found"));
    }

    @Override
    public List<Provider> getAllProvider() {
        return providerRepository.findAll();
    }
}
