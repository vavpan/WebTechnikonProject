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

    private int id;
    private int e9;
    private String address;
    private String yearOfConstruction;
    private PropertyType propertyType;
    private PropertyOwnerDto owner;
    private List<RepairDto> repairs;
    
    public PropertyDto(int id, int e9, String address, String yearOfConstruction, PropertyType propertyType, PropertyOwnerDto owner, List<RepairDto> repairs) {
        this.id = id;
        this.e9 = e9;
        this.address = address;
        this.yearOfConstruction = yearOfConstruction;
        this.propertyType = propertyType;
        this.owner = owner;
        this.repairs = repairs;
    }
    
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
