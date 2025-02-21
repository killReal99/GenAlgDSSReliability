package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Protection {
    private ProtectionType protectionType;
    private EquipmentType equipmentType;
    private float falsePositive;
    private float overTriggering;
}
