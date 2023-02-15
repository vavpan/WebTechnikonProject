package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

public interface OwnerService {

    /**
     * This method registers a new Owner that got inserted in the console.
     */
    void registerNewOwner();

    /**
     * This method registers a new Property that the owner inserted in the
     * console.
     *
     * @param propertyOwner
     */
    /**
     * This method gets a new Property from the console. To be used in
     * registering a new property by its owner.
     *
     * @param propertyOwner
     * @return
     */
    Property getPropertyFromConsole(PropertyOwner propertyOwner);

    /**
     * This method gets a anew Property owner from the console. To be used in
     * registering a new owner.
     *
     * @return
     */
    PropertyOwner getOwnerFromConsole();

    /**
     * This method gets a new Repair from the console. To be used in registering
     * a new Repair.
     *
     * @param property
     * @return
     */
    /**
     * This method registers a new repair that the owner inserted in the
     * console.
     *
     * @param property
     */
    void displayAllOwners();

    void displayOwnersProperties(int ownerId);

    /**
     * This method checks if the property inserted fulfills the business
     * requirements
     *
     * @param property
     * @throws PropertyException
     */
    /**
     * This method changes acceptance status of a repair to true
     *
     * @param repair
     */
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
