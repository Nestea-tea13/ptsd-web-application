package com.application.ptsdwebapplication.data;

public class Labels {

    public static String[] usersTableHeaders = {
        "№",
        "Фамилия", 
        "Имя",
        "Почта",
        "Статус лечения",
        ""
    };

    public static String[] adminsTableHeaders = {
        "№",
        "Фамилия", 
        "Имя", 
        "Отчество",
        "Пол", 
        "Дата рождения", 
        "Почта",
        ""
    };

    public static String[] PersonDrugsTableHeaders = {
        "№",
        "Название", 
        "Дозировка", 
        "Периодичность приема",
        "Дата начала приема", 
        "Дата завершения приема",
        ""
        // "Статус" ? в процессе/завершено/ожидается
    };

    public static String[] PeriodDrugOptions = {
        "1 раз в день",
        "2 раза в день", 
        "3 раза в день", 
        "1 раз в 2 дня", 
        "1 раз в 3 дня",
        "1 раз в неделю",
        "1 раз в 2 недели",
        "1 раз в месяц"      
    };

    public static int[][] PeriodDrugParameters = { // {кол-во дней для периодичности, кол-во раз в день}
        {1, 1},
        {1, 2}, 
        {1, 3},
        {2, 1},
        {3, 1},
        {7, 1},
        {14, 1},
        {30, 1}   
    };

    public static String[] QuestionnairesNames = {
        "Шкала для клинической диагностики ПТСР (Clinical-administered PTSD Scale - CAPS)",
        "Шкала оценки влияния травматического события (Impact of Event Scale-R - IES-R)", 
        "Шкала Безнадежности Бека (Beck Hopelessness Scale, BHS)", 
        "Опросник для оценки терапевтической динамики ПТСР «Treatment Outcome PTSD Scale» (TOP-8)"     
    };

    public static String[][] QuestionnairesResultsTableHeaders = {
        {"№", "Дата прохождения", "Частота", "Интенсивность", ""}, // CAPS
        {"№", "Дата прохождения", "Шкала «вторжение»", "Шкала «избегание»", "Шкала «возбуждение»", ""}, // IESR
        {"№", "Дата прохождения", "Результат", ""}, // BHS
        {"№", "Дата прохождения", "Результат", ""} // TOP8
    };

}
