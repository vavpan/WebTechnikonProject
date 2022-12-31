/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.dto;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
public class PropertyDto {
    
    private int id;
    private int e9;
    private String address;
    private String yearOfConstruction;
    private PropertyType propertyType;


    public PropertyDto(Property property) {
        this.id = property.getId();
        this.e9 = property.getE9();
        this.address = property.getAddress();
        this.yearOfConstruction = property.getYearOfConstruction();
        this.propertyType = property.getPropertyType();

    }

    public Property asProperty() {
        Property property = new Property();
        property.setId(id);
        property.setE9(e9);
        property.setAddress(address);
        property.setYearOfConstruction(yearOfConstruction);
        property.setPropertyType(propertyType);
        return property;
    }

}
