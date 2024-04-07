package com.application.ptsdwebapplication.data;

// Ежемесячный
public class QuestionnaireDataCAPS {
    
    // Нечетные вопросы - про частоту (frequency)
    // Четные вопросы - про интенсивность (intensity)
    public static String[] Questions = {
        //1
        "Случалось ли так, что вы непроизвольно, без всякого повода, вспоминали об этих событиях? Было ли это только во сне? " +
            "(Исключить, если воспоминания появлялись только во сне.) Как часто это происходило в течение последнего месяца?",
        "Если из этих случаев взять самый тяжелый, насколько сильны были переживания, вызванные этими воспоминаниями? Были ли вы вынуждены " +
            "прерывать дела, которыми в этот момент занимались? В состоянии ли вы, если постараетесь, преодолеть эти воспоминания, избавиться от них?",
        //2
        "Были ли у вас случаи, когда вы видели что-то, напомнившее об этих событиях, и вам становилось тяжело и грустно, вы расстраивались? " +
            "(Например: определенный тип мужчин – для жертв изнасилования, цепочка деревьев или лесистая местность – для ветеранов войны.) " + 
            "Как часто это с вами случалось в течение последнего месяца?", 
        "Если из этих случаев взять самый тяжелый, насколько вы были взволнованы и расстроены, насколько сильны были переживания?",
        //3
        "Приходилось ли вам когда-нибудь внезапно совершать какое-либо действие или чувствовать себя так, " +
            "как будто бы то событие(я) происходит снова? Как часто это с вами случалось в течение последнего месяца?",
        "Если из этих случаев взять самый тяжелый, насколько реальным было это ощущение?" +
            "Насколько сильно вам казалось, что то событие(я) происходило снова? Как долго это продолжалось? Что вы делали при этом?",
        //4
        "Были ли у вас когда-нибудь неприятные сны о том событии? Как часто это с вами случалось в течение последнего месяца?",
        "Если из этих случаев взять самый худший, как сильны были те неприятные, тяжелые переживания и чувство дискомфорта, " +
            "которые вызывали у вас эти сны? Вы просыпались от этих снов?",
        //5
        "Пытались ли вы отгонять мысли о событии(ях)? Прилагали ли вы усилия, чтобы избежать ощущений, связанных с событием(ями) " +
            "(например, чувств гнева, печали, вины)? Как часто это с вами случалось в течение последнего месяца?",
        "Как велики были усилия, которые вы прилагали, чтобы не думать о том событии(ях), чтобы не испытывать чувств, имеющих к нему (ним) отношение? " +
            "(Дайте оценку всех попыток сознательного избегания, включая отвлечение, подавление и снижение возбужденности при помощи алкоголя или наркотиков.)",
        //6
        "Пытались ли вы когда-либо избегать каких-либо действий или ситуаций, которые напоминали бы вам о событии(ях)?",
        "Как велики были усилия, которые вы прилагали для того, чтобы избегать каких-то действий или ситуаций," + 
            " связанных с событием(ями)? (Дайте оценку всех попыток поведенческого избегания)",
        //7
        "Случалось ли так, что вы не могли вспомнить важные обстоятельства происшедшего с вами события(ий) (например, имена, лица, " +
            "последовательность событий)? Многое ли из того, что имело отношение к происшедшему, вам было трудно вспомнить в прошедшем месяце?",
        "Как велики были усилия, которые вам понадобилось приложить, чтобы вспомнить о важных обстоятельствах того, что произошло с вами?",
        //8
        "Чувствуете ли вы, что потеряли интерес к занятиям, которые были для вас важными или приятными, – например, спорт, увлечения, общение? " +
            "Если сравнить с тем, что было до происшедшего, сколько видов занятий перестали вас интересовать за прошедший месяц?",
        "В самом худшем случае насколько глубока или сильна была утрата интереса к этим занятиям?",
        //9
        "Не было ли у вас чувства отдаленности или потери связи с окружающими? Отличалось ли это состояние от того, " +
            "что вы чувствовали перед тем, как произошло это событие(я)? Как часто вы чувствовали себя так в течение последнего месяца?",
        "В самом худшем случае насколько сильно было ваше чувство отстраненности или отсутствия связи с окружающими? Кто все-таки оставался вам близок?",
        //10
        "Были ли у вас такие периоды, когда вы были «бесчувственны» (вам было трудно испытывать такие чувства, как любовь или счастье)? Отличалось ли это " +
            "состояние от того, как вы себя чувствовали перед тем, как произошло это событие(я)? Как часто вы чувствовали себя так в течение последнего месяца?",
        "В самом худшем случае насколько сильным у вас было ощущение «бесчувственности»?",
        //11
        "Было ли когда-нибудь, что вы чувствовали отсутствие необходимости строить планы на будущее, что почему-либо у вас «нет завтрашнего дня»? " +
            "(при отсутствии реального риска, например, опасных для жизни медицинских диагнозов). Отличалось ли это состояние от того," +
            "как вы себя чувствовали перед тем, как произошло это событие(я)? Как часто вы чувствовали себя так в течение последнего месяца?",
        "В самом худшем случае насколько сильным было чувство, что у вас не будет будущего? Как вы думаете; " +
            "как долго вы будете жить? Какие основания у вас предполагать, что вы умрете преждевременно?",
        //12
        "Были ли у вас какие-либо проблемы с засыпанием или с тем, чтобы спать нормально? Отличается ли это от того, " +
            "как вы спали перед событием(ями)? Как часто у вас были трудности со сном в прошлом месяце?",
        "Как много времени надо вам, чтобы заснуть? Как часто вы просыпаетесь среди ночи? Сколько часов подряд, не просыпаясь, вы спите каждую ночь?",
        //13
        "Были ли такие периоды, когда вы чувствовали, что необычно раздражительны или проявляли чувство гнева и вели себя агрессивно? Отличается ли это состояние от того, " + 
            "как вы себя чувствовали или как действовали до событий(я)? Как часто вы испытывали подобные чувства или вели себя подобным образом в прошлом месяце?",
        "Насколько сильный гнев вы испытывали и каким образом вы его выражали?",
        //14
        "Не казалось ли вам трудным сосредоточиться на каком-то занятии или на чем-то, что окружало вас? Изменилась ли ваша способность к сосредоточению " + 
            "с тех пор, как случилось это событие(я)? Как часто у вас наблюдались затруднения с сосредоточением внимания в течение прошлого месяца?",
        "Насколько трудно для вас было сосредоточиться на чем-либо? ",
        //15
        "Приходилось ли вам быть как-то особенно настороженным или бдительным даже тогда, когда для этого не было очевидной необходимости? " +
            "Отличалось ли это от того, как вы себя чувствовали перед событием(ями)? Как часто в прошлом месяце вы были настороженны или бдительны?",
        "Как велики усилия, которые вы прилагаете, чтобы знать обо всем, что происходит вокруг вас?",
        //16
        "Испытывали ли вы сильную реакцию испуга на громкие неожиданные звуки (например, на автомобильные выхлопы, пиротехнические эффекты, стук захлопнувшейся двери и т. п.) " +
            "или на что-то, что вы вдруг увидели (например, движение, замеченное на периферии вашего поля зрения – «углом глаза»)? Отличается ли это от того, как вы себя чувствовали до события(ий)?",
        "В самом худшем случае насколько сильной была эта реакция испуга?",
        //17
        "Приходилось ли вам отмечать у себя наличие определенных физиологических реакций, когда вы сталкивались с ситуациями, напоминающими вам о событии(ях)? Как часто они наблюдались в прошлом месяце?",
        "В самом худшем случае как сильны были эти физиологические реакции?"

    };
	
    public static String[][] AnswerOptions = {
        //1
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"отсутствие дистресса", "низкая интенсивность симптома: минимальный дистресс", "умеренная интенсивность: дистресс отчетливо присутствует", 
            "высокая интенсивность: существенный дистресс, явные срывы в деятельности и трудности в преодолении возникших воспоминаний о событии",
            "очень высокая интенсивность: тяжелый, непереносимый дистресс, неспособность продолжать деятельность и невозможность избавиться от воспоминаний о событии"},
        //2
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"нисколько", "слабая интенсивность симптома: минимальный дистресс", 
            "умеренная интенсивности: дистресс отчетливо присутствует, но еще контролируется", 
            "высокая интенсивность: значительный дистресс", 
            "очень высокая интенсивность: непереносимый дистресс"},
        //3
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"никогда такого не было", "слабая интенсивность симптома: ощущение чуть большей реалистичности происходящего, чем при простом размышлении о нем", 
            "умеренная интенсивность: ощущения определенные, но передающие диссоциативное качество, однако при этом сохраняется тесная связь с окружающим, ощущения похожи на грезы наяву", 
            "высокая интенсивность: в сильной степени диссоциативные ощущения – вспоминаются образы, звуки, запахи, но все еще сохраняется некоторая связь с окружающим миром", // ЧУТЬ ИЗМЕНЕНА ФОРМУЛИРОВКА
            "очень высокая интенсивность: ощущения полностью диссоциативны – флэшбэкэффект, полное отсутствие связи с окружающей реальностью, возможна амнезия данного эпизода – «затмение», провал в памяти"},
        //4
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"отсутствовали", "слабо выраженная интенсивность симптома: минимальный дистресс не вызывал пробуждении", 
            "умеренная интенсивность: просыпается, испытывая дистресс, но легко засыпает снова", 
            "высокая интенсивность: очень тяжелый дистресс, затруднено повторное засыпание", 
            "очень высокая интенсивность: непреодолимый дистресс, невозможность заснуть снова"},
        //5
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"никакого усилия", "слабая интенсивность симптома: минимальное усилие", "умеренная интенсивность: некоторое усилие, избегание определенно есть", 
            "высокая интенсивность: значительное усилие, избегание безусловно есть", "очень высокая интенсивность: отчетливо выраженные попытки избегания"},
        //6
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"никакого усилия", "слабая интенсивность симптома: минимальное усилие", "умеренная интенсивность: некоторое усилие, избегание определенно есть", 
            "высокая интенсивность: значительное усилие, избегание безусловно есть", "очень высокая интенсивность: отчетливо выраженные попытки избегания"},
        //7
        {" ничего, ясная память о событии в целом", "некоторые обстоятельства события(ий) было невозможно вспомнить (менее чем 10%)", 
            "ряд обстоятельств события(й) было невозможно вспомнить (20–30%)", 
            "большую часть обстоятельств события(й) было невозможно вспомнить (50–60%)", 
            "почти совсем невозможно было вспомнить событие^) (более чем 80%)"},
        {"какие-либо затруднения при воспоминании о событиях отсутствовали", 
            "слабая интенсивность симптома: незначительные затруднения при воспроизведении обстоятельств события", 
            "умеренная интенсивность: определенные затруднения, однако при сосредоточении сохраняется способность к воспроизведению события в памяти", 
            "высокая интенсивность: несомненные затруднения при воспроизведении обстоятельств события", 
            "очень высокая интенсивность: полная неспособность вспомнить о событии(ях)"},
        //8
        {"потери интереса вообще не было", "потеря интереса к отдельным занятиям (менее чем 10%)", "потеря интереса к нескольким занятиям (20–30%)", 
            "потеря интереса к большому числу занятий (50–60%)", "потеря интереса почти ко всему (более чем 80%)"},
        {"вообще не было потери интереса", "слабая интенсивность симптома: только незначительная потеря интереса, не " +
            "исключающая получения удовольствия в процессе занятий, если они все-таки начались", 
            "умеренная интенсивность: безусловная потеря интереса, но все-таки сохраняется некоторое чувство удовольствия от занятия(й)", 
            "высокая интенсивность: очень значительная потеря интереса к занятиям", 
            "очень высокая интенсивность: полная потеря интереса, не вовлекается ни в какую деятельность"},
        //9
        {"такого вообще не было", "редко (менее 10% времени)", "иногда (20-30%)", "часто (50-60%)", "почти всегда или постоянно (более чем 80%)"},
        {"отсутствуют подобные чувства", "слабая интенсивность симптома: эпизодически чувствует себя «идущим,не в ногу» с окружающими", 
            "умеренная интенсивность: определенное наличие чувства отдаленности, но сохраняются какие-то межличностные связи и чувство принадлежности к окружающему миру", 
            "высокая интенсивность: значительное чувство отстраненности или отчужденности от большинства людей, сохраняется способность взаимодействия только с одним человеком", 
            "очень высокая интенсивность: чувствует себя полностью оторванным от других людей, близкие отношения не поддерживает ни с кем"},
        //10
        {"такого вообще не было", "редко (менее 10% времени)", "иногда (20-30%)", "часто (50-60%)", "почти всегда или постоянно (более чем 80%)"},
        {"ощущения бесчувственности нет", "слабая интенсивность симптома: такое ощущение имеется, но незначительное", 
            "умеренная интенсивность: явное ощущение бесчувственности, но способность испытывать эмоции все-таки сохранена", 
            "высокая интенсивность: значительное ощущение бесчувственности по отношению по крайней мере к двум основным эмоциям – любви и счастья", 
            "очень высокая интенсивность: ощущает полное отсутствие эмоций"},
        //11
        {"такого вообще не было", "редко (менее 10% времени)", "иногда (20-30%)", "часто (50-60%)", "почти всегда или постоянно (более чем 80%)"},
        {"ощущения сокращенного будущего нет", "слабая интенсивность симптома: незначительные ощущения по поводу сокращенности жизненной перспективы", 
            "умеренная интенсивность симптома: определенно присутствует ощущение сокращенной жизненной перспективы", 
            "высокая интенсивность симптома: значительно выражены ощущения сокращенной жизненной перспективы, могут иметь место определенные предчувствия о продолжительности жизни", 
            "очень высокая интенсивность симптома: всепоглощающее чувство сокращенной жизненной перспективы, полная убежденность в преждевременной смерти"},
        //12
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "каждую ночь (или почти каждую)"},
        {"нет проблем со сном", "слабая интенсивность симптома: несколько затруднено засыпание, некоторые трудности с сохранением сна (потеря сна до 30 минут)", 
            "умеренная интенсивность: определенное нарушение сна – ясно выраженная увеличенная продолжительность периода засыпания или трудности с сохранением сна (потеря сна – 30–90 минут)", 
            "высокая интенсивность: значительное удлинение времени засыпания или большие трудности с сохранением сна (потеря сна – 1,5–3 часа)", 
            "очень высокая интенсивность: очень длительный латентный период сна, непреодолимые трудности с сохранением сна (потеря сна более 3 часов)"},
        //13
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"не было ни раздражительности, ни гнева", "слабая интенсивность симптома: минимальная раздражительность, в гневе – повышение голоса", 
            "умеренная интенсивность: явное наличие раздражительности, в гневе с легкостью начинает спорить, но быстро «остывает»", 
            "высокая интенсивность: значительная раздражительность, в гневе – речевая или поведенческая агрессивность", 
            "очень высокая интенсивность: непреодолимый гнев, сопровождаемый эпизодами физического насилия"},
        //14
        {"вообще не было", "редко (менее 10% времени)", "иногда (20-30%)", "часто (50-60%)", "почти всегда или постоянно (более чем 80%)"},
        {"никакой проблемы не было", "слабая интенсивность: для сосредоточения требовались незначительные усилия", 
            "умеренная интенсивность: определенная потеря способности к сосредоточению, но при усилии может сосредоточиться", 
            "высокая интенсивность: значительное ухудшение функции, даже при особых усилиях", 
            "очень высокая интенсивность: полная неспособность к сосредоточению и концентрации внимания"},
        //15
        {"вообще не было", "редко (менее 10% времени)", "иногда (20-30%)", "часто (50-60%)", "почти всегда или постоянно (более чем 80%)"},
        {"симптом отсутствует", "слабая интенсивность симптома: минимальные проявления гипербдительности, незначительно повышенное любопытство", 
            "умеренная интенсивность: определенно имеется гипербдительность, настороженность в общественных местах (например, выбор безопасного места в ресторане или кинотеатре)", 
            "высокая интенсивность: значительные проявления гипербдительности, субъект очень насторожен, постоянное наблюдение за окружающими в поисках опасности, " +
            "преувеличенное беспокойство о собственной безопасности (а также своей семьи и дома)", 
            "очень высокая интенсивность: гипертрофированная бдительность, усилия по обеспечению безопасности требуют значительных затрат времени и энергии и" + 
            "могут включать активные действия по проверке ее наличия; в процессе беседы – значительная настороженность"},
        //16
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"симптом отсутствовал: реакции испуга не было", "слабая интенсивность симптома: минимальная реакция испуга", 
            "умеренная интенсивность: определенная реакция испуга на внезапный раздражитель, «подпрыгивание»", 
            "высокая интенсивность: значительная реакция испуга, сохранение возбуждения после первичной реакции", 
            "очень высокая интенсивность: крайне выраженная реакция испуга, явное защитное поведение (например, ветеран войны, который «падает лицом в грязь»)"},
        //17
        {"никогда", "один или два раза", "один или два раза в неделю", "несколько раз в неделю", "ежедневно или почти каждый день"},
        {"симптом отсутствует", "слабая интенсивность симптома: минимальная реакция", 
            "умеренная интенсивность: явное наличие физиологической реакции, некоторый дискомфорт", 
            "высокая интенсивность: интенсивная физиологическая реакция, сильный дискомфорт", 
            "очень высокая интенсивность: драматическая физиологическая реакция, сохранение последующего возбуждения"}
           
    };

}
