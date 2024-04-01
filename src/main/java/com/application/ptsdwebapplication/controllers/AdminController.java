package com.application.ptsdwebapplication.controllers;

import java.util.regex.Pattern;

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

import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.util.PersonValidator;

@Controller
@RequestMapping("/adminpage")
public class AdminController {

    private final PeopleService peopleService;
    private final PersonValidator personValidator;

    @Autowired
    public AdminController(PeopleService peopleService, PersonValidator personValidator) { 
        this.peopleService = peopleService;
        this.personValidator = personValidator;
    }

    @GetMapping("/users")
    public String getUsersTable(Model model) {
        Iterable<Person> users = peopleService.findByRole("ROLE_USER");
        model.addAttribute("users", users);
        model.addAttribute("headers", Labels.usersTableHeaders);
        return "admin/tables/users-table";
    }

    @GetMapping("/admins")
    public String getAdminsTable(Model model) {
        Iterable<Person> admins = peopleService.findByRole("ROLE_ADMIN");
        model.addAttribute("admins", admins);
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

    @GetMapping({"/user/{id}/edit", "/admin/{id}/edit"})
    public String editUserOrOtherAdmin(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsPersonById(id)) {
            return "redirect:/adminpage";
        }

        model.addAttribute("person", peopleService.findPersonById(id));
        model.addAttribute("flagEditUser", peopleService.findPersonById(id).getRole().equals("ROLE_USER"));
        return "admin/person-edit";
    }

    @PostMapping({"/user/{id}", "/admin/{id}"})
    public String userUpdate(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult,
            @PathVariable(value = "id") int id, Model model) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flagEditUser", peopleService.findPersonById(id).getRole().equals("ROLE_USER"));
            return "admin/person-edit";
        }

        model.addAttribute("user", peopleService.update(id, person));

        if (person.getRole().equals("ROLE_USER")) return "admin/user-details";
        else return "redirect:/adminpage/admins";
    }

    @GetMapping("/person/add")
    public String addNewPerson(@ModelAttribute("person") Person person, @RequestParam(value = "role", required = false) String role, Model model) {
        if(!role.equals("ROLE_USER") && !role.equals("ROLE_ADMIN")) return "redirect:/adminpage";
        model.addAttribute("flagEditUser", role.equals("ROLE_USER"));
        return "admin/person-add";
    }

    @PostMapping("/person/add")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult, Model model) {

        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("flagEditUser", person.getRole().equals("ROLE_USER"));
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
    public String getDrugsTable(Model model) {
        // ПОЛУЧЕНИЕ СПИСКА ЛЕКАРСТВ ИЗ БД
        return "admin/tables/drugs-table";
    }

}
