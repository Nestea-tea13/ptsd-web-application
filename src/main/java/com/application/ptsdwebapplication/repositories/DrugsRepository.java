package com.application.ptsdwebapplication.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Drug;

@Repository
public interface DrugsRepository extends JpaRepository<Drug, Integer> {

    Optional<Drug> findByName(String name);

    Iterable<Drug> findByStatus(int i);
    
}
