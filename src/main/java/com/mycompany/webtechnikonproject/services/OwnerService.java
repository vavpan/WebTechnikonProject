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

    List<PropertyOwnerDto> getAllOwners();

    List<PropertyDto> getAllProperties();

    List<RepairDto> getAllRepairs();

    RestApiResult<PropertyDto> getProperty(int propertyId);

    PropertyDto getPropertyByE9(int e9);

    List<PropertyDto> getPropertiesByOwnerVat(int vat);

    RestApiResult<RepairDto> getRepair(int repairId);

    void registerNewPropertyDto(PropertyDto propertyDto);

    boolean deletePropertyOwner(int ownerId);

    boolean deleteRepair(int repairId);

    RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto propertyOwnerDto, int id);

    PropertyOwnerDto updateAddress(int id, String newAddress);

    PropertyOwnerDto updateEmail(int id, String email);

    PropertyOwnerDto updateName(int id, String name);

    PropertyOwnerDto updateSurname(int id, String surname);

    PropertyOwnerDto updatePassword(int id, String password);

    PropertyOwnerDto updatePhoneNumber(int id, String phoneNumber);

    PropertyOwnerDto updateUsername(int id, String username);

    PropertyOwnerDto updateVat(int id, int vat);

    RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, int id);

    PropertyDto updatePropertyAddress(int propertyId, String address);

    PropertyDto updateYearOfConstruction(int propertyId, String yearOfConstruction);

    PropertyDto updatePropertyType(int propertyId, PropertyType propertyType);

    RestApiResult<RepairDto> updateRepair(RepairDto repairDto, int id);

    List<RepairDto> getRepairsBySubmissionDate(String submissionDate);

    List<RepairDto> getRepairsOfOwner(int id);

    RepairDto updateRepairType(int id, RepairType repairType);

    RepairDto updateRepairDescription(int id, String repairDescription);

    RepairDto updateSubmissionDate(int id, String submissionDate);

    RepairDto updateWorkDescription(int id, String workDescription);

    RepairDto updateStartDate(int id, String startDate);

    RepairDto updateEndDate(int id, String endDate);

    RepairDto updateCost(int id, double cost);

    RepairDto updateAcceptance(int id, boolean acceptance);

    RepairDto updateRepairStatus(int id, RepairStatus repairStatus);

    RepairDto updateActualStartDate(int id, String actualStartDate);

    RepairDto updateActualEndDate(int id, String actualEndDate);

}
