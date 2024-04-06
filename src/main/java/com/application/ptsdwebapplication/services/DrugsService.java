package com.application.ptsdwebapplication.services;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.repositories.DrugsRepository;

@Service
@Transactional(readOnly = true)
public class DrugsService {

    private final DrugsRepository drugsRepository;

    public DrugsService(DrugsRepository drugsRepository) {
        this.drugsRepository = drugsRepository;
    }

    public Iterable<Drug> findAll() {
        return drugsRepository.findAll(Sort.by("name"));
    }

    public Optional<Drug> findByName(String name) {
        return drugsRepository.findByName(name);
    }

    @Transactional
    public void addDrug(Drug drug) {
        drugsRepository.save(drug);
    }

    public List<String> getAllDrugsNames() {
        List<String> drugs = ((Collection<Drug>) findAll()).stream().map((drug) -> drug.getName()).collect(Collectors.toList()); 
        Collections.sort(drugs);
        return drugs;
        
    }
    
}
