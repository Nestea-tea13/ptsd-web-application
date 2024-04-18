package com.application.ptsdwebapplication.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.services.PersonDrugsService;

@Controller
public class MainController {

  private final PeopleService peopleService;
  private final PersonDrugsService personDrugsService;

  public MainController(PeopleService peopleService, PersonDrugsService personDrugsService) {
    this.peopleService = peopleService;
    this.personDrugsService = personDrugsService;
  }

  @GetMapping("/")
    public String startPage(Model model) {
    return "redirect:/login";
  }

  @GetMapping("/login")
    public String loginPage() {
    return "main/login";
  }

  @GetMapping("/main")
  public String userStartPage(Model model) {
    model.addAttribute("name", peopleService.getCurrentPerson().getName());

    List<DrugMark> todayMarks = personDrugsService.findNeedMarks("today");
    if (todayMarks != null) model.addAttribute("todayMarks", todayMarks);

    List<DrugMark> missedMarks = personDrugsService.findNeedMarks("missed");
    if (missedMarks != null) model.addAttribute("missedMarks", missedMarks);
    return "user/user-start-page";
  }
    
}
