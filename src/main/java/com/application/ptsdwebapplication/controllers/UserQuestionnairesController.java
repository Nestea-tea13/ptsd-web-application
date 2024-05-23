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
import com.application.ptsdwebapplication.data.QuestionnaireData;
import com.application.ptsdwebapplication.models.questionnaireResults.*;
import com.application.ptsdwebapplication.models.subsidiaryClasses.DatesInterval;
import com.application.ptsdwebapplication.models.subsidiaryClasses.QuestionnaireAnswers;
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
        if (!questionnairesService.checkPeriodQuestionnaire("CAPS")) model.addAttribute("errorCAPSPeriod", true);
        if (!questionnairesService.checkPeriodQuestionnaire("IESR")) model.addAttribute("errorIESRPeriod", true);
        if (!questionnairesService.checkPeriodQuestionnaire("BHS")) model.addAttribute("errorBHSPeriod", true);
        if (!questionnairesService.checkPeriodQuestionnaire("TOP8")) model.addAttribute("errorTOP8Period", true);     
        return "user/questionnaires/questionnaires-list";
    }

    @GetMapping("/{name}")
    public String fillingQuestionnaire(@PathVariable(value = "name") String name, @ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireData.getQuestions(name));
        if (name.equals("IESR") || name.equals("BHS")) model.addAttribute("answerOptions", QuestionnaireData.getSingleAnswerOptions(name));
        else model.addAttribute("answerOptions", QuestionnaireData.getDoubleAnswerOptions(name));
        return QuestionnaireData.getQuestionnaireURL(name);
    }

    @PostMapping("/{name}")
    public String saveQuestionnaireResults(@PathVariable(value = "name") String name, @ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        int id = (questionnairesService.saveQuestionnaire(answers.getAnswers(), name)).getId();
        return "redirect:/questionnaires/{name}/" + id;
    }

    @GetMapping("/{name}/{id}")
    public String getQuestionnaireResults(@PathVariable(value = "id") int id, @PathVariable(value = "name") String name, Model model) {
        if(!questionnairesService.existsQuestionnaireResults(id, name)) {
            return "redirect:/questionnaires/questionnaires-results";
        }
        
        model.addAttribute("questionnare", name);
        model.addAttribute("questions", QuestionnaireData.getQuestions(name));
        if (name.equals("IESR") || name.equals("BHS")) model.addAttribute("answerOptions", QuestionnaireData.getSingleAnswerOptions(name));
        else model.addAttribute("answerOptions", QuestionnaireData.getDoubleAnswerOptions(name));
        model.addAttribute("results", questionnairesService.findQuestionnaireResults(id, name));     
        return "user/questionnaires/questionnaire-answers";
    }

    @GetMapping("/results")
    public String getQuestionnairesListForResults(Model model) {
        model.addAttribute("fullNames", Labels.QuestionnairesNames);
        if (!questionnairesService.existsPassedQuestionnaire("CAPS")) model.addAttribute("nullResultsCAPS", true);
        if (!questionnairesService.existsPassedQuestionnaire("IESR")) model.addAttribute("nullResultsIESR", true);
        if (!questionnairesService.existsPassedQuestionnaire("BHS")) model.addAttribute("nullResultsBHS", true);
        if (!questionnairesService.existsPassedQuestionnaire("TOP8")) model.addAttribute("nullResultsTOP8", true);     
        return "user/questionnaires/questionnaires-list-results";
    }

    @GetMapping("/results/{name}")
    public String getQuestionnaireResults(@PathVariable(value = "name") String name, Model model,
                @ModelAttribute("dates") DatesInterval dates) {
        if(!QuestionnaireData.checkQuestionnaireName(name)) {
            return "redirect:/user/questionnaires/results";
        }
        
        if (name.equals("CAPS")) {
            List<CAPSResults> CAPSResults = questionnairesService.getAllCAPSForUser();
            if (!CAPSResults.isEmpty()) {
                model.addAttribute("results", CAPSResults);
                model.addAttribute("ChartLabelsCAPS", QuestionnaireData.getChartLabelsCAPS(CAPSResults));
                model.addAttribute("ChartDateCAPS", QuestionnaireData.getChartDataCAPS(CAPSResults));        
            }
        } else if (name.equals("IESR")) {
            List<IESRResults> IESRResults = questionnairesService.getAllIESRForUser();
            if (!IESRResults.isEmpty()) {
                model.addAttribute("results", IESRResults);
                model.addAttribute("ChartLabelsIESR", QuestionnaireData.getChartLabelsIESR(IESRResults));
                model.addAttribute("ChartDateIESR", QuestionnaireData.getChartDataIESR(IESRResults));
            }
        } else if (name.equals("BHS")) {
            List<BHSResults> BHSResults = questionnairesService.getAllBHSForUser();
            if (!BHSResults.isEmpty()) {
                model.addAttribute("results", BHSResults);
                model.addAttribute("ChartLabelsBHS", QuestionnaireData.getChartLabelsBHS(BHSResults));
                model.addAttribute("ChartDateBHS", QuestionnaireData.getChartDataBHS(BHSResults));
            } 
        } else {
            List<TOP8Results> TOP8Results = questionnairesService.getAllTOP8ForUser();
            if (!TOP8Results.isEmpty()) {
                model.addAttribute("results", TOP8Results);
                model.addAttribute("ChartLabelsTOP8", QuestionnaireData.getChartLabelsTOP8(TOP8Results));
                model.addAttribute("ChartDateTOP8", QuestionnaireData.getChartDataTOP8(TOP8Results));
            }
        }

        model.addAttribute("questionnare", name);
        model.addAttribute("fullName", QuestionnaireData.getQuestionnairesNames(name));
        model.addAttribute("headers", QuestionnaireData.getQuestionnairesResultsTableHeaders(name));
        model.addAttribute("minDate", questionnairesService.getMinMaxIntervalDate(name, "min"));
        model.addAttribute("maxDate", questionnairesService.getMinMaxIntervalDate(name, "max"));
        return "user/questionnaires/questionnaire-results";
    }

}
