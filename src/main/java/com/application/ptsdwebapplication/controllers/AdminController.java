package com.application.ptsdwebapplication.controllers;

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
    
    @GetMapping("/user/{id}")
    public String userDetails(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsUserById(id)) {
            return "redirect:/adminpage/users";
        }

        model.addAttribute("user", peopleService.findUserById(id));
        return "admin/user-details";
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsUserById(id)) {
            return "redirect:/adminpage/users";
        }

        model.addAttribute("user", peopleService.findUserById(id));
        return "admin/user-edit";
    }

    @PostMapping("/user/{id}")
    public String userUpdate(@ModelAttribute("user") @Valid Person user, BindingResult bindingResult,
            @PathVariable(value = "id") int id, Model model) {
        
        if(!peopleService.existsUserById(id)) {
            return "redirect:/adminpage/users";
        }

        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/user-edit";

        model.addAttribute("user", peopleService.update(id, user));
        return "admin/user-details";
    }

    @GetMapping("/user/add")
    public String newUser(@ModelAttribute("person") Person person) {
        return "admin/user-add";
    }

    @PostMapping("/user/add")
    public String createUser(@ModelAttribute("person") @Valid Person user, BindingResult bindingResult, Model model) {
        personValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/user-add";

        model.addAttribute("user", peopleService.addPerson(user, "ROLE_USER"));
        return "admin/user-details";
    }

    @PostMapping("/{id}/remove")
    public String personRemove(@PathVariable(value = "id") int id, Model model) {
        peopleService.removePerson(id);
        return "redirect:/adminpage/users";
    }

    @GetMapping("/admins")
    public String getAdminsTable(Model model) {
        Iterable<Person> admins = peopleService.findByRole("ROLE_ADMIN");
        model.addAttribute("admins", admins);
        model.addAttribute("headers", Labels.adminsTableHeaders);
        return "admin/tables/admins-table";
    }

    @GetMapping("/admin/add")
    public String newAdmin(@ModelAttribute("person") Person person) {
        return "admin/admin-add";
    }

    @PostMapping("/admin/add")
    public String createAdmin(@ModelAttribute("person") @Valid Person admin, BindingResult bindingResult, Model model) {
        personValidator.validate(admin, bindingResult);
        if (bindingResult.hasErrors())
            return "admin/admin-add";

        model.addAttribute("admin", peopleService.addPerson(admin, "ROLE_ADMIN"));
        return "admin/admins-table";
    }

    @GetMapping("/drugs")
    public String getDrugsTable(Model model) {
        // ПОЛУЧЕНИЕ СПИСКА ЛЕКАРСТВ ИЗ БД
        return "admin/tables/drugs-table";
    }

}
