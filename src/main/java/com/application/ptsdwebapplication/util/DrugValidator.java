package com.application.ptsdwebapplication.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.services.DrugsService;

@Component
public class DrugValidator implements Validator {

    private final DrugsService drugsService;

    @Autowired
    public DrugValidator(DrugsService drugsService) {
        this.drugsService = drugsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Drug.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Drug drug = (Drug) o;         

        Optional<Drug> drugFromDB = drugsService.findByName(drug.getName());
        if (drugFromDB.isPresent())
            errors.rejectValue("name", "", "Лекарство с таким названием уже есть");

    }
    
}
