package org.mpei.nti.substation.substationGeneration;

import org.mpei.nti.substation.substationStructures.OrganizationalMeasures;

public class OrganizationalMeasuresGeneration {

    public static OrganizationalMeasures organizationalMeasuresGeneration(int D1, int D6, int D10, int D12, int D16, int D22) {
        OrganizationalMeasures organizationalMeasures = new OrganizationalMeasures();
        organizationalMeasures.setD1(D1);
        organizationalMeasures.setD6(D6);
        organizationalMeasures.setD10(D10);
        organizationalMeasures.setD12(D12);
        organizationalMeasures.setD16(D16);
        organizationalMeasures.setD22(D22);
        return organizationalMeasures;
    }

}
