package com.example.gympool.controller;


import com.example.gympool.entity.Provider;
import com.example.gympool.service.ProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {
    private final ProviderService providerService;

    @Autowired
    public ProviderController(ProviderService providerService) {
        this.providerService = providerService;
    }

    @PostMapping
    public void save(@RequestBody Provider provider){
        providerService.addProvider(provider);
    }

    @PutMapping("/{id}")
    public Provider update(@PathVariable Long id, @RequestBody Provider provider){
        provider.setId(id);
        providerService.updateProvider(provider);
        return provider;
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id){
        providerService.deleteProvider(id);
    }

    @GetMapping("/{id}")
    public Provider findById(@PathVariable Long id){
        return providerService.getProviderById(id);
    }

    @GetMapping("/name/{name}")
    public Provider findByName(@PathVariable String name){
        return providerService.getProviderByName(name);
    }

    @GetMapping
    public List<Provider> findAll(){
        return providerService.getAllProvider();
    }
}
