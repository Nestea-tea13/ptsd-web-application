package com.application.ptsdwebapplication.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.services.PersonDrugsService;
import com.application.ptsdwebapplication.services.QuestionnairesService;

@Controller
public class MainController {

  private final PeopleService peopleService;
  private final PersonDrugsService personDrugsService;
  private final QuestionnairesService questionnairesService;

  public MainController(PeopleService peopleService, PersonDrugsService personDrugsService, QuestionnairesService questionnairesService) {
    this.peopleService = peopleService;
    this.personDrugsService = personDrugsService;
    this.questionnairesService = questionnairesService;
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

    if (questionnairesService.checkNotCompletedQuestionnaires()) model.addAttribute("allQuestionnairesCompleted", true); 
    else {
      if (questionnairesService.getLastQuestionnaire("CAPS") == null) model.addAttribute("firstCAPS", true);
      else {
        if (questionnairesService.checkNeedQuestionnaire("CAPS", "today")) model.addAttribute("todayCAPS", true);
        if (questionnairesService.checkNeedQuestionnaire("CAPS", "missed")) model.addAttribute("missedCAPS", true);
      }
      
      if (questionnairesService.getLastQuestionnaire("IESR") == null) model.addAttribute("firstIESR", true);
      else {
        if (questionnairesService.checkNeedQuestionnaire("IESR", "today")) model.addAttribute("todayIESR", true);
        if (questionnairesService.checkNeedQuestionnaire("IESR", "missed")) model.addAttribute("missedIESR", true);
      }

      if (questionnairesService.getLastQuestionnaire("BHS") == null) model.addAttribute("firstBHS", true);
      else {
        if (questionnairesService.checkNeedQuestionnaire("BHS", "today")) model.addAttribute("todayBHS", true);
        if (questionnairesService.checkNeedQuestionnaire("BHS", "missed")) model.addAttribute("missedBHS", true);
      }

      if (questionnairesService.getLastQuestionnaire("TOP8") == null) model.addAttribute("firstTOP8", true);
      else {
        if (questionnairesService.checkNeedQuestionnaire("TOP8", "today")) model.addAttribute("todayTOP8", true);     
        if (questionnairesService.checkNeedQuestionnaire("TOP8", "missed")) model.addAttribute("missedTOP8", true);     
      }
    }
    return "user/user-start-page";
  }
    
}
