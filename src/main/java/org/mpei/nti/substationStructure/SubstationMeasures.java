package org.mpei.nti.substationStructure;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SubstationMeasures {
    private float totalPrice;
    private float opexPrice;
    private float capexPrice;
    private int architectureType;
    private int yearNumber;
    private OrganizationalMeasures organizationalMeasures;
    private ImprosedMeasures improsedMeasures;
    private List<EmbeddedMeasures> embeddedMeasuresList;
}
