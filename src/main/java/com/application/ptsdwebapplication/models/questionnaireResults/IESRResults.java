package com.application.ptsdwebapplication.models.questionnaireResults;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;

@Entity
@Table(name = "IESRResults")
public class IESRResults extends QuestionnaireResults implements Questionnaire {

    @Column(name = "result_in")
    private int resultIN;

    @Column(name = "result_av")
    private int resultAV;

    @Column(name = "result_ar")
    private int resultAR;

    @Column(name = "answer1")
    private String answer1;

    @Column(name = "answer2")
    private String answer2;

    @Column(name = "answer3")
    private String answer3;

    @Column(name = "answer4")
    private String answer4;

    @Column(name = "answer5")
    private String answer5;

    @Column(name = "answer6")
    private String answer6;

    @Column(name = "answer7")
    private String answer7;

    @Column(name = "answer8")
    private String answer8;

    @Column(name = "answer9")
    private String answer9;

    @Column(name = "answer10")
    private String answer10;

    @Column(name = "answer11")
    private String answer11;

    @Column(name = "answer12")
    private String answer12;

    @Column(name = "answer13")
    private String answer13;

    @Column(name = "answer14")
    private String answer14;

    @Column(name = "answer15")
    private String answer15;

    @Column(name = "answer16")
    private String answer16;

    @Column(name = "answer17")
    private String answer17;

    @Column(name = "answer18")
    private String answer18;

    @Column(name = "answer19")
    private String answer19;

    @Column(name = "answer20")
    private String answer20;

    @Column(name = "answer21")
    private String answer21;

    @Column(name = "answer22")
    private String answer22;

    @Transient
    private String[] answers;

    public IESRResults() { super(); }

    public IESRResults(String[] answers, Patient patient) {
        this.answers = answers;
        this.patient = patient;
        this.date = new Date();
        int[] results = getResultIESR();
        this.resultIN = results[0];
        this.resultAV = results[1];
        this.resultAR = results[2];
        this.answer1 = answers[0];
        this.answer2 = answers[1];
        this.answer3 = answers[2];
        this.answer4 = answers[3];
        this.answer5 = answers[4];
        this.answer6 = answers[5];
        this.answer7 = answers[6];
        this.answer8 = answers[7];
        this.answer9 = answers[8];
        this.answer10 = answers[9];
        this.answer11 = answers[10];
        this.answer12 = answers[11];
        this.answer13 = answers[12];
        this.answer14 = answers[13];
        this.answer15 = answers[14];
        this.answer16 = answers[15];
        this.answer17 = answers[16];
        this.answer18 = answers[17];
        this.answer19 = answers[18];
        this.answer20 = answers[19];
        this.answer21 = answers[20];
        this.answer22 = answers[21];
    }

    public int getResultIN() {
        return resultIN;
    }

    public void setResultIN(int resultIN) {
        this.resultIN = resultIN;
    }

    public int getResultAV() {
        return resultAV;
    }

    public void setResultAV(int resultAV) {
        this.resultAV = resultAV;
    }

    public int getResultAR() {
        return resultAR;
    }

    public void setResultAR(int resultAR) {
        this.resultAR = resultAR;
    }

    public String getAnswer1() { return answer1; }

    public void setAnswer1(String answer1) { this.answer1 = answer1; }

    public String getAnswer2() { return answer2; }

    public void setAnswer2(String answer2) { this.answer2 = answer2; }

    public String getAnswer3() { return answer3; }

    public void setAnswer3(String answer3) { this.answer3 = answer3; }

    public String getAnswer4() { return answer4; }

    public void setAnswer4(String answer4) { this.answer4 = answer4; }

    public String getAnswer5() { return answer5; }

    public void setAnswer5(String answer5) { this.answer5 = answer5; }

    public String getAnswer6() { return answer6; }

    public void setAnswer6(String answer6) { this.answer6 = answer6; }

    public String getAnswer7() { return answer7; }

    public void setAnswer7(String answer7) { this.answer7 = answer7; }

    public String getAnswer8() { return answer8; }

    public void setAnswer8(String answer8) { this.answer8 = answer8; }

    public String getAnswer9() { return answer9; }

    public void setAnswer9(String answer9) { this.answer9 = answer9; }

    public String getAnswer10() { return answer10; }

    public void setAnswer10(String answer10) { this.answer10 = answer10; }

    public String getAnswer11() { return answer11; }

    public void setAnswer11(String answer11) { this.answer11 = answer11; }

    public String getAnswer12() { return answer12; }

    public void setAnswer12(String answer12) { this.answer12 = answer12; }

    public String getAnswer13() { return answer13; }

    public void setAnswer13(String answer13) { this.answer13 = answer13; }

    public String getAnswer14() { return answer14; }

    public void setAnswer14(String answer14) { this.answer14 = answer14; }

    public String getAnswer15() { return answer15; }

    public void setAnswer15(String answer15) { this.answer15 = answer15; }

    public String getAnswer16() { return answer16; }

    public void setAnswer16(String answer16) { this.answer16 = answer16; }

    public String getAnswer17() { return answer17; }

    public void setAnswer17(String answer17) { this.answer17 = answer17; }

    public String getAnswer18() { return answer18; }

    public void setAnswer18(String answer18) { this.answer18 = answer18; }

    public String getAnswer19() { return answer19; }

    public void setAnswer19(String answer19) { this.answer19 = answer19; }

    public String getAnswer20() { return answer20; }

    public void setAnswer20(String answer20) { this.answer20 = answer20; }

    public String getAnswer21() { return answer21; }

    public void setAnswer21(String answer21) { this.answer21 = answer21; }

    public String getAnswer22() { return answer22; }

    public void setAnswer22(String answer22) { this.answer22 = answer22; }

    public int[] getResultIESR() {
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

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setAnswers() {
        answers = new String[22];
        this.answers[0] = this.answer1;
        this.answers[1] = this.answer2;
        this.answers[2] = this.answer3;
        this.answers[3] = this.answer4;
        this.answers[4] = this.answer5;
        this.answers[5] = this.answer6;
        this.answers[6] = this.answer7;
        this.answers[7] = this.answer8;
        this.answers[8] = this.answer9;
        this.answers[9] = this.answer10;
        this.answers[10] = this.answer11;
        this.answers[11] = this.answer12;
        this.answers[12] = this.answer13;
        this.answers[13] = this.answer14;
        this.answers[14] = this.answer15;
        this.answers[15] = this.answer16;
        this.answers[16] = this.answer17;
        this.answers[17] = this.answer18;
        this.answers[18] = this.answer19;
        this.answers[19] = this.answer20;
        this.answers[20] = this.answer21;
        this.answers[21] = this.answer22;
    }
    
}
