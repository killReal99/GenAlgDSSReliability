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
            writer.println("Год № " + String.format("%d", substationMeasuresPerYear.getYearNumber()));
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

            for (IED ied : substationMeasuresPerYear.getIedList()) {
                writer.println("ИЭУ - " + ied.getNameOfIED() + " со встроенными механизмами:");
                if (ied.getD2() == 1) {
                    writer.println("Криптографическая защита пакетов SV с использованием имитовставки");
                }
                if (ied.getD4() == 1) {
                    writer.println("Парольная защита ИЭУ");
                }
                if (ied.getD5() == 1) {
                    writer.println("Разграничение доступа в ИЭУ");
                }
                if (ied.getD8() == 1) {
                    writer.println("Криптографическая защита протокола MMS с использованием TLS");
                }
                if (ied.getD9() == 1) {
                    writer.println("Механизм удаленной аутентификации к ИЭУ");
                }
                if (ied.getD13() == 1) {
                    writer.println("Механизм доверенного обновления");
                }
                if (ied.getD14() == 1) {
                    writer.println("Контроль целостности ПО");
                }
                if (ied.getD15() == 1) {
                    writer.println("Доверенная загрузка");
                }
                if (ied.getD17() == 1) {
                    writer.println("Криптографическая защита протокола GOOSE с использованием имитовставки");
                }
                if (ied.getD18() == 1) {
                    writer.println("Межсетевой экран в ИЭУ предотвратил передачу некорректного трафика");
                }
                if (ied.getD23() == 1) {
                    writer.println("Встроенный в ИЭУ механизм защиты от DoS");
                }
            }
            writer.println(" ");
        }
        writer.close();
    }

}
