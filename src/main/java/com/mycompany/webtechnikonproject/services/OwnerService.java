package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import java.util.List;

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
    void registerNewProperty(PropertyOwner propertyOwner);

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
    Repair getRepairFromConsole(Property property);

    /**
     * This method registers a new repair that the owner inserted in the
     * console.
     *
     * @param property
     */
    void registerRepair(Property property);

    void displayAllOwners();

    void displayOwnersProperties(int ownerId);

    /**
     * This method checks if the property inserted fulfills the business
     * requirements
     *
     * @param property
     * @throws PropertyException
     */
    void isValidProperty(Property property) throws PropertyException;

    /**
     * This method changes acceptance status of a repair to true
     *
     * @param repair
     */
    void acceptRepair(Repair repair);

    /**
     * This method changes acceptance status of a repair to false
     *
     * @param repair
     */
    void declineRepair(Repair repair);

    boolean deleteProperty(int id);

    // REST API METHODS
    void createPropertyOwner(PropertyOwnerDto ownerDto);

    void createRepair(RepairDto repair);

    RestApiResult<PropertyOwnerDto> getOwner(int ownerId);

    RestApiResult<PropertyOwnerDto> getOwnerByVat(int vat);

    RestApiResult<PropertyOwnerDto> getOwnerByEmail(String email);

    RestApiResult<PropertyDto> getProperty(int propertyId);

    RestApiResult<RepairDto> getRepair(int repairId);

    void registerNewPropertyDto(PropertyDto propertyDto);

    boolean deletePropertyOwner(int ownerId);

    boolean deleteRepair(int repairId);

    RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto propertyOwnerDto, int id);

    RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, int id);

    RestApiResult<RepairDto> updateRepair(RepairDto repairDto, int id);

}
