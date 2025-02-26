package org.mpei.nti.substation.substationStructures;

import jakarta.persistence.*;
import lombok.*;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "protection")
public class IED {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "protection_type")
    private String nameOfIED;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type")
    private EquipmentType equipmentTypeName;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Protection> protections;

    /*Криптографическая защита пакетов SV с использованием имитовставки, предотвратит обработку SV потока,
сгенерированного нарушителем.*/
    @Column(name = "d2")
    private int D2;

    /*Парольная защита, соответствующая корпоративным правилам к парольной защите, ИЭУ предотвратила доступ и дальнейшую
    подмену конфигурации и/или уставок.*/
    @Column(name = "d4")
    private int D4;

    /*Разграничение доступа, реализованное в необходимом объеме, предотвратило подмену конфигурации и/или уставок ИЭУ.*/
    @Column(name = "d5")
    private int D5;

    /*Криптографическая защита протокола MMS с использованием TLS предотвратила передачу информации по MMS,
    сгенерированной нарушителем.*/
    @Column(name = "d8")
    private int D8;

    /*Механизм удаленной аутентификации к ИЭУ предотвратит несанкционированное удаленное подключение.*/
    @Column(name = "d9")
    private int D9;

    /*Механизм доверенного обновления предотвратил установку измененной прошивки ИЭУ РЗА.*/
    @Column(name = "d13")
    private int D13;

    /*Контроль целостности предотвратил подмену ПО на ИЭУ РЗА.*/
    @Column(name = "d14")
    private int D14;

    /*Доверенная загрузка предотвратил подмену системного ПО на ИЭУ РЗА.*/
    @Column(name = "d15")
    private int D15;

    /*Криптографическая защита пакетов GOOSE с использованием имитовставки, предотвратит обработку GOOSE потока,
    сгенерированного нарушителем.*/
    @Column(name = "d17")
    private int D17;

    /*Межсетевой экран в ИЭУ предотвратил передачу некорректного трафика.*/
    @Column(name = "d18")
    private int D18;

    /*Встроенный в ИЭУ механизм защиты от DoS предотвратил атаку типа отказ в обслуживании.*/
    @Column(name = "d23")
    private int D23;

    @Column(name = "failure_triggering")
    private float failureTriggering;
}
