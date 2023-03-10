package com.mycompany.webtechnikonproject.model;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "property")
@Table
public class Property extends PersistentClass {

    @Column(unique = true)
    private int e9;
    private String address;
    private String yearOfConstruction;
    @Enumerated(EnumType.STRING)
    private PropertyType propertyType;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private PropertyOwner owner;
    @OneToMany(mappedBy = "property", orphanRemoval = true)
    private List<Repair> repairs;

    public Property(int e9, int par1, String address, String yearOfConstruction, PropertyType propertyType) {
        this.e9 = e9;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
    }
}
