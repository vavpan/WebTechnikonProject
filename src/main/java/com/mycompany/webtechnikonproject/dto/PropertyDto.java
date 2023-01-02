/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.dto;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
public class PropertyDto {
    
    private int e9;
    private String address;
    private String yearOfConstruction;
    private PropertyType propertyType;
    private PropertyOwnerDto owner;
    private List<RepairDto> repairs;

    public PropertyDto(int e9, String address, String yearOfConstruction, PropertyType propertyType, PropertyOwnerDto owner, List<RepairDto> repairs) {
        this.e9 = e9;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
        this.owner = owner;
        this.repairs = repairs;
    }
   
    public PropertyDto(Property property) {
        this.e9 = property.getE9();
        this.address = property.getAddress();
        this.yearOfConstruction = property.getYearOfConstruction();
        this.propertyType = property.getPropertyType();
    }


public Property asProperty() {
        Property property = new Property();
        property.setE9(e9);
        property.setAddress(address);
        property.setYearOfConstruction(yearOfConstruction);
        property.setPropertyType(propertyType);
        
        return property;
    }

}
