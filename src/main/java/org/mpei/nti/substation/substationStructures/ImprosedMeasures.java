package org.mpei.nti.substation.substationStructures;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "improsed_measures")
public class ImprosedMeasures {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*Адаптивная антенная решетка предотвратила атаку на систему GPS/GLONASS.*/
    @Column(name = "d3")
    private int D3;

    /*Парольная защита инженерного АРМ, используемого при локальном доступе, предотвратила доступ и дальнейшую подмену
    конфигурации и/или уставок ИЭУ.*/
    @Column(name = "d7")
    private int D7;

    /*Механизмы безопасности, реализующие парольную защиту в ПО АРМ инженера РЗА, предотвратили доступ и дальнейшую
    подмену уставок ИЭУ.*/
    @Column(name = "d11")
    private int D11;

    /*Наложенные СЗИ на АРМ инженера РЗА (антивирус и/или система защиты конечных точек) предотвратят работу стороннего
    ПО на АРМ инженера РЗА.*/
    @Column(name = "d19")
    private int D19;

    /*Межсетевой экран на ЦПС предотвратил передачу стороннего трафика.*/
    @Column(name = "d20")
    private int D20;

    /*Система обнаружения вторжений детектировала нелегитимный трафик.*/
    @Column(name = "d21")
    private int D21;

    /*Встроенный в коммутатор механизм защиты от DoS предотвратил атаку типа отказ в обслуживании.*/
    @Column(name = "d24")
    private int D24;
}


