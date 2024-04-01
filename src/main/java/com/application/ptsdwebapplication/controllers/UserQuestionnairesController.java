package com.application.ptsdwebapplication.controllers;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.application.ptsdwebapplication.data.QuestionnaireDataBHS;
import com.application.ptsdwebapplication.data.QuestionnaireDataCAPS;
import com.application.ptsdwebapplication.data.QuestionnaireDataIESR;
import com.application.ptsdwebapplication.data.QuestionnaireDataTOP8;
import com.application.ptsdwebapplication.models.QuestionnaireAnswers;

@Controller
@RequestMapping("/questionnaires")
public class UserQuestionnairesController {
    
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
        model.addAttribute("date", new Date());
        model.addAttribute("result", answers.getgResultCAPS());
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
        model.addAttribute("date", new Date());
        model.addAttribute("result", answers.getgResultIESR());
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
        model.addAttribute("date", new Date());
        model.addAttribute("result", answers.getgResultBHS());
        model.addAttribute("answerOptions", QuestionnaireDataBHS.AnswerOptions);
        return "user/questionnaires/BHS-result";
    }

    @GetMapping("/TOP8")
    public String questionnaireTOP8(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("questions", QuestionnaireDataTOP8.Questions);
        model.addAttribute("answerOptions", QuestionnaireDataTOP8.AnswerOptions);
        return "user/questionnaires/TOP-8";
    }

    @PostMapping("/TOP8")
    public String resultQuestionnaireTOP8(@ModelAttribute("answers") QuestionnaireAnswers answers, Model model) {
        model.addAttribute("date", new Date());
        model.addAttribute("result", answers.getgResultTOP8());
        model.addAttribute("answerOptions", QuestionnaireDataTOP8.AnswerOptions);
        return "user/questionnaires/TOP-8-result";
    }

}
