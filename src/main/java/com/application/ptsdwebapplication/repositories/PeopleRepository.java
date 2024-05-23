package com.application.ptsdwebapplication.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.Person;


@Repository
public interface PeopleRepository extends CrudRepository<Person, Integer> { //JpaRepository?

    Optional<Person> findByEmail(String email);

    List<Person> findByRoleOrderBySername(String role);

}

