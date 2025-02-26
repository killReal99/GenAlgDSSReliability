package org.mpei.nti.substation.substationStructures;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "substation_measures")
public class SubstationMeasures {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "total_price")
    private float totalPrice;

    @Column(name = "opex_price")
    private float opexPrice;

    @Column(name = "capex_price")
    private float capexPrice;

    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubstationMeasuresPerYear> substationMeasuresPerYear;
}
