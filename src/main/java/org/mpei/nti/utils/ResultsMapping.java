package org.mpei.nti.utils;

import org.mpei.nti.substation.substationStructures.IED;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResultsMapping {

    public static void resultsMapping(List<SubstationMeasures> population) throws IOException {
        PrintWriter writer = new PrintWriter("src" + File.separator + "main" + File.separator +
                "resources" + File.separator + "results.txt", StandardCharsets.UTF_8);
        writer.println("Значение целевой функции " + String.format("%f", population.get(0).getTotalPrice()));
        writer.println("CAPEX затраты за 25 лет " + String.format("%f", population.get(0).getCapexPrice()));
        writer.println("OPEX затраты за 25 лет " + String.format("%f", population.get(0).getOpexPrice()));
        writer.println(" ");
        for (SubstationMeasuresPerYear substationMeasuresPerYear : population.get(0).getSubstationMeasuresPerYear()) {
            writer.println("Год №" + String.format("%d", substationMeasuresPerYear.getYearNumber()));
            writer.println("Архитектура " + String.format("%d", substationMeasuresPerYear.getArchitectureType()));
            //ORG
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD1() == 1) {
                writer.println("Организационные меры безопасности, ограничивающие доступ нарушителя к шине процесса");
            }
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD6() == 1) {
                writer.println("Организационные меры безопасности, ограничивающие подключение нарушителя к инженерному " +
                        "АРМ, используемому для локального доступа");
            }
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD10() == 1) {
                writer.println("Организационные меры безопасности, ограничивающие подключение нарушителя к АРМ инженера РЗА");
            }
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD12() == 1) {
                writer.println("Организационные меры безопасности, ограничивающие доступ к сервисному порту для " +
                        "конфигурирования ИЭУ РЗА");
            }
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD16() == 1) {
                writer.println("Организационные меры безопасности, ограничивающие доступ нарушителя к шине станции");
            }
            if (substationMeasuresPerYear.getOrganizationalMeasures().getD22() == 1) {
                writer.println("Процесс и меры реагирования на систему обнаружения вторжений");
            }

            if (substationMeasuresPerYear.getImprosedMeasures().getD3() == 1) {
                writer.println("Адаптивная антенная решетка для систем GPS/GLONASS");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD7() == 1) {
                writer.println("Парольная защита инженерного АРМ, используемого при локальном доступе");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD11() == 1) {
                writer.println("Механизмы безопасности, реализующие парольную защиту в ПО АРМ инженера РЗА");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD19() == 1) {
                writer.println("Наложенные СЗИ на АРМ инженера РЗА (антивирус и/или система защиты конечных точек)");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD19() == 1) {
                writer.println("Межсетевой экран");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                writer.println("Система обнаружения вторжений");
            }
            if (substationMeasuresPerYear.getImprosedMeasures().getD21() == 1) {
                writer.println("Встроенный в коммутатор механизм защиты от DoS");
            }

            for (IED ied : substationMeasuresPerYear.getIedList()){
                writer.println("IED - " + ied.getNameOfIED() + " со встроенными механизмами:");

            }

//            //Emb
//            private String nameOfProtection;
//    /*Криптографическая защита пакетов SV с использованием имитовставки, предотвратит обработку SV потока,
//    сгенерированного нарушителем.*/
//            private int D2;
//    /*Парольная защита, соответствующая корпоративным правилам к парольной защите, ИЭУ предотвратила доступ и дальнейшую
//    подмену конфигурации и/или уставок.*/
//            private int D4;
//            /*Разграничение доступа, реализованное в необходимом объеме, предотвратило подмену конфигурации и/или уставок ИЭУ.*/
//            private int D5;
//    /*Криптографическая защита протокола MMS с использованием TLS предотвратила передачу информации по MMS,
//    сгенерированной нарушителем.*/
//            private int D8;
//            /*Механизм удаленной аутентификации к ИЭУ предотвратит несанкционированное удаленное подключение.*/
//            private int D9;
//            /*Механизм доверенного обновления предотвратил установку измененной прошивки ИЭУ РЗА.*/
//            private int D13;
//            /*Контроль целостности предотвратил подмену ПО на ИЭУ РЗА.*/
//            private int D14;
//            /*Доверенная загрузка предотвратил подмену системного ПО на ИЭУ РЗА.*/
//            private int D15;
//    /*Криптографическая защита пакетов GOOSE с использованием имитовставки, предотвратит обработку GOOSE потока,
//    сгенерированного нарушителем.*/
//            private int D17;
//            /*Межсетевой экран в ИЭУ предотвратил передачу некорректного трафика.*/
//            private int D18;
//            /*Встроенный в ИЭУ механизм защиты от DoS предотвратил атаку типа отказ в обслуживании.*/
//            private int D23;

            writer.println(" ");
        }

        writer.close();
    }

}
