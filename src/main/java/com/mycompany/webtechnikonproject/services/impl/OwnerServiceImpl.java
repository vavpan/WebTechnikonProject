package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.exceptions.ExceptionsCodes;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.OwnerService;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import com.mycompany.webtechnikonproject.util.JpaUtil;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class OwnerServiceImpl implements OwnerService {

    private final Properties sqlCommands = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream config = loader.getResourceAsStream("sql.properties")) {
            sqlCommands.load(config);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }
    private static final Logger logger = LogManager.getLogger(OwnerServiceImpl.class);

    @Inject
    private PropertyOwnerRepository propertyOwnerRepository;

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private RepairRepository repairRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PropertyOwner getOwnerFromConsole() {
        PropertyOwner propertyOwner = new PropertyOwner();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give the new owner's  details in the following form: ");
        System.out.println("VAT (Nine digits), Name, Surename, Address, phone, email, user name, password ");
        String ownerDetails = scanner.nextLine();
        String[] ownerDetailsSplit = ownerDetails.split(",");
        propertyOwner.setVat(Integer.parseInt(ownerDetailsSplit[0].trim()));
        propertyOwner.setName(ownerDetailsSplit[1].trim());
        propertyOwner.setSurname(ownerDetailsSplit[2].trim());
        propertyOwner.setAddress(ownerDetailsSplit[3].trim());
        propertyOwner.setPhoneNumber(ownerDetailsSplit[4].trim());
        propertyOwner.setEmail(ownerDetailsSplit[5]);
        propertyOwner.setUsername(ownerDetailsSplit[6]);
        propertyOwner.setPassword(ownerDetailsSplit[7]);
        return propertyOwner;
    }

    @Override
    public void registerNewOwner() {
        PropertyOwner propertyOwner = getOwnerFromConsole();
        propertyOwnerRepository.create(propertyOwner);
        registerNewProperty(propertyOwner);
        registerNewProperty(propertyOwner);
        logger.info("The new owner with VAT number {} has been registered", propertyOwner.getVat());
    }

    @Override
    public Property getPropertyFromConsole(PropertyOwner propertyOwner) {
        Property property = new Property();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Give the new property's  details in the following form: ");
        System.out.println("E9 number, address, type(DETACHED_HOUSE MAISONETTE APARTMENT_BUILDING)");
        String propertyDetails = scanner.nextLine();
        String[] propertyDetailsSplit = propertyDetails.split(",");
        property.setE9(Integer.parseInt(propertyDetailsSplit[0].trim()));
        property.setAddress(propertyDetailsSplit[1].trim());
        property.setPropertyType(PropertyType.valueOf(propertyDetailsSplit[2].trim().toUpperCase()));
        property.setOwner(propertyOwner);
        return property;
    }

    @Override
    public void registerNewProperty(PropertyOwner propertyOwner) {
        Property property = getPropertyFromConsole(propertyOwner);
        try {
            isValidProperty(property);
            logger.info("The new property with E9 number {} has been registered", property.getE9());
        } catch (PropertyException e) {
            System.out.println(e.getMessage());
        }
        propertyRepository.create(property);
    }

    @Override
    public Repair getRepairFromConsole(Property property) {
        Scanner scanner = new Scanner(System.in);
        Repair repair = new Repair();
        System.out.println("Give the new repair's  details in the following form: ");
        System.out.println("repair type (PAINTING INSULATION FRAMES PLUMBING ELECTRICAL_WORK), repair description, "
                + "submission date (YYYY-MM-DD), work description, start date  (YYYY-MM-DD), end date  (YYYY-MM-DD),"
                + " cost, acceptance (True, False), repair status (PENDING DECLINED IN_PROGRESS COMPLETE), "
                + " actual start date (YYYY-MM-DD), actual end date (YYYY-MM-DD)");
        String repairDetails = scanner.nextLine();
        String[] repairDetailsSplit = repairDetails.split(",");
        repair.setProperty(property);
        repair.setRepairType(RepairType.valueOf(repairDetailsSplit[0].trim().toUpperCase()));
        repair.setRepairDescription(repairDetailsSplit[1].trim());
        repair.setSubmissionDate(repairDetailsSplit[2].trim());
        repair.setWorkDescription(repairDetailsSplit[3].trim());
        repair.setStartDate(repairDetailsSplit[4].trim());
        repair.setEndDate(repairDetailsSplit[5].trim());
        repair.setCost(Double.parseDouble(repairDetailsSplit[6].trim()));
        repair.setAcceptance(Boolean.parseBoolean(repairDetailsSplit[7].trim()));
        repair.setRepairStatus(RepairStatus.valueOf(repairDetailsSplit[8].trim().toUpperCase()));
        repair.setActualStartDate(repairDetailsSplit[9].trim());
        repair.setActualEndDate(repairDetailsSplit[10].trim());
        return repair;
    }

    @Override
    public void registerRepair(Property property) {
        Repair repair = getRepairFromConsole(property);
        repairRepository.create(repair);
        repairRepository.updatePropertyId(repair.getId(), property.getId());
        logger.info("The new repair has been registered");
    }

    public void displayAllOwners() {
        List<PropertyOwner> owners = propertyOwnerRepository.readAll();
        for (PropertyOwner owner : owners) {
            System.out.println(owner.getId() + " " + owner.getName() + " " + owner.getSurname()
                    + " " + owner.getAddress() + " " + owner.getPhoneNumber()
                    + " " + owner.getEmail() + " " + owner.getUsername() + " " + owner.getPassword());
        }
    }

    @Override
    public void displayOwnersProperties(int ownerId) {
        TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.report.properties"), Tuple.class)
                .setParameter("ownerid", ownerId);
        List<Tuple> resultList = query.getResultList();
        resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(0) + "| Address: " + tuple.get(1) + "| E9: " + tuple.get(2) + "| Property type: " + tuple.get(3) + "| Year of construction: " + tuple.get(4)));
    }

    @Override
    public void isValidProperty(Property property) throws PropertyException {
        if (String.valueOf(property.getE9()).length() != 9) {
            throw new PropertyException(ExceptionsCodes.PROPERTY_E9_NOT_VALID);
        }
        if (property.getAddress() == null) {
            throw new PropertyException(ExceptionsCodes.MISSING_PROPERTY_ADDRESS);
        }
        if ((property.getPropertyType() != PropertyType.APARTMENT_BUILDING)
                && (property.getPropertyType() != PropertyType.DETACHED_HOUSE)
                && (property.getPropertyType() != PropertyType.MAISONETTE)) {
            throw new PropertyException(ExceptionsCodes.PROPERTY_TYPE_NOT_VALID);
        }
    }

    @Override
    public void acceptRepair(Repair repair) {
        repairRepository.updateAcceptance(repair.getId(), true);
    }

    @Override
    public void declineRepair(Repair repair) {
        repairRepository.updateAcceptance(repair.getId(), false);
    }

    @Override
    public void createPropertyOwner(PropertyOwnerDto ownerDto) {
        propertyOwnerRepository.createPropertyOwner(ownerDto.asOwner());
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwner(int ownerId) {
        PropertyOwnerDto ownerDto = new PropertyOwnerDto(propertyOwnerRepository.findById(ownerId));
        return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwnerByVat(int vat) {
        PropertyOwnerDto ownerDto = new PropertyOwnerDto(propertyOwnerRepository.findByVat(vat));
        return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwnerByEmail(String email) {
        PropertyOwnerDto ownerDto = new PropertyOwnerDto(propertyOwnerRepository.findByEmail(email));
        return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
    }

    @Override
    @Transactional
    public List<PropertyOwnerDto> getAllOwners() {
        return propertyOwnerRepository.findAll().stream().map(PropertyOwnerDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PropertyDto> getAllProperties() {
      return propertyRepository.findAll().stream().map(PropertyDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RepairDto> getAllRepairs() {
       return repairRepository.findAll().stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @Override
    public RestApiResult<PropertyDto> getProperty(int propertyId) {
        PropertyDto propertyDto = new PropertyDto(propertyRepository.findById(propertyId));
        return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");
    }

    @Override
    public RestApiResult<RepairDto> getRepair(int repairId) {
        RepairDto repairDto = new RepairDto(repairRepository.findById(repairId));
        return new RestApiResult<RepairDto>(repairDto, 0, "successful");
    }

    @Override
    public void registerNewPropertyDto(PropertyDto propertyDto) {
        propertyRepository.create(propertyDto.asProperty());
    }

    @Override
    public boolean deletePropertyOwner(int ownerId) {
        return propertyOwnerRepository.deleteOwner(ownerId);
    }

    @Override
    public boolean deleteRepair(int repairId) {
        return repairRepository.deleteRepair(repairId);
    }

    @Override
    public boolean deleteProperty(int id) {
        propertyRepository.deleteProperty(id);
        return true;
    }

    @Override
    public void createRepair(RepairDto repair) {
        repairRepository.create(repair.asRepair());
    }

    @Override
    public RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto propertyOwnerDto, int id) {
        try {
            PropertyOwner existingOwner = propertyOwnerRepository.findById(id);
            existingOwner.setVat(propertyOwnerDto.getVat());
            existingOwner.setName(propertyOwnerDto.getName());
            existingOwner.setSurname(propertyOwnerDto.getSurname());
            existingOwner.setAddress(propertyOwnerDto.getAddress());
            existingOwner.setPhoneNumber(propertyOwnerDto.getPhoneNumber());
            existingOwner.setEmail(propertyOwnerDto.getEmail());
            existingOwner.setUsername(propertyOwnerDto.getUsername());
            existingOwner.setPassword(propertyOwnerDto.getPassword());
            entityManager.getTransaction().begin();
            entityManager.merge(existingOwner);
            entityManager.getTransaction().commit();
            return new RestApiResult<PropertyOwnerDto>(propertyOwnerDto, 0, "successful");
        } catch (Exception e) {
            return new RestApiResult<PropertyOwnerDto>(propertyOwnerDto, 0, "successful");

        }

    }

    @Override
    public RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, int id) {
        try {
            Property existingProperty = propertyRepository.findById(id);
            existingProperty.setE9(propertyDto.getE9());
            existingProperty.setAddress(propertyDto.getAddress());
            existingProperty.setYearOfConstruction(propertyDto.getYearOfConstruction());
            existingProperty.setPropertyType(propertyDto.getPropertyType());
            entityManager.getTransaction().begin();
            entityManager.merge(existingProperty);
            entityManager.getTransaction().commit();
            return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");
        } catch (Exception e) {
            return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");

        }

    }

    @Override
    public RestApiResult<RepairDto> updateRepair(RepairDto repairDto, int id) {
        try {
            Repair existingRepair = repairRepository.findById(id);
            existingRepair.setRepairType(repairDto.getRepairType());
            existingRepair.setRepairDescription(repairDto.getRepairDescription());
            existingRepair.setSubmissionDate(repairDto.getSubmissionDate());
            existingRepair.setWorkDescription(repairDto.getWorkDescription());
            existingRepair.setStartDate(repairDto.getStartDate());
            existingRepair.setEndDate(repairDto.getEndDate());
            existingRepair.setCost(repairDto.getCost());
            existingRepair.setRepairStatus(repairDto.getRepairStatus());
            existingRepair.setActualStartDate(repairDto.getActualStartDate());
            existingRepair.setActualEndDate(repairDto.getActualEndDate());
            entityManager.getTransaction().begin();
            entityManager.merge(existingRepair);
            entityManager.getTransaction().commit();
            return new RestApiResult<RepairDto>(repairDto, 0, "successful");

        } catch (Exception e) {
            return new RestApiResult<RepairDto>(repairDto, 0, "successful");

        }

    }

    @Override
    public PropertyOwnerDto updateAddress(int id, String newAddress) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setAddress(newAddress);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updateEmail(int id, String email) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setEmail(email);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updateName(int id, String name) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        //if null exception
        propertyOwner.setName(name);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);

    }

    @Override
    public PropertyOwnerDto updateSurname(int id, String surname) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setSurname(surname);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updatePassword(int id, String password) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setPassword(password);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updatePhoneNumber(int id, String phoneNumber) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setPhoneNumber(phoneNumber);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updateUsername(int id, String username) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setUsername(username);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyOwnerDto updateVat(int id, int vat) {
        PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
        propertyOwner.setVat(vat);
        propertyOwnerRepository.create(propertyOwner);
        return new PropertyOwnerDto(propertyOwner);
    }

    @Override
    public PropertyDto updatePropertyAddress(int propertyId, String address) {
        Property property = propertyRepository.read(propertyId);
        property.setAddress(address);
        propertyRepository.create(property);
        return new PropertyDto(property);

    }

    @Override
    public PropertyDto updateYearOfConstruction(int propertyId, String yearOfConstruction) {
        Property property = propertyRepository.read(propertyId);
        property.setYearOfConstruction(yearOfConstruction);
        propertyRepository.create(property);
        return new PropertyDto(property);

    }

    @Override
    public PropertyDto updatePropertyType(int propertyId, PropertyType propertyType) {
        Property property = propertyRepository.read(propertyId);
        property.setPropertyType(propertyType);
        propertyRepository.create(property);
        return new PropertyDto(property);

    }

    @Override
    public RepairDto updateRepairType(int id, RepairType repairType) {
        Repair repair = repairRepository.read(id);
        repair.setRepairType(repairType);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateRepairDescription(int id, String repairDescription) {
        Repair repair = repairRepository.read(id);
        repair.setRepairDescription(repairDescription);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateSubmissionDate(int id, String submissionDate) {
        Repair repair = repairRepository.read(id);
        repair.setSubmissionDate(submissionDate);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateWorkDescription(int id, String workDescription) {
        Repair repair = repairRepository.read(id);
        repair.setWorkDescription(workDescription);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateStartDate(int id, String startDate) {
        Repair repair = repairRepository.read(id);
        repair.setStartDate(startDate);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateEndDate(int id, String endDate) {
        Repair repair = repairRepository.read(id);
        repair.setEndDate(endDate);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateCost(int id, double cost) {
        Repair repair = repairRepository.read(id);
        repair.setCost(cost);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateAcceptance(int id, boolean acceptance) {
        Repair repair = repairRepository.read(id);
        repair.setAcceptance(acceptance);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateRepairStatus(int id, RepairStatus repairStatus) {
        Repair repair = repairRepository.read(id);
        repair.setRepairStatus(repairStatus);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateActualStartDate(int id, String actualStartDate) {
        Repair repair = repairRepository.read(id);
        repair.setActualStartDate(actualStartDate);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

    @Override
    public RepairDto updateActualEndDate(int id, String actualEndDate) {
        Repair repair = repairRepository.read(id);
        repair.setActualEndDate(actualEndDate);
        repairRepository.create(repair);
        return new RepairDto(repair);
    }

}
