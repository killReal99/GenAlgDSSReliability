package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IED {
    private String nameOfIED;
    private EquipmentType equipmentTypeName;
    private List<Protections> protectionsList;
    /*Криптографическая защита пакетов SV с использованием имитовставки, предотвратит обработку SV потока,
сгенерированного нарушителем.*/
    private int D2;
    /*Парольная защита, соответствующая корпоративным правилам к парольной защите, ИЭУ предотвратила доступ и дальнейшую
    подмену конфигурации и/или уставок.*/
    private int D4;
    /*Разграничение доступа, реализованное в необходимом объеме, предотвратило подмену конфигурации и/или уставок ИЭУ.*/
    private int D5;
    /*Криптографическая защита протокола MMS с использованием TLS предотвратила передачу информации по MMS,
    сгенерированной нарушителем.*/
    private int D8;
    /*Механизм удаленной аутентификации к ИЭУ предотвратит несанкционированное удаленное подключение.*/
    private int D9;
    /*Механизм доверенного обновления предотвратил установку измененной прошивки ИЭУ РЗА.*/
    private int D13;
    /*Контроль целостности предотвратил подмену ПО на ИЭУ РЗА.*/
    private int D14;
    /*Доверенная загрузка предотвратил подмену системного ПО на ИЭУ РЗА.*/
    private int D15;
    /*Криптографическая защита пакетов GOOSE с использованием имитовставки, предотвратит обработку GOOSE потока,
    сгенерированного нарушителем.*/
    private int D17;
    /*Межсетевой экран в ИЭУ предотвратил передачу некорректного трафика.*/
    private int D18;
    /*Встроенный в ИЭУ механизм защиты от DoS предотвратил атаку типа отказ в обслуживании.*/
    private int D23;
}
