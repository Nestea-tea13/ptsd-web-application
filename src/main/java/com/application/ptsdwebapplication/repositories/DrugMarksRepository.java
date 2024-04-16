package com.application.ptsdwebapplication.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.application.ptsdwebapplication.models.DrugMark;
import com.application.ptsdwebapplication.models.PersonDrug;

@Repository
public interface DrugMarksRepository  extends JpaRepository<DrugMark, Integer> {

    List<DrugMark> findByPersonDrugOrderByTakingNum (PersonDrug personDrug);

    List<DrugMark> findByPersonDrugAndMarkAndDateBefore(PersonDrug personDrug, Object object, Date date);

    List<DrugMark> findByPersonDrugAndMarkAndDateBetween(PersonDrug personDrug, Object object, Date date1, Date date2);
    
}
