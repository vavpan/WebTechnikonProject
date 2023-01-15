package com.mycompany.webtechnikonproject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Data
@NoArgsConstructor
@Entity(name = "propertyowner")
@Table
public class PropertyOwner extends PersistentClass {

    // id is in PersistenceClass
    private int vat;
    private String name;
    private String surname;
    private String address;
    private String phoneNumber;

    @Column(unique = true)
    private String email;
   
    private String username;
    private String password;
    @OneToMany(mappedBy = "owner", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Property> properties;

    public PropertyOwner(int vat, String name, String surname, String address, String phoneNumber, String email, String username, String password) {
        this.vat = vat;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.username = username;
        this.password = password;
    }

}
