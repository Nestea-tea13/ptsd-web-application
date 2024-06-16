package com.application.ptsdwebapplication.util;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.models.PersonDrug;
import com.application.ptsdwebapplication.services.DrugsService;

@Component
public class PersonDrugValidator implements Validator {

    private final DrugsService drugsService;

    @Autowired
    public PersonDrugValidator(DrugsService drugsService) {
        this.drugsService = drugsService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return PersonDrug.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }

    public void validate(Object o, String name, Errors errors) {
        PersonDrug drug = (PersonDrug) o;  
        
        if (name.isEmpty())
            errors.rejectValue("drug", "", "Название не может быть пустым");
        else {
            Optional<Drug> drugFromDB = drugsService.findByName(name);
            if (!drugFromDB.isPresent())
                errors.rejectValue("drug", "", "Лекарства с таким названием нет в списках для выбора");
        }

        if (drug.getStartDate() == null)
            errors.rejectValue("startDate", "", "Необходимо заполнить");

        if (drug.getEndDate() == null)
            errors.rejectValue("endDate", "", "Необходимо заполнить");

        if (!(drug.getStartDate() == null) && !(drug.getEndDate() == null)) {
            if (drug.getStartDate().after(drug.getEndDate())) {
                errors.rejectValue("startDate", "", "Начальная дата должна быть раньше завершающей");
                errors.rejectValue("endDate", "", "Конечная дата должна быть позже начальной");
            }
        }

    }
    
}
