package com.application.ptsdwebapplication.controllers;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.ptsdwebapplication.services.PeopleService;

@Controller
public class GeneralController {

    private final PeopleService peopleService;

    @Autowired
    public GeneralController(PeopleService peopleService) { 
        this.peopleService = peopleService;
    }
    
    @GetMapping({"/profile", "/adminpage/profile"})
    public String openProfile(Model model) {
        model.addAttribute("person", peopleService.getCurrentPerson());
        model.addAttribute("flagRoleAdmin", peopleService.getCurrentPerson().getRole().equals("ROLE_ADMIN"));
        return "general/profile-page";
    }

    @GetMapping({"/profile/changepassword", "/adminpage/profile/changepassword"})
    public String changePassword(Model model) {
        model.addAttribute("flagRoleAdmin", peopleService.getCurrentPerson().getRole().equals("ROLE_ADMIN"));
        return "general/change-password";
    }

    @PostMapping({"/profile/changepassword", "/adminpage/profile/changepassword"})
    public String saveEditPassword(@RequestParam String lastPassword, @RequestParam String newPassword, @RequestParam String newPasswordRepeat, Model model) { 
        boolean errorLastPassword = lastPassword.equals(peopleService.getCurrentPerson().getPassword());
        boolean errorNewPassword = newPassword.equals(newPasswordRepeat);
        boolean errorSuitablePassword = Pattern.matches("^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$", newPassword);

        if (!errorLastPassword || !errorNewPassword || !errorSuitablePassword) { // ПОТОМ ДОБАВИТСЯ ШИФРОВАНИЕ
            if (!errorLastPassword) model.addAttribute("errorLastPassword", true);
            if (!errorNewPassword) model.addAttribute("errorNewPassword", true);
            if (!errorSuitablePassword) model.addAttribute("errorSuitablePassword", true);
            model.addAttribute("lastPassword", lastPassword);
            model.addAttribute("newPassword", newPassword);
            model.addAttribute("newPasswordRepeat", newPasswordRepeat);
            return "general/change-password";
        }

        peopleService.updatePassword(newPassword);

        if(peopleService.getCurrentPerson().getRole().equals("ROLE_ADMIN")) return "redirect:/adminpage/profile";
        else return "redirect:/profile";
    }

    @GetMapping({"/profile/edit", "/adminpage/profile/edit"})
    public String profileEdit(Model model) {
        model.addAttribute("flagRoleAdmin", peopleService.getCurrentPerson().getRole().equals("ROLE_ADMIN"));
        return "general/profile-edit";
    }

    // Страница редактирования данных своего профиля
    //@PostMapping({"/profile/edit", "/adminpage/profile/edit"})

}
