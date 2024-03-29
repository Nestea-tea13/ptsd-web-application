package com.application.ptsdwebapplication.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.repositories.PeopleRepository;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.util.PersonValidator;

@Controller
@RequestMapping("/adminpage")
public class AdminController {

    //private PeopleRepository peopleRepository;
    private final PeopleService peopleService;

    @Autowired
    public AdminController(PeopleService peopleService) { //, PersonValidator personValidator
        this.peopleService = peopleService;
        //this.personValidator = personValidator;
    }

    @GetMapping("/users")
    public String getUsersTable(Model model) {
        Iterable<Person> users = peopleService.findByRole("ROLE_USER"); //peopleRepository.findByRole("ROLE_USER");
        model.addAttribute("users", users);
        model.addAttribute("headers", Labels.usersTableHeaders);
        return "admin/users-table";
    }

    @GetMapping("/admins")
    public String getAdminsTable(Model model) {
        Iterable<Person> admins = peopleService.findByRole("ROLE_ADMIN"); //peopleRepository.findByRole("ROLE_ADMIN");
        model.addAttribute("admins", admins);
        model.addAttribute("headers", Labels.adminsTableHeaders);
        return "admin/admins-table";
    }
    
     @GetMapping("/user/{id}")
    public String userDetails(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsUserById(id)) {
            return "redirect:/admin/users-table";
        }

        //Person user = peopleRepository.findById(id).get();
        //model.addAttribute("user", user);
        model.addAttribute("user", peopleService.findUserById(id));
        return "admin/user-details";
    }

    @GetMapping("/user/{id}/edit")
    public String userEdit(@PathVariable(value = "id") int id, Model model) {
        if(!peopleService.existsUserById(id)) { //  peopleRepository.existsById(id)
            return "redirect:/admin/users-table";
        }

        //Person user = peopleRepository.findById(id).get();
        //model.addAttribute("user", user);
        model.addAttribute("user", peopleService.findUserById(id));
        return "admin/user-edit";
    }

    /*@PostMapping("/user/{id}")
    public String userUpdate(@PathVariable(value = "id") int id, Model model, @RequestParam String sername, 
            @RequestParam String name, @RequestParam String gender, @RequestParam String patronymic,
            @RequestParam String birthday, @RequestParam String email, @RequestParam String status) {
        
        if(!peopleService.existsUserById(id)) {
            return "redirect:/admin/users-table";
        }

        // Сделать из этого метод в сервисе
        Person user = peopleRepository.findById(id).get();
        user.setSername(sername);
        user.setName(name);
        user.setPatronymic(patronymic);
        user.setGender(gender);
        user.setBirthday(birthday);
        user.setEmail(email);
        user.setStatus(status);

        peopleRepository.save(user);
        model.addAttribute("user", user);
        return "admin/user-details";
    }*/

    @PostMapping("/user/{id}")
    public String userUpdate(@ModelAttribute("user") @Valid Person user, @PathVariable(value = "id") int id, Model model) {
        
        if(!peopleService.existsUserById(id)) {
            return "redirect:/admin/users-table";
        }

        model.addAttribute("user", peopleService.update(id, user));
        return "admin/user-details";
    }

}
