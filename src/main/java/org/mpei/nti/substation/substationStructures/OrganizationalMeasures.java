package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationalMeasures {
    /*Организационные меры безопасности предотвратят подключение нарушителя к шине процесса.*/
    private int D1;
    /*Организационные меры безопасности предотвратят подключение нарушителя к инженерному АРМ, используемому для
    локального доступа.*/
    private int D6;
    /*Организационные меры безопасности предотвратят подключение нарушителя к АРМ инженера РЗА.*/
    private int D10;
    /*Организационные меры безопасности по защите сервисного порта предотвратила физический доступ нарушителя к
    конфигурированию ИЭУ РЗА*/
    private int D12;
    /*Организационные меры безопасности предотвратят сетевое подключение нарушителя к шине станции.*/
    private int D16;
    /*Работники ЦПС отреагировали на сигнал системы обнаружения вторжения и предотвратили передачу трафика с
    некорректной информацией.*/
    private int D22;
}
