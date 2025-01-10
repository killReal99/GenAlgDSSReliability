package org.mpei.nti.substation.substationStructures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubstationMeasuresPerYear {
    private float totalPrice;
    private float opexPrice;
    private float capexPrice;
    private int architectureType;
    private int yearNumber;
    private OrganizationalMeasures organizationalMeasures;
    private ImprosedMeasures improsedMeasures;
    private List<IED> iedList;
}
