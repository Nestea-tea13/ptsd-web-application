package com.application.ptsdwebapplication.controllers;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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

import com.application.ptsdwebapplication.data.QuestionnaireData;
import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.DatesInterval;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;
import com.application.ptsdwebapplication.models.TOP8Results;
import com.application.ptsdwebapplication.services.PDFGeneratorService;
import com.application.ptsdwebapplication.services.PeopleService;
import com.application.ptsdwebapplication.services.QuestionnairesService;
import com.application.ptsdwebapplication.util.DatesValidator;
import com.itextpdf.text.DocumentException;

@Controller
@RequestMapping("/questionnaires")
public class PDFExportController {

    @Autowired
    PDFGeneratorService pdfGeneratorService;
    private final QuestionnairesService questionnairesService;
    private final PeopleService peopleService;
    private final DatesValidator datesValidator;

    @Autowired
    public PDFExportController(QuestionnairesService questionnairesService, PeopleService peopleService, DatesValidator datesValidator) {
        this.questionnairesService = questionnairesService;
        this.peopleService = peopleService;
        this.datesValidator = datesValidator;
    }
 
    @GetMapping("/{name}/{id}/pdf")
    public String createPdfWithQuestionnaireAnswers(@PathVariable(value = "id") int id, @PathVariable(value = "name") String name, 
                                        HttpServletResponse response, Model model)  throws IOException, DocumentException {
        if(!questionnairesService.existsQuestionnaireResults(id, name)) {
            return "redirect:/questionnaires/results";
        }

        Questionnaire questionnaire = questionnairesService.findQuestionnaireResults(id, name);
        Person currentPerson = peopleService.getCurrentPerson();

        response.setContentType("application/pdf");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
 
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + dateFormatter.format(questionnaire.getDate()) + "_" + name + ".pdf";
        response.setHeader(headerKey, headerValue);
 
        pdfGeneratorService.exportQuestionnaireAnswers(response, currentPerson, name, questionnaire);
        return "redirect:/questionnaires/{name}/{id}";
    }

    @GetMapping("/{name}/pdf")
    public String createPdfWithQuestionnaireResultsTable(@PathVariable(value = "name") String name,
                HttpServletResponse response, Model model)  throws IOException, DocumentException {
        if(!QuestionnaireData.checkQuestionnaireName(name)) {
            return "redirect:/questionnaires/results";
        }

        Person currentPerson = peopleService.getCurrentPerson();
        response.setContentType("application/pdf");
 
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + name + "_Results.pdf";
        response.setHeader(headerKey, headerValue);
 
        pdfGeneratorService.exportQuestionnaireTable(response, currentPerson, name);
        return "redirect:/questionnaires/results";
    }

    @PostMapping("/{name}/pdf")
    public String createPdfWithQuestionnaireAnswersBetweenDates(@PathVariable(value = "name") String name,
                @ModelAttribute("dates") @Valid DatesInterval dates, BindingResult bindingResult,
                HttpServletResponse response, Model model)  throws IOException, DocumentException {

        if(!QuestionnaireData.checkQuestionnaireName(name)) {
            return "redirect:/questionnaires/results";
        }

        datesValidator.validate(dates, name, bindingResult);
        if (bindingResult.hasErrors()) {
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

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Person currentPerson = peopleService.getCurrentPerson();
        response.setContentType("application/pdf");
 
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + name + "_Answers_" + dateFormatter.format(dates.getStart()) + "_" + dateFormatter.format(dates.getEnd()) + ".pdf";
        response.setHeader(headerKey, headerValue);
 
        pdfGeneratorService.exportQuestionnairesAnswersBerweenDates(response, currentPerson, name, dates.getStart(), dates.getEnd());
        return "redirect:/questionnaires/results/{name}";
    }
    
}
