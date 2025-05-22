package org.mpei.nti.substation.substationStructures;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.mpei.nti.substation.substationStructures.Enums.EquipmentType;
import org.mpei.nti.substation.substationStructures.Enums.ProtectionType;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table (name = "protection")
public class Protection {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(name = "protection_type")
    private ProtectionType protectionType;

    @Enumerated(EnumType.STRING)
    @Column(name = "equipment_type")
    private EquipmentType equipmentType;

    @Column(name = "false_positive")
    private float falsePositive;

    @Column(name = "over_triggering")
    private float overTriggering;
}
