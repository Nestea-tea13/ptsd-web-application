package com.application.ptsdwebapplication.controllers;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.ptsdwebapplication.data.DateBorders;
import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.models.Drug;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.services.DrugsService;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.util.DrugValidator;
import com.application.ptsdwebapplication.util.PersonValidator;

@Controller
@RequestMapping("/adminpage")
public class AdminController {

    private final PeopleService peopleService;
    private final DrugsService drugsService;
    private final PersonValidator personValidator;
    private final DrugValidator drugValidator;

    @Autowired
    public AdminController(PeopleService peopleService, DrugsService drugsService, 
                        PersonValidator personValidator, DrugValidator drugValidator) { 
        this.peopleService = peopleService;
        this.drugsService = drugsService;
        this.personValidator = personValidator;
        this.drugValidator = drugValidator;
    }

    @GetMapping("/users")
    public String getUsersTable(Model model) {
        model.addAttribute("users", peopleService.findByRole("ROLE_USER"));
        model.addAttribute("headers", Labels.usersTableHeaders);
        return "admin/tables/users-table";
    }

    @GetMapping("/admins")
    public String getAdminsTable(Model model) {
        model.addAttribute("admins", peopleService.findByRole("ROLE_ADMIN"));
        model.addAttribute("headers", Labels.adminsTableHeaders);
        return "admin/tables/admins-table";
    }
    
    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsPersonById(id)) {
            return "redirect:/adminpage/users";
        }

        model.addAttribute("user", peopleService.findPersonById(id));
        return "admin/user-details";
    }

    @GetMapping("/{role}/{id}/edit")
    public String editUserOrOtherAdmin(@PathVariable(value = "role") String role, @PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsPersonById(id) || !Arrays.asList("admin", "user").contains(role)) {
            if (role.equals("admin"))
                return "redirect:/adminpage/admins";
            else return "redirect:/adminpage/users";
        }

        model.addAttribute("person", peopleService.findPersonById(id));
        model.addAttribute("flagEditUser", role.equals("user"));
        model.addAttribute("BirthdayDateBorders", DateBorders.getBirthdayBorders());
        return "admin/person-edit";
    }

    @PostMapping({"/user/{id}", "/admin/{id}"})
    public String userUpdate(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
            @PathVariable(value = "id") int id, Model model) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flagEditUser", peopleService.findPersonById(id).getRole().equals("ROLE_USER"));
            model.addAttribute("BirthdayDateBorders", DateBorders.getBirthdayBorders());
            return "admin/person-edit";
        }
        
        peopleService.update(id, person);
        if (person.getRole().equals("ROLE_USER")) return "redirect:/adminpage/user/{id}";
        else return "redirect:/adminpage/admins";
    }

    @GetMapping("/person/add")
    public String addNewPerson(@ModelAttribute("person") Person person, @RequestParam(value = "role", required = false) String role, Model model) {
        if(!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) return "redirect:/adminpage/users";
        model.addAttribute("flagEditUser", role.equals("ROLE_USER"));
        model.addAttribute("BirthdayDateBorders", DateBorders.getBirthdayBorders());
        return "admin/person-add";
    }

    @PostMapping("/person/add")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flagEditUser", person.getRole().equals("ROLE_USER"));
            model.addAttribute("BirthdayDateBorders", DateBorders.getBirthdayBorders());
            return "admin/person-add";
        }
            
        model.addAttribute("person", peopleService.addPerson(person));
        if (person.getRole().equals("ROLE_USER")) return "redirect:/adminpage/users";
        else return "redirect:/adminpage/admins";
    }

    @PostMapping("/{id}/remove")
    public String personRemove(@PathVariable(value = "id") int id, Model model) {
        peopleService.removePerson(id);
        return "redirect:/adminpage/users";
    }
    
    @GetMapping("/drugs")
    public String getDrugsTable(@ModelAttribute("drug") Drug drug, Model model) {
        model.addAttribute("drugs", drugsService.findAll());
        model.addAttribute("headers", Labels.drugsTableHeaders);
        return "admin/tables/drugs-table";
    }

    @GetMapping("/drug/add")
    public String settingNewDrug(@ModelAttribute("drug") Drug drug, Model model) {
        return "admin/drug-add";
    }

    @PostMapping("/drug/add")
    public String addNewDrug(@ModelAttribute("drug") @Valid Drug drug, BindingResult bindingResult, Model model) {

        drugValidator.validate(drug, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/drug-add";
        }

        drugsService.addDrug(drug);
        return "redirect:/adminpage/drugs";
    }

    @GetMapping("/drug/{id}/edit")
    public String editDrug(@PathVariable(value = "id") int id, Model model) {
        if(!drugsService.existsById(id)) {
            return "redirect:/adminpage/drugs";
        }

        model.addAttribute("drug", drugsService.findById(id));
        return "admin/drug-edit";
    }

    @PostMapping("/drug/{id}")
    public String drugUpdate(@ModelAttribute("drug") @Valid Drug drug, BindingResult bindingResult,
            @PathVariable(value = "id") int id, Model model) {

        drugValidator.validate(drug, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/drug-edit";
        }

        drugsService.update(id, drug);
        return "redirect:/adminpage/drugs";
    }
        
}
