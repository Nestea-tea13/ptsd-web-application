package com.application.ptsdwebapplication.services;

import java.text.SimpleDateFormat;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.data.QuestionnaireDataBHS;
import com.application.ptsdwebapplication.data.QuestionnaireDataCAPS;
import com.application.ptsdwebapplication.data.QuestionnaireDataIESR;
import com.application.ptsdwebapplication.data.QuestionnaireDataTOP8;
import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Questionnaire;
import com.application.ptsdwebapplication.models.TOP8Results;
import com.application.ptsdwebapplication.repositories.BHSRepository;
import com.application.ptsdwebapplication.repositories.CAPSRepository;
import com.application.ptsdwebapplication.repositories.IESRRepository;
import com.application.ptsdwebapplication.repositories.TOP8Repository;

@Service
@Transactional(readOnly = true)
public class QuestionnairesService {

    private final PeopleService peopleService; 
    private final CAPSRepository capsRepository;
    private final IESRRepository iesrRepository;
    private final BHSRepository bhsRepository;
    private final TOP8Repository top8Repository;

    @Autowired
    public QuestionnairesService(PeopleService peopleService, CAPSRepository capsRepository, IESRRepository iesrRepository,
                        BHSRepository bhsRepository, TOP8Repository top8Repository) {
        this.peopleService = peopleService;
        this.capsRepository = capsRepository;
        this.iesrRepository = iesrRepository;
        this.bhsRepository = bhsRepository;
        this.top8Repository = top8Repository;
    }

    public Boolean existsQuestionnaireResults(int id, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return capsRepository.existsById(id);
        else if (questionnaireName.equals("IESR")) return iesrRepository.existsById(id);
        else if (questionnaireName.equals("BHS")) return bhsRepository.existsById(id);
        else if (questionnaireName.equals("TOP8")) return top8Repository.existsById(id);
        else return false;
    }

    public Questionnaire findQuestionnaireResults(int id, String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return findCAPSResultsById(id);
        else if (questionnaireName.equals("IESR")) return findIESRResultsById(id);
        else if (questionnaireName.equals("BHS")) return findBHSResultsById(id);
        else return findTOP8ResultsById(id);
    }

    public CAPSResults findCAPSResultsById(int id) {
        CAPSResults results = capsRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public IESRResults findIESRResultsById(int id) {
        IESRResults results = iesrRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public BHSResults findBHSResultsById(int id) {
        BHSResults results = bhsRepository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public TOP8Results findTOP8ResultsById(int id) {
        TOP8Results results = top8Repository.findById(id).get();
        results.setAnswers();
        return results;
    }

    public List<CAPSResults> getAllCAPSForUser() {
        return capsRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<IESRResults> getAllIESRForUser() {
        return iesrRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<BHSResults> getAllBHSForUser() {
        return bhsRepository.findByUser(peopleService.getCurrentPerson());
    }

    public List<TOP8Results> getAllTOP8ForUser() {
        return top8Repository.findByUser(peopleService.getCurrentPerson());
    }

    @Transactional
    public Questionnaire saveQuestionnaire(String[] answers, String questionnaireName) {
        Questionnaire saveQuestionnaire;
        if (questionnaireName.equals("CAPS")) {
            saveQuestionnaire = new CAPSResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsCAPS().add(results); // получение данных после ленивой загрузки?
            capsRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("IESR")) {
            saveQuestionnaire = new IESRResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsIESR().add(results);
            iesrRepository.save(saveQuestionnaire);
        } else if (questionnaireName.equals("BHS")) {
            saveQuestionnaire = new BHSResults(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsBHS().add(results);
            bhsRepository.save(saveQuestionnaire);
        } else {
            saveQuestionnaire = new TOP8Results(answers, peopleService.getCurrentPerson());
            //peopleService.getCurrentPerson().getResultsTOP8().add(results);
            top8Repository.save(saveQuestionnaire);
        }
        return saveQuestionnaire;
    }

    public String[] getQuestions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.Questions;
        else if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.Questions;
        else if (questionnaireName.equals("BHS")) return QuestionnaireDataBHS.Questions;
        else return QuestionnaireDataTOP8.Questions;
    }

    public String[] getSingleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.AnswerOptions;
        else return QuestionnaireDataBHS.AnswerOptions;
    }

    public String[][] getDoubleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.AnswerOptions;
        else return QuestionnaireDataTOP8.AnswerOptions;
    }

    public String getQuestionnaireURL(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return "user/questionnaires/CAPS";
        else if (questionnaireName.equals("IESR")) return "user/questionnaires/IESR";
        else if (questionnaireName.equals("BHS")) return "user/questionnaires/BHS";
        else if (questionnaireName.equals("TOP8")) return "user/questionnaires/TOP8";
        else return "redirect:/questionnaires";
    }
  
    public String[] getChartLabelsCAPS(List<CAPSResults> CAPSResults) {
        String[] ChartLabels = new String[CAPSResults.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < CAPSResults.size(); i++) 
            ChartLabels[i] = formatter.format(CAPSResults.get(i).getDate());
        return ChartLabels;
    }

    public String[] getChartLabelsIESR(List<IESRResults> IESRResults) {
        String[] ChartLabels = new String[IESRResults.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < IESRResults.size(); i++) 
            ChartLabels[i] = formatter.format(IESRResults.get(i).getDate());
        return ChartLabels;
    }

    public String[] getChartLabelsBHS(List<BHSResults> BHSResults) {
        String[] ChartLabels = new String[BHSResults.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < BHSResults.size(); i++) 
            ChartLabels[i] = formatter.format(BHSResults.get(i).getDate());
        return ChartLabels;
    }

    public String[] getChartLabelsTOP8(List<TOP8Results> TOP8Results) {
        String[] ChartLabels = new String[TOP8Results.size()];
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        for(int i = 0; i < TOP8Results.size(); i++) 
            ChartLabels[i] = formatter.format(TOP8Results.get(i).getDate());
        return ChartLabels;
    }

    public int[][] getChartDataCAPS(List<CAPSResults> CAPSResults) {
        int[][] ChartDate = new int[2][CAPSResults.size()];
        for(int i = 0; i < CAPSResults.size(); i++) {
            ChartDate[0][i] = CAPSResults.get(i).getResultFrequency();
            ChartDate[1][i] = CAPSResults.get(i).getResultIntensity();
        }
        return ChartDate;
    }

    public int[][] getChartDataIESR(List<IESRResults> IESRResults) {
        int[][] ChartDate = new int[3][IESRResults.size()];
        for(int i = 0; i < IESRResults.size(); i++) {
            ChartDate[0][i] = IESRResults.get(i).getResultIN();
            ChartDate[1][i] = IESRResults.get(i).getResultAV();
            ChartDate[2][i] = IESRResults.get(i).getResultAR();
        }
        return ChartDate;
    }

    public int[] getChartDataBHS(List<BHSResults> BHSResults) {
        int[] ChartDate = new int[BHSResults.size()];
        for(int i = 0; i < BHSResults.size(); i++)
            ChartDate[i] = BHSResults.get(i).getResult();
        return ChartDate;
    }

    public int[] getChartDataTOP8(List<TOP8Results> TOP8Results) {
        int[] ChartDate = new int[TOP8Results.size()];
        for(int i = 0; i < TOP8Results.size(); i++)
            ChartDate[i] = TOP8Results.get(i).getResult();
        return ChartDate;
    }

}
