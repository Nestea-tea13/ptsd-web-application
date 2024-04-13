package com.application.ptsdwebapplication.services;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.application.ptsdwebapplication.data.QuestionnaireData;
import com.application.ptsdwebapplication.data.QuestionnaireDataBHS;
import com.application.ptsdwebapplication.data.QuestionnaireDataCAPS;
import com.application.ptsdwebapplication.data.QuestionnaireDataIESR;
import com.application.ptsdwebapplication.data.QuestionnaireDataTOP8;
import com.application.ptsdwebapplication.models.BHSResults;
import com.application.ptsdwebapplication.models.CAPSResults;
import com.application.ptsdwebapplication.models.IESRResults;
import com.application.ptsdwebapplication.models.Person;
import com.application.ptsdwebapplication.models.Questionnaire;
import com.application.ptsdwebapplication.models.TOP8Results;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

@Service
public class PDFGeneratorService {

    private final QuestionnairesService questionnairesService;
    public static final String FONT = "Arial.ttf";

    public PDFGeneratorService(QuestionnairesService questionnairesService) {
        this.questionnairesService = questionnairesService;
    }

    public void exportQuestionnaireAnswers(HttpServletResponse response, Person person, 
            String questionnaireName, Questionnaire questionnaire) throws IOException, DocumentException {

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
 
        document.open();

        Font fontHeader = new Font(bf, 16, Font.NORMAL);
        Paragraph headerParagraph = new Paragraph(QuestionnaireData.getQuestionnairesNames(questionnaireName), fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headerParagraph);
 
        Font fontParagraph = new Font(bf, 12, Font.NORMAL);

        String[] questions = QuestionnaireData.getQuestions(questionnaireName);
        String[] answers = questionnaire.getAnswers();
        Font fontBold = new Font(bf, 12, Font.BOLD);
        document.add((new Paragraph("\nФИО: " + person.getSername() + ' ' + person.getName() + ' ' + person.getPatronymic() +"\n", fontBold)));
        document.add((new Paragraph("Дата прохождения: " + questionnaire.getDate() +"\n", fontBold)));

        if (questionnaireName.equals("CAPS")) {
            CAPSResults q = (CAPSResults) questionnaire;
            document.add((new Paragraph("Частота: " + q.getResultFrequency() +"\n", fontBold)));
            document.add((new Paragraph("Интенсивность: " + q.getResultIntensity() +"\n", fontBold)));
        } else if (questionnaireName.equals("IESR")) {
            IESRResults q = (IESRResults) questionnaire;
            document.add((new Paragraph("Шкала «вторжение»: " + q.getResultIN() +"\n", fontBold)));
            document.add((new Paragraph("Шкала «избегание»: " + q.getResultAV() +"\n", fontBold)));
            document.add((new Paragraph("Шкала «возбудимость»: " + q.getResultAR() +"\n", fontBold)));
        } else if (questionnaireName.equals("BHS")) {
            BHSResults q = (BHSResults) questionnaire;
            document.add((new Paragraph("Результат: " + q.getResult() +"\n", fontBold)));
        } else {
            TOP8Results q = (TOP8Results) questionnaire;
            document.add((new Paragraph("Результат: " + q.getResult() +"\n", fontBold)));
        }

        document.add((new Paragraph("\nОтветы:\n", fontBold)));
        for(int i = 0; i < questions.length; i++) { 
            document.add((new Paragraph((i + 1) + ") " + questions[i] +"\n", fontParagraph))); 
            if (questionnaireName.equals("CAPS")) document.add((new Paragraph("  - " + QuestionnaireDataCAPS.AnswerOptions[i][Integer.parseInt(answers[i])] +"\n", fontBold)));
            else if (questionnaireName.equals("IESR")) document.add((new Paragraph("  - " + QuestionnaireDataIESR.AnswerOptions[Integer.parseInt(answers[i])] +"\n", fontBold)));
            else if (questionnaireName.equals("BHS")) document.add((new Paragraph("  - " + QuestionnaireDataBHS.AnswerOptions[Integer.parseInt(answers[i])] +"\n", fontBold)));
            else document.add((new Paragraph("  - " + QuestionnaireDataTOP8.AnswerOptions[i][Integer.parseInt(answers[i])] +"\n", fontBold)));
        }

        document.close();
    }

    public void exportQuestionnairesAnswersBerweenDates(HttpServletResponse response, Person person, 
            String questionnaireName, Date start, Date end) throws IOException, DocumentException {

        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
 
        document.open();

        Font fontHeader = new Font(bf, 16, Font.NORMAL);
        Paragraph headerParagraph = new Paragraph(QuestionnaireData.getQuestionnairesNames(questionnaireName), fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headerParagraph);
 
        Font fontParagraph = new Font(bf, 12, Font.NORMAL);
        Font fontBold = new Font(bf, 12, Font.BOLD);
        document.add((new Paragraph("\nФИО: " + person.getSername() + ' ' + person.getName() + ' ' + person.getPatronymic(), fontBold)));

        String[] questions = QuestionnaireData.getQuestions(questionnaireName);

        int num = 1;
        if(questionnaireName.equals("CAPS")) {
            List<CAPSResults> questionnaires = questionnairesService.getCAPSResultsBetweenDates(start, end);
            for(CAPSResults q : questionnaires) {
                document.add((new Paragraph("\n\n№ " + num++ +"\n", fontBold)));
                document.add((new Paragraph("Дата прохождения: " + q.getDate() +"\n", fontBold)));
                document.add((new Paragraph("Частота: " + q.getResultFrequency() +"\n", fontBold)));
                document.add((new Paragraph("Интенсивность: " + q.getResultIntensity() +"\n", fontBold)));
                document.add((new Paragraph("Ответы:\n", fontBold)));
                q.setAnswers();
                String[] answers = q.getAnswers();
                for(int i = 0; i < questions.length; i++) { 
                    document.add((new Paragraph((i + 1) + ") " + questions[i] +"\n", fontParagraph))); 
                    document.add((new Paragraph("  - " + QuestionnaireDataCAPS.AnswerOptions[i][Integer.parseInt(answers[i])] +"\n", fontBold)));
                }
            }
        }
        
        if(questionnaireName.equals("IESR")) {
            List<IESRResults> questionnaires = questionnairesService.getIESRResultsBetweenDates(start, end);
            for(IESRResults q : questionnaires) {
                document.add((new Paragraph("\n\n№ " + num++ +"\n", fontBold)));
                document.add((new Paragraph("Дата прохождения: " + q.getDate() +"\n", fontBold)));
                document.add((new Paragraph("Шкала «вторжение»: " + q.getResultIN() +"\n", fontBold)));
                document.add((new Paragraph("Шкала «избегание»: " + q.getResultAV() +"\n", fontBold)));
                document.add((new Paragraph("Шкала «возбудимость»: " + q.getResultAR() +"\n", fontBold)));
                document.add((new Paragraph("Ответы:\n", fontBold)));
                q.setAnswers();
                String[] answers = q.getAnswers();
                for(int i = 0; i < questions.length; i++) { 
                    document.add((new Paragraph((i + 1) + ") " + questions[i] +"\n", fontParagraph))); 
                    document.add((new Paragraph("  - " + QuestionnaireDataIESR.AnswerOptions[Integer.parseInt(answers[i])] +"\n", fontBold)));
                }
            }
        }
        
        if(questionnaireName.equals("BHS")) {
            List<BHSResults> questionnaires = questionnairesService.getBHSResultsBetweenDates(start, end); 
            for(BHSResults q : questionnaires) {
                document.add((new Paragraph("\n\n№ " + num++ +"\n", fontBold)));
                document.add((new Paragraph("Дата прохождения: " + q.getDate() +"\n", fontBold)));
                document.add((new Paragraph("Результат: " + q.getResult() +"\n", fontBold)));
                document.add((new Paragraph("Ответы:\n", fontBold)));
                q.setAnswers();
                String[] answers = q.getAnswers();
                for(int i = 0; i < questions.length; i++) { 
                    document.add((new Paragraph((i + 1) + ") " + questions[i] +"\n", fontParagraph))); 
                    document.add((new Paragraph("  - " + QuestionnaireDataBHS.AnswerOptions[Integer.parseInt(answers[i])] +"\n", fontBold)));
                }
            }
        }

        if(questionnaireName.equals("TOP8")) {
            List<TOP8Results> questionnaires = questionnairesService.getTOP8ResultsBetweenDates(start, end);   
            for(TOP8Results q : questionnaires) {
                document.add((new Paragraph("\n\n№ " + num++ +"\n", fontBold)));
                document.add((new Paragraph("Дата прохождения: " + q.getDate() +"\n", fontBold)));
                document.add((new Paragraph("Результат: " + q.getResult() +"\n", fontBold)));
                document.add((new Paragraph("Ответы:\n", fontBold)));
                q.setAnswers();
                String[] answers = q.getAnswers();
                for(int i = 0; i < questions.length; i++) { 
                    document.add((new Paragraph((i + 1) + ") " + questions[i] +"\n", fontParagraph))); 
                    document.add((new Paragraph("  - " + QuestionnaireDataTOP8.AnswerOptions[i][Integer.parseInt(answers[i])] +"\n", fontBold)));
                }
            }
        }

        document.close();  
    }

    public void exportQuestionnaireTable(HttpServletResponse response, Person person, String questionnaireName) throws DocumentException, IOException {
        BaseFont bf = BaseFont.createFont(FONT, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        
        Document document = new Document(PageSize.A4);
        PdfWriter.getInstance(document, response.getOutputStream());
 
        document.open();

        Font fontHeader = new Font(bf, 16, Font.NORMAL);
        Paragraph headerParagraph = new Paragraph(QuestionnaireData.getQuestionnairesNames(questionnaireName), fontHeader);
        headerParagraph.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(headerParagraph);
 
        Font fontBold = new Font(bf, 12, Font.BOLD);
        document.add((new Paragraph("\nФИО: " + person.getSername() + ' ' + person.getName() + ' ' + person.getPatronymic() +"\n\n", fontBold)));

        String[] tableHeaders = QuestionnaireData.getQuestionnairesResultsTableHeaders(questionnaireName);
        PdfPTable table = new PdfPTable(tableHeaders.length - 1);
        if (questionnaireName.equals("CAPS")) table.setWidths(new float[] {1, 4, 3, 3});
        else if (questionnaireName.equals("IESR")) table.setWidths(new float[] {1, 5, 4, 4, 4});
        else table.setWidths(new float[] {1, 4, 3});

        PdfPCell cell = new PdfPCell();
        for(int i = 0; i < tableHeaders.length - 1; i++) { 
            cell.setPhrase(new Phrase(tableHeaders[i], fontBold));
            table.addCell(cell);
        }

        int num = 1;
        if (questionnaireName.equals("CAPS")) {
            List<CAPSResults> results = questionnairesService.getAllCAPSForUser();
            for (CAPSResults r : results) {
                table.addCell(String.valueOf(num++));
                table.addCell(String.valueOf(r.getDate()));
                table.addCell(String.valueOf(r.getResultFrequency()));
                table.addCell(String.valueOf(r.getResultIntensity()));
            }
        } else if (questionnaireName.equals("IESR")) {
            List<IESRResults> results = questionnairesService.getAllIESRForUser();
            for (IESRResults r : results) {
                table.addCell(String.valueOf(num++));
                table.addCell(String.valueOf(r.getDate()));
                table.addCell(String.valueOf(r.getResultIN()));
                table.addCell(String.valueOf(r.getResultAV()));
                table.addCell(String.valueOf(r.getResultAR()));
            }
        } else if (questionnaireName.equals("BHS")) {
            List<BHSResults> results = questionnairesService.getAllBHSForUser();
            for (BHSResults r : results) {
                table.addCell(String.valueOf(num++));
                table.addCell(String.valueOf(r.getDate()));
                table.addCell(String.valueOf(r.getResult()));
            }
        } else {
            List<TOP8Results> results = questionnairesService.getAllTOP8ForUser();
            for (TOP8Results r : results) {
                table.addCell(String.valueOf(num++));
                table.addCell(String.valueOf(r.getDate()));
                table.addCell(String.valueOf(r.getResult()));
            }
        }

        document.add(table);

        document.close();

    }
    
}
