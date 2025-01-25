package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mpei.nti.utils.Equipment;
import org.mpei.nti.utils.Protection;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Protections {
    private Protection nameOfProtection;
    private Equipment equipmentType;
}
