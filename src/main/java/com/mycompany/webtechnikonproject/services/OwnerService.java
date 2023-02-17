package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import java.util.List;

public interface OwnerService {


    void registerNewOwner();
    Property getPropertyFromConsole(PropertyOwner propertyOwner);
    PropertyOwner getOwnerFromConsole();
    void displayAllOwners();
    void displayOwnersProperties(int ownerId);
    RestApiResult<PropertyOwnerDto> getOwner(int ownerId);

    RestApiResult<PropertyOwnerDto> getOwnerByVat(int vat);

    RestApiResult<PropertyOwnerDto> getOwnerByEmail(String email);

    List<PropertyOwnerDto> getAllOwners();

    boolean checkVat(int vat);

    boolean deletePropertyOwner(int ownerId);

    RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto propertyOwnerDto, int id);

    PropertyOwnerDto updateAddress(int id, String newAddress);

    PropertyOwnerDto updateEmail(int id, String email);

    PropertyOwnerDto updateName(int id, String name);

    PropertyOwnerDto updateSurname(int id, String surname);

    PropertyOwnerDto updatePassword(int id, String password);

    PropertyOwnerDto updatePhoneNumber(int id, String phoneNumber);

    PropertyOwnerDto updateUsername(int id, String username);

    PropertyOwnerDto updateVat(int id, int vat);

    PropertyOwnerDto createPropertyOwner(PropertyOwnerDto ownerDto);

    PropertyOwnerDto getUser(String authorization);

    void registerNewProperty(PropertyOwner propertyOwner);

    void isValidProperty(Property property) throws PropertyException;

}
