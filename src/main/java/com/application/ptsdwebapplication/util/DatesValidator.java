package com.application.ptsdwebapplication.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.application.ptsdwebapplication.models.subsidiaryClasses.DatesInterval;
import com.application.ptsdwebapplication.services.QuestionnairesService;

@Component
public class DatesValidator implements Validator {

    private final QuestionnairesService questionnairesService;
    
    @Autowired
    public DatesValidator(QuestionnairesService questionnairesService) {
        this.questionnairesService = questionnairesService;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return DatesInterval.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    }

    public void validate(Object o, String name, Errors errors) {
        DatesInterval dates = (DatesInterval) o;         

        boolean checkStart = (dates.getStart() == null);
        if (checkStart) errors.rejectValue("start", "", "Необходимо заполнить начальную дату");

        boolean checkEnd = (dates.getEnd() == null);
        if (checkEnd) errors.rejectValue("end", "", "Необходимо заполнить конечную дату");

        if(!checkStart && !checkEnd) {
            if (dates.getStart().after(dates.getEnd())) {
                errors.rejectValue("start", "", "Начальная дата должна быть раньше конечной");
            }
            else {
                if(!questionnairesService.existsQuestionnaireResultsBetweenDates(name, dates.getStart(), dates.getEnd())) {
                    errors.rejectValue("start", "", "В указанный временной промежуток вы не заполняли данный вид опросника");
                }
            }
        }

    }
    
}
