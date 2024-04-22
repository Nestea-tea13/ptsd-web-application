package com.application.ptsdwebapplication.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.models.PersonDrug;

@Repository
public interface DrugMarksRepository  extends JpaRepository<DrugMark, Integer> {

    List<DrugMark> findByPersonDrugOrderByTakingNum(PersonDrug personDrug);

    List<DrugMark> findByPersonDrugAndMarkAndDateBeforeOrderByTakingNum(PersonDrug personDrug, Object object, Date date);

    List<DrugMark> findByPersonDrugAndMarkAndDateBetweenOrderByTakingNum(PersonDrug personDrug, Object object, Date date1, Date date2);
    
}
