package com.application.ptsdwebapplication.services;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.application.ptsdwebapplication.data.Labels;
import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.models.PersonDrug;
import com.application.ptsdwebapplication.repositories.DrugMarksRepository;

@Service
@Transactional(readOnly = true)
public class DrugMarksService {

    private final DrugMarksRepository drugMarksRepository;

    public DrugMarksService(DrugMarksRepository drugMarksRepository) {
        this.drugMarksRepository = drugMarksRepository;
    }

    public List<DrugMark> findByPersonDrug(PersonDrug personDrug) {
        return drugMarksRepository.findByPersonDrugOrderByTakingNum(personDrug);
    }

    @Transactional
    public void createEmptyMarks(PersonDrug personDrug) {
        int takingNum = 1, needPeriod = 1, needNumPerDay = 1;
        Date date = personDrug.getStartDate();

        for(int i = 0; i < Labels.PeriodDrugOptions.length; i++) {
            if (personDrug.getPeriod().equals(Labels.PeriodDrugOptions[i])) {
                needPeriod = Labels.PeriodDrugParameters[i][0];
                needNumPerDay = Labels.PeriodDrugParameters[i][1];
                break;
            }
        }

        while (date.compareTo(personDrug.getEndDate()) <= 0) {
            for(int i = 1; i <= needNumPerDay; i++) {
                DrugMark mark = new DrugMark(personDrug, takingNum++, date, i);
                drugMarksRepository.save(mark);
            }
            date = DateUtils.addDays(date, needPeriod);
        }

    }

    @Transactional
    public void updateMark(int id, String mark) {
        DrugMark drugMark = drugMarksRepository.getById(id);
        drugMark.setMark(mark);
        drugMarksRepository.save(drugMark);
    }

    public List<DrugMark> findByPersonDrugMissedMarks(PersonDrug personDrug) {
        return drugMarksRepository.findByPersonDrugAndMarkAndDateBeforeOrderByTakingNum(personDrug, null, new Date());
    }

    public List<DrugMark> findByPersonDrugTodayNullMarks(PersonDrug personDrug) {
        return drugMarksRepository.findByPersonDrugAndMarkAndDateBetweenOrderByTakingNum(personDrug, null, new Date(), new Date());
    }

    public int[] getQuantityMarksForPersonDrug(PersonDrug personDrug) {
        List<DrugMark> marks = findByPersonDrug(personDrug);
        int[] quantity = new int[4];
        for (DrugMark mark : marks) {
            if (mark.getMark() != null) {
                if (mark.getMark().equals("Принято")) quantity[0]++;
                else if (mark.getMark().equals("Пропущено")) quantity[1]++;
                else quantity[2]++;
            } else quantity[3]++;
        }
        return quantity;
    }
    
}
