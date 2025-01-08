package org.mpei.nti.utils;

import org.mpei.nti.substation.substationStructures.SubstationMeasures;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultsMapping {


    public static void resultsMapping(List<SubstationMeasures> population) throws IOException {
        PrintWriter writer = new PrintWriter("src" + File.separator + "main" + File.separator +
            "resources" + File.separator + "results.txt", StandardCharsets.UTF_8);
        writer.println("Total price of the best individual " + String.format("%f", population.get(0).getTotalPrice()));
        writer.println("CAPEX price of the best individual " + String.format("%f", population.get(0).getCapexPrice()));
        writer.println("OPEX price of the best individual " + String.format("%f", population.get(0).getOpexPrice()));

        writer.close();
    }

    public static void mapSzi(List<List<Double>> population) {
        switch (population.get(0).get(0).intValue()) {
            case 1:
                System.out.println("ЦПС первой архитектуры");
                break;
            case 2:
                System.out.println("ЦПС второй архитектуры");
                break;
            case 3:
                System.out.println("ЦПС третьей архитектуры");
                break;
        }

        if (population.get(0).get(2) == 1) System.out.println("Организационные меры безопасности предотвратят " +
                "подключение нарушителя к шине процесса");
        if (population.get(0).get(3) == 1) System.out.println("Криптографическая защита пакетов SV с использованием " +
                "имитовставки, предотвратит обработку SV потока, сгенерированного нарушителем.");
        if (population.get(0).get(4) == 1) System.out.println("Адаптивная антенная решетка предотвратила атаку на " +
                "систему GPS/GLONASS");
        if (population.get(0).get(5) == 1)
            System.out.println("Парольная защита, соответствующая корпоративным правилам " +
                    "к парольной защите, ИЭУ предотвратила доступ и дальнейшую подмену конфигурации и/или уставок.");
        if (population.get(0).get(6) == 1) System.out.println("Разграничение доступа, реализованное в необходимом " +
                "объеме, предотвратило подмену конфигурации и/или уставок ИЭУ");
        if (population.get(0).get(7) == 1) System.out.println("Организационные меры безопасности предотвратят " +
                "подключение нарушителя к инженерному АРМ, используемому для локального доступа.");
        if (population.get(0).get(8) == 1) System.out.println("Парольная защита инженерного АРМ, используемого при " +
                "локальном доступе, предотвратила доступ и дальнейшую подмену конфигурации и/или уставок ИЭУ");
        if (population.get(0).get(9) == 1)
            System.out.println("Криптографическая защита протокола MMS с использованием " +
                    "TLS предотвратила передачу информации по MMS, сгенерированной нарушителем");
        if (population.get(0).get(10) == 1) System.out.println("Механизм удаленной аутентификации к ИЭУ предотвратит " +
                "несанкционированное удаленное подключение.");
        if (population.get(0).get(11) == 1) System.out.println("Организационные меры безопасности предотвратят " +
                "подключение нарушителя к АРМ инженера РЗА");
        if (population.get(0).get(12) == 1)
            System.out.println("Механизмы безопасности, реализующие парольную защиту в " +
                    "ПО АРМ инженера РЗА, предотвратили доступ и дальнейшую подмену уставок ИЭУ");
        if (population.get(0).get(13) == 1)
            System.out.println("Организационные меры безопасности по защите сервисного " +
                    "порта предотвратила физический доступ нарушителя к конфигурированию ИЭУ РЗА");
        if (population.get(0).get(14) == 1)
            System.out.println("Механизм доверенного обновления предотвратил установку " +
                    "измененной прошивки ИЭУ РЗА");
        if (population.get(0).get(15) == 1)
            System.out.println("Контроль целостности предотвратил подмену ПО на ИЭУ РЗА");
        if (population.get(0).get(16) == 1)
            System.out.println("Доверенная загрузка предотвратил подмену системного ПО " +
                    "на ИЭУ РЗА");
        if (population.get(0).get(17) == 1)
            System.out.println("Организационные меры безопасности предотвратят сетевое " +
                    "подключение нарушителя к шине станции");
        if (population.get(0).get(18) == 1)
            System.out.println("Криптографическая защита пакетов GOOSE с использованием " +
                    "имитовставки, предотвратит обработку GOOSE потока, сгенерированного нарушителем.");
        if (population.get(0).get(19) == 1) System.out.println("Межсетевой экран в ИЭУ предотвратил передачу " +
                "некорректного трафика");
        if (population.get(0).get(20) == 1) System.out.println("Наложенные СЗИ на АРМ инженера РЗА (антивирус и/или " +
                "система защиты конечных точек) предотвратят работу стороннего ПО на АРМ инженера РЗА");
        if (population.get(0).get(21) == 1)
            System.out.println("Межсетевой экран на ЦПС");
        if (population.get(0).get(22) == 1)
            System.out.println("Система обнаружения вторжений");
        if (population.get(0).get(23) == 1) System.out.println("Работники ЦПС отреагировали на сигнал системы " +
                "обнаружения вторжения и предотвратили передачу трафика с некорректной информацией");
        if (population.get(0).get(24) == 1) System.out.println("Встроенный в ИЭУ механизм защиты от DoS");
        if (population.get(0).get(25) == 1) System.out.println("Встроенный в коммутатор механизм защиты от DoS");
    }

}
