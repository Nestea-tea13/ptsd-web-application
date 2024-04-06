package com.application.ptsdwebapplication.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.models.PersonDrug;
import com.application.ptsdwebapplication.services.DrugsService;
import com.application.ptsdwebapplication.services.PersonDrugsService;
import com.application.ptsdwebapplication.util.PersonDrugValidator;

@Controller
@RequestMapping("/drugs")
public class UserDrugsController {

    private final PersonDrugsService personDrugsService;
    private final DrugsService drugsService;
    private final PersonDrugValidator personDrugValidator;
    
    public UserDrugsController(PersonDrugsService personDrugsService, DrugsService drugsService, PersonDrugValidator personDrugValidator) {
        this.personDrugsService = personDrugsService;
        this.drugsService = drugsService;
        this.personDrugValidator = personDrugValidator;
    }

    @GetMapping()
    public String getDrugsList(Model model) {
        List<PersonDrug> drugs = personDrugsService.findPersonDrugs();
        if (!drugs.isEmpty()) {
            model.addAttribute("drugs", drugs);
            model.addAttribute("headers", Labels.PersonDrugsTableHeaders);
        }
        return "user/drugs/drugs-data";
    }

    @GetMapping("/add")
    public String settingNewDrugForUser(@ModelAttribute("personDrug") PersonDrug personDrug, Model model) {
        model.addAttribute("periodoptions", Labels.PeriodDrugOptions);
        model.addAttribute("names", drugsService.getAllDrugsNames());
        return "user/drugs/drug-add";
    }

    @PostMapping("/add")
    public String addNewDrugForUser(@ModelAttribute("personDrug") @Valid PersonDrug personDrug, BindingResult bindingResult,
            @RequestParam String drugname, Model model) {

        personDrugValidator.validate(personDrug, drugname, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("name", drugname);
            model.addAttribute("periodoptions", Labels.PeriodDrugOptions);
            model.addAttribute("names", drugsService.getAllDrugsNames());
            return "user/drugs/drug-add";
        }

        // ДОБАВИТЬ СОЗДАНИЕ ЗАПИСЕЙ ДЛЯ ОТМЕТОК О ПРИНЯТИИ ЛЕКАРСТВ

        personDrugsService.addDrug(personDrug, drugname);
        return "redirect:/drugs";
    }

}
