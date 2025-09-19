package com.example.gympool.service;

import com.example.gympool.entity.Provider;

import java.util.List;

public interface ProviderService {
    void addProvider(Provider provider);
    void updateProvider(Provider provider);
    void deleteProvider(Long id);
    Provider getProviderByName(String name);
    Provider getProviderById(Long id);
    List<Provider> getAllProvider();
}
