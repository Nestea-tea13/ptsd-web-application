package com.application.ptsdwebapplication.data;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.TOP8Results;

public class QuestionnaireData {

    static SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
    
    public static Boolean checkQuestionnaireName(String questionnaireName) {
        List<String> names = new ArrayList<>(Arrays.asList("CAPS", "IESR", "BHS", "TOP8"));
        if (questionnaireName == null) return false;
        if(names.contains(questionnaireName)) return true;
        else return false;
    }

    public static String[] getQuestions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.Questions;
        else if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.Questions;
        else if (questionnaireName.equals("BHS")) return QuestionnaireDataBHS.Questions;
        else return QuestionnaireDataTOP8.Questions;
    }
    
    public static String getQuestionnairesNames(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return Labels.QuestionnairesNames[0];
        else if (questionnaireName.equals("IESR")) return Labels.QuestionnairesNames[1];
        else if (questionnaireName.equals("BHS")) return Labels.QuestionnairesNames[2];
        else return Labels.QuestionnairesNames[3];
    }

    public static String[] getQuestionnairesResultsTableHeaders(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return Labels.QuestionnairesResultsTableHeaders[0];
        else if (questionnaireName.equals("IESR")) return Labels.QuestionnairesResultsTableHeaders[1];
        else if (questionnaireName.equals("BHS")) return Labels.QuestionnairesResultsTableHeaders[2];
        else return Labels.QuestionnairesResultsTableHeaders[3];
    }

    public static String[] getSingleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("IESR")) return QuestionnaireDataIESR.AnswerOptions;
        else return QuestionnaireDataBHS.AnswerOptions;
    }

    public static String[][] getDoubleAnswerOptions(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return QuestionnaireDataCAPS.AnswerOptions;
        else return QuestionnaireDataTOP8.AnswerOptions;
    }

    public static String getQuestionnaireURL(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return "user/questionnaires/CAPS";
        else if (questionnaireName.equals("IESR")) return "user/questionnaires/IESR";
        else if (questionnaireName.equals("BHS")) return "user/questionnaires/BHS";
        else if (questionnaireName.equals("TOP8")) return "user/questionnaires/TOP8";
        else return "redirect:/questionnaires";
    }
  
    public static String[] getChartLabelsCAPS(List<CAPSResults> CAPSResults) {
        String[] ChartLabels = new String[CAPSResults.size()];
        for(int i = 0; i < CAPSResults.size(); i++) 
            ChartLabels[i] = formatter.format(CAPSResults.get(i).getDate());
        return ChartLabels;
    }

    public static String[] getChartLabelsIESR(List<IESRResults> IESRResults) {
        String[] ChartLabels = new String[IESRResults.size()];
        for(int i = 0; i < IESRResults.size(); i++) 
            ChartLabels[i] = formatter.format(IESRResults.get(i).getDate());
        return ChartLabels;
    }

    public static String[] getChartLabelsBHS(List<BHSResults> BHSResults) {
        String[] ChartLabels = new String[BHSResults.size()];
        for(int i = 0; i < BHSResults.size(); i++) 
            ChartLabels[i] = formatter.format(BHSResults.get(i).getDate());
        return ChartLabels;
    }

    public static String[] getChartLabelsTOP8(List<TOP8Results> TOP8Results) {
        String[] ChartLabels = new String[TOP8Results.size()];
        for(int i = 0; i < TOP8Results.size(); i++) 
            ChartLabels[i] = formatter.format(TOP8Results.get(i).getDate());
        return ChartLabels;
    }

    public static int[][] getChartDataCAPS(List<CAPSResults> CAPSResults) {
        int[][] ChartDate = new int[2][CAPSResults.size()];
        for(int i = 0; i < CAPSResults.size(); i++) {
            ChartDate[0][i] = CAPSResults.get(i).getResultFrequency();
            ChartDate[1][i] = CAPSResults.get(i).getResultIntensity();
        }
        return ChartDate;
    }

    public static int[][] getChartDataIESR(List<IESRResults> IESRResults) {
        int[][] ChartDate = new int[3][IESRResults.size()];
        for(int i = 0; i < IESRResults.size(); i++) {
            ChartDate[0][i] = IESRResults.get(i).getResultIN();
            ChartDate[1][i] = IESRResults.get(i).getResultAV();
            ChartDate[2][i] = IESRResults.get(i).getResultAR();
        }
        return ChartDate;
    }

    public static int[] getChartDataBHS(List<BHSResults> BHSResults) {
        int[] ChartDate = new int[BHSResults.size()];
        for(int i = 0; i < BHSResults.size(); i++)
            ChartDate[i] = BHSResults.get(i).getResult();
        return ChartDate;
    }

    public static int[] getChartDataTOP8(List<TOP8Results> TOP8Results) {
        int[] ChartDate = new int[TOP8Results.size()];
        for(int i = 0; i < TOP8Results.size(); i++)
            ChartDate[i] = TOP8Results.get(i).getResult();
        return ChartDate;
    }

    public static String getErrorPeriodName(String questionnaireName) {
        if (questionnaireName.equals("CAPS")) return "errorCAPSPeriod";
        else if (questionnaireName.equals("IESR")) return "errorIESRPeriod";
        else if (questionnaireName.equals("BHS")) return "errorBHSPeriod";
        else return "errorTOP8Period";
    }

}
