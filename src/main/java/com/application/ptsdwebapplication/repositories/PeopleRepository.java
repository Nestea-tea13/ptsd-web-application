package com.application.ptsdwebapplication.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Person;
import java.util.List;


@Repository
public interface PeopleRepository extends CrudRepository<Person, Integer> { //JpaRepository?

    Optional<Person> findByEmail(String email);

    List<Person> findByRole(String role);

}

