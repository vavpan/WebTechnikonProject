package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import java.util.List;

public interface PropertyService {

    RestApiResult<PropertyDto> getProperty(int propertyId);

    List<PropertyDto> getAllProperties();

    PropertyDto getPropertyByE9(int e9);

    boolean checkE9(int e9);

    List<PropertyDto> getPropertiesByOwnerVat(int vat);

    void registerNewPropertyDto(PropertyDto propertyDto);

    RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, int id);

    PropertyDto updateE9(int propertyId, int e9);

    PropertyDto updatePropertyAddress(int propertyId, String address);

    PropertyDto updateYearOfConstruction(int propertyId, String yearOfConstruction);

    PropertyDto updatePropertyType(int propertyId, PropertyType propertyType);

    boolean deleteProperty(int id);

}
