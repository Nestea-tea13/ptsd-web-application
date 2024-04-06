package com.application.ptsdwebapplication.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.data.QuestionnaireDataBHS;
import com.application.ptsdwebapplication.data.QuestionnaireDataCAPS;
import com.application.ptsdwebapplication.data.QuestionnaireDataIESR;
import com.application.ptsdwebapplication.data.QuestionnaireDataTOP8;
import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.QuestionnaireAnswers;
import com.application.ptsdwebapplication.models.TOP8Results;
import com.application.ptsdwebapplication.services.QuestionnairesService;

@Controller
@RequestMapping("/questionnaires")
public class UserQuestionnairesController {

    private final QuestionnairesService questionnairesService;
    
    public UserQuestionnairesController(QuestionnairesService questionnairesService) {
        this.questionnairesService = questionnairesService;
    }

    @GetMapping()
    public String getQuestionnairesList(Model model) {
        return "user/questionnaires/questionnaires-list";
    }

    @GetMapping("/CAPS")
    public String questionnaireCAPS(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireDataCAPS.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataCAPS.AnswerOptions);
        return "user/questionnaires/CAPS";
    }

    @PostMapping("/CAPS")
    public String resultQuestionnaireCAPS(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("results", questionnairesService.saveCAPS(answers.getAnswers()));
        model.addAttribute("answerOptions", QuestionnaireDataCAPS.AnswerOptions);
        return "user/questionnaires/CAPS-result";
    }


    @GetMapping("/IESR")
    public String questionnaireIESR(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireDataIESR.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataIESR.AnswerOptions);
        return "user/questionnaires/IES-R";
    }

    @PostMapping("/IESR")
    public String resultQuestionnaireIESR(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("results", questionnairesService.saveIESR(answers.getAnswers()));
        model.addAttribute("answerOptions", QuestionnaireDataIESR.AnswerOptions);
        return "user/questionnaires/IES-R-result";
    }

    @GetMapping("/BHS")
    public String questionnaireBHS(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireDataBHS.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataBHS.AnswerOptions);
        return "user/questionnaires/BHS";
    }

    @PostMapping("/BHS")
    public String resultQuestionnaireBHS(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("results", questionnairesService.saveBHS(answers.getAnswers())); 
        model.addAttribute("answerOptions", QuestionnaireDataBHS.AnswerOptions);
        return "user/questionnaires/BHS-result";
    }

    @GetMapping("/TOP8")
    public String questionnaireTOP8(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireDataTOP8.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataTOP8.AnswerOptions);
        return "user/questionnaires/TOP-8";
    }

    // ЗАМЕНИТЬ вывод результатов этих функций?
    @PostMapping("/TOP8")
    public String resultQuestionnaireTOP8(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) { 
        model.addAttribute("results", questionnairesService.saveTOP8(answers.getAnswers())); 
        model.addAttribute("answerOptions", QuestionnaireDataTOP8.AnswerOptions);
        return "user/questionnaires/TOP-8-result";
    }

    @GetMapping("/TOP8/{id}")
    public String getTOP8Results(@PathVariable(value = "id") int id, Model model) {
        if(!questionnairesService.existsResultsById(id, "TOP8")) {
            return "redirect:/adminpage/users";
        }
        
        model.addAttribute("questions", QuestionnaireDataTOP8.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataTOP8.AnswerOptions);
        model.addAttribute("results", questionnairesService.findTOP8ResultsById(id));
        return "user/questionnaires/questionnaire-answers";
    }

    @GetMapping("/results")
    public String getQuestionnairesResults(Model model) {
        List<CAPSResults> CAPSResults = questionnairesService.getAllCAPSForUser();
        List<IESRResults> IESRResults = questionnairesService.getAllIESRForUser();
        List<BHSResults> BHSResults = questionnairesService.getAllBHSForUser();
        List<TOP8Results> TOP8Results = questionnairesService.getAllTOP8ForUser();

        if (!CAPSResults.isEmpty()) model.addAttribute("CAPSResults", CAPSResults);
        if (!IESRResults.isEmpty()) model.addAttribute("IESRResults", IESRResults);
        if (!BHSResults.isEmpty()) model.addAttribute("BHSResults", BHSResults);
        if (!TOP8Results.isEmpty()) model.addAttribute("TOP8Results", TOP8Results);

        model.addAttribute("headers", Labels.QuestionnairesResultsTableHeaders);
        return "user/questionnaires/results";
    }

}
