package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.Protections;
import org.mpei.nti.substation.substationStructures.SubstationMeasures;
import org.mpei.nti.substation.substationStructures.SubstationMeasuresPerYear;
import org.mpei.nti.utils.Equipment;
import org.mpei.nti.utils.Protection;

import java.util.ArrayList;
import java.util.List;

public class ProtectionsSet {

    public static List<Protections> lineProtectionsSetGeneration() {
        List<Protections> protectionsList = new ArrayList<>();
        protectionsList.add(new Protections(Protection.DZL, Equipment.LINE));
        protectionsList.add(new Protections(Protection.MTZ, Equipment.LINE));
        protectionsList.add(new Protections(Protection.TZNP, Equipment.LINE));
        protectionsList.add(new Protections(Protection.DZ, Equipment.LINE));
        return protectionsList;
    }

}
