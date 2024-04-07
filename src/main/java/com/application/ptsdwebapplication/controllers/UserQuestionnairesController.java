package com.application.ptsdwebapplication.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.ptsdwebapplication.data.Labels;
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
    
    @Autowired
    public UserQuestionnairesController(QuestionnairesService questionnairesService) {
        this.questionnairesService = questionnairesService;
    }

    @GetMapping()
    public String getQuestionnairesList(Model model) {
        return "user/questionnaires/questionnaires-list";
    }

    @GetMapping("/{name}")
    public String fillingQuestionnaire(@PathVariable(value = "name") String name, @ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", questionnairesService.getQuestions(name));
        if (name.equals("IESR") || name.equals("BHS")) model.addAttribute("answerOptions", questionnairesService.getSingleAnswerOptions(name));
        else model.addAttribute("answerOptions", questionnairesService.getDoubleAnswerOptions(name));
        return questionnairesService.getQuestionnaireURL(name);
    }

    @PostMapping("/{name}")
    public String saveQuestionnaireResults(@PathVariable(value = "name") String name, @ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questionnare", name);
        model.addAttribute("questions", questionnairesService.getQuestions(name));
        if (name.equals("IESR") || name.equals("BHS")) model.addAttribute("answerOptions", questionnairesService.getSingleAnswerOptions(name));
        else model.addAttribute("answerOptions", questionnairesService.getDoubleAnswerOptions(name));
        model.addAttribute("results", questionnairesService.saveQuestionnaire(answers.getAnswers(), name));     
        return "user/questionnaires/questionnaire-answers";
    }

    @GetMapping("/{name}/{id}")
    public String getQuestionnaireResults(@PathVariable(value = "id") int id, @PathVariable(value = "name") String name, Model model) {
        if(!questionnairesService.existsQuestionnaireResults(id, name)) {
            return "redirect:/questionnaires/questionnaires-results";
        }
        
        model.addAttribute("questionnare", name);
        model.addAttribute("questions", questionnairesService.getQuestions(name));
        if (name.equals("IESR") || name.equals("BHS")) model.addAttribute("answerOptions", questionnairesService.getSingleAnswerOptions(name));
        else model.addAttribute("answerOptions", questionnairesService.getDoubleAnswerOptions(name));
        model.addAttribute("results", questionnairesService.findQuestionnaireResults(id, name));     
        return "user/questionnaires/questionnaire-answers";
    }

    @GetMapping("/results")
    public String getQuestionnairesResults(Model model) {
        List<CAPSResults> CAPSResults = questionnairesService.getAllCAPSForUser();
        List<IESRResults> IESRResults = questionnairesService.getAllIESRForUser();
        List<BHSResults> BHSResults = questionnairesService.getAllBHSForUser();
        List<TOP8Results> TOP8Results = questionnairesService.getAllTOP8ForUser();

        if (!CAPSResults.isEmpty()) {
            model.addAttribute("CAPSResults", CAPSResults);
            model.addAttribute("ChartLabelsCAPS", questionnairesService.getChartLabelsCAPS(CAPSResults));
            model.addAttribute("ChartDateCAPS", questionnairesService.getChartDataCAPS(CAPSResults));        
        }
        if (!IESRResults.isEmpty()) {
            model.addAttribute("IESRResults", IESRResults);
            model.addAttribute("ChartLabelsIESR", questionnairesService.getChartLabelsIESR(IESRResults));
            model.addAttribute("ChartDateIESR", questionnairesService.getChartDataIESR(IESRResults));
        }
        if (!BHSResults.isEmpty()) {
            model.addAttribute("BHSResults", BHSResults);
            model.addAttribute("ChartLabelsBHS", questionnairesService.getChartLabelsBHS(BHSResults));
            model.addAttribute("ChartDateBHS", questionnairesService.getChartDataBHS(BHSResults));
        }    
        if (!TOP8Results.isEmpty()) {
            model.addAttribute("TOP8Results", TOP8Results);
            model.addAttribute("ChartLabelsTOP8", questionnairesService.getChartLabelsTOP8(TOP8Results));
            model.addAttribute("ChartDateTOP8", questionnairesService.getChartDataTOP8(TOP8Results));
        }

        model.addAttribute("headers", Labels.QuestionnairesResultsTableHeaders);
        return "user/questionnaires/questionnaires-results";
    }

}
