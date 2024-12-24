package org.mpei.nti.substationStructure;

import lombok.Data;

import java.util.List;

@Data
public class SubstationMeasures {
    float totalPrice;
    int architectureType;
    int yearNumber;
    OrganizationalMeasures organizationalMeasures;
    ImprosedMeasures improsedMeasures;
    List<EmbeddedMeasures> embeddedMeasuresList;
}
