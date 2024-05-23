package com.application.ptsdwebapplication.models.questionnaireResults;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.application.ptsdwebapplication.interfaces.Questionnaire;
import com.application.ptsdwebapplication.models.Patient;

@Entity
@Table(name = "TOP8Results")
public class TOP8Results extends QuestionnaireResults implements Questionnaire {

    @Column(name = "result")
    private int result;

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

    @Transient
    private String[] answers;

    public TOP8Results() { super(); }

    public TOP8Results(String[] answers, Patient patient) {
        this.answers = answers;
        this.patient = patient;
        this.date = new Date();
        this.result = getResultTOP8();
        this.answer1 = answers[0];
        this.answer2 = answers[1];
        this.answer3 = answers[2];
        this.answer4 = answers[3];
        this.answer5 = answers[4];
        this.answer6 = answers[5];
        this.answer7 = answers[6];
        this.answer8 = answers[7];
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
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

    public int getResultTOP8() {
        int result = 0;
        for(int i = 0; i < answers.length; i++)
            result += Integer.parseInt(answers[i]);
        return result;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }

    public void setAnswers() {
        answers = new String[8];
        this.answers[0] = this.answer1;
        this.answers[1] = this.answer2;
        this.answers[2] = this.answer3;
        this.answers[3] = this.answer4;
        this.answers[4] = this.answer5;
        this.answers[5] = this.answer6;
        this.answers[6] = this.answer7;
        this.answers[7] = this.answer8;
    }
    
}
