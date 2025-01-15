package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImprosedMeasures {
    /*Адаптивная антенная решетка предотвратила атаку на систему GPS/GLONASS.*/
    private int D3;
    /*Парольная защита инженерного АРМ, используемого при локальном доступе, предотвратила доступ и дальнейшую подмену
    конфигурации и/или уставок ИЭУ.*/
    private int D7;
    /*Механизмы безопасности, реализующие парольную защиту в ПО АРМ инженера РЗА, предотвратили доступ и дальнейшую
    подмену уставок ИЭУ.*/
    private int D11;
    /*Наложенные СЗИ на АРМ инженера РЗА (антивирус и/или система защиты конечных точек) предотвратят работу стороннего
    ПО на АРМ инженера РЗА.*/
    private int D19;
    /*Межсетевой экран на ЦПС предотвратил передачу стороннего трафика.*/
    private int D20;
    /*Система обнаружения вторжений детектировала нелегитимный трафик.*/
    private int D21;
    /*Встроенный в коммутатор механизм защиты от DoS предотвратил атаку типа отказ в обслуживании.*/
    private int D24;
}


