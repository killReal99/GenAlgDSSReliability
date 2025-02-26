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
@Table(name = "organizational_measures")
public class OrganizationalMeasures {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    /*Организационные меры безопасности предотвратят подключение нарушителя к шине процесса.*/
    @Column(name = "d1")
    private int D1;

    /*Организационные меры безопасности предотвратят подключение нарушителя к инженерному АРМ, используемому для
    локального доступа.*/
    @Column(name = "d6")
    private int D6;

    /*Организационные меры безопасности предотвратят подключение нарушителя к АРМ инженера РЗА.*/
    @Column(name = "d10")
    private int D10;

    /*Организационные меры безопасности по защите сервисного порта предотвратила физический доступ нарушителя к
    конфигурированию ИЭУ РЗА*/
    @Column(name = "d12")
    private int D12;

    /*Организационные меры безопасности предотвратят сетевое подключение нарушителя к шине станции.*/
    @Column(name = "d16")
    private int D16;

    /*Работники ЦПС отреагировали на сигнал системы обнаружения вторжения и предотвратили передачу трафика с
    некорректной информацией.*/
    @Column(name = "d22")
    private int D22;
}
