package com.application.ptsdwebapplication.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class QuestionnaireAnswers {
    
    private String[] answers;

    public QuestionnaireAnswers() {}

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public int getgResultTOP8() {
        int result = 0;
        for(int i = 0; i < answers.length; i++)
            result += Integer.parseInt(answers[i]);
        return result;
    }

    public int[] getgResultCAPS() {
        int[] result = {0, 0};
        for(int i = 0; i < answers.length; i = i + 2) {
            result[0] += Integer.parseInt(answers[i]);
            result[1] += Integer.parseInt(answers[i + 1]);
        }
        return result;
    }

    public int getgResultBHS() {
        int result = 0;
        List<Integer> weightyTrue = new ArrayList<>(Arrays.asList(2, 4, 7, 9, 11, 12, 14, 16, 17, 18, 20));
        List<Integer> weightyFalse = new ArrayList<>(Arrays.asList(1, 3, 5, 6, 8, 10, 13, 15, 19));     

        for(int i = 0; i < answers.length; i++) {
            if (weightyTrue.contains(i + 1)) 
                if (Integer.parseInt(answers[i]) == 0) result++;
            if (weightyFalse.contains(i + 1)) 
                if (Integer.parseInt(answers[i]) == 1) result++;
        }
        return result;
    }

    public int[] getgResultIESR() {
        int[] result = {0, 0, 0};
        int[] answerWeight = {0, 1, 3, 5};
        List<Integer> IN = new ArrayList<>(Arrays.asList(1, 2, 3, 6, 9, 16, 20));
        List<Integer> AV = new ArrayList<>(Arrays.asList(5, 7, 8, 11, 12, 13, 17, 22));
        List<Integer> AR = new ArrayList<>(Arrays.asList(4, 10, 14, 15, 18, 19, 21));

        for(int i = 0; i < answers.length; i++) {
            if (IN.contains(i + 1)) result[0] += answerWeight[Integer.parseInt(answers[i])];
            if (AV.contains(i + 1)) result[1] += answerWeight[Integer.parseInt(answers[i])];
            if (AR.contains(i + 1)) result[2] += answerWeight[Integer.parseInt(answers[i])];
        }
        return result;
    }

}
