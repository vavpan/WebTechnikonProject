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
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.StringTokenizer;
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
    public PropertyOwnerDto createPropertyOwner(PropertyOwnerDto ownerDto) {
        try {
            PropertyOwner owner = ownerDto.asOwner();
            List<PropertyOwner> emails = propertyOwnerRepository.findEmails(owner.getEmail());
            if (!emails.isEmpty()) {
                logger.warn("Email already in use by another property owner");
                throw new IllegalArgumentException("Email already in use by another property owner");
            }

            List<PropertyOwner> vats = propertyOwnerRepository.findVats(owner.getVat());
            if (!vats.isEmpty()) {
                logger.warn("User with this Vat already exists");
                throw new IllegalArgumentException("User with this Vat already exists");
            }

            List<PropertyOwner> usernames = propertyOwnerRepository.findUsernames(owner.getUsername());
            if (!usernames.isEmpty()) {
                logger.warn("Email already in use by another property owner");
                throw new IllegalArgumentException("Username already in use by another property owner");
            }

            propertyOwnerRepository.createPropertyOwner(ownerDto.asOwner());
            logger.info("Adding new owner: " + ownerDto.toString());
            return new PropertyOwnerDto(owner);
        } catch (IllegalArgumentException e) {
            logger.error("Bad Request: " + e.getMessage());
            return null;
        }
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwner(int ownerId) {
        try {
            PropertyOwner owner = propertyOwnerRepository.findById(ownerId);
            if (owner != null) {
                logger.info("Returning owner with id: " + ownerId);
                PropertyOwnerDto ownerDto = new PropertyOwnerDto(owner);
                return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
            } else {
                logger.warn("Owner cannot be found");
                throw new Exception("Owner not found");
            }
        } catch (Exception e) {
            logger.error("Error has been occured");
            return new RestApiResult<PropertyOwnerDto>(null, 1, e.getMessage());
        }
    }

    @Override
    public RestApiResult<PropertyDto> getProperty(int propertyId) {
        try {
            PropertyDto propertyDto = new PropertyDto(propertyRepository.findById(propertyId));
            if (propertyDto != null) {
                logger.info("Returning property with id: " + propertyId);
                return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");
            } else {
                logger.warn("Property cannot be found");
                throw new Exception("Property not found");
            }

        } catch (Exception e) {
            logger.error("Error has been occured");
            return new RestApiResult<PropertyDto>(null, 1, e.getMessage());
        }
    }

    @Override
    public RestApiResult<RepairDto> getRepair(int repairId) {
        try {
            RepairDto repairDto = new RepairDto(repairRepository.findById(repairId));
            if (repairDto != null) {
                logger.info("Returning repair with id: " + repairId);
                return new RestApiResult<RepairDto>(repairDto, 0, "successful");
            } else {
                logger.warn("Repair cannot be found");
                throw new Exception("Repair not found");
            }
        } catch (Exception e) {
            logger.error("Error has been occured");
            return new RestApiResult<RepairDto>(null, 1, e.getMessage());
        }
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwnerByVat(int vat
    ) {
        PropertyOwnerDto ownerDto = new PropertyOwnerDto(propertyOwnerRepository.findByVat(vat));
        logger.info("Returning owner with vat : " + vat);
        return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
    }

    @Override
    public RestApiResult<PropertyOwnerDto> getOwnerByEmail(String email
    ) {
        logger.info("Getting owner with e-mail: " + email);
        PropertyOwnerDto ownerDto = new PropertyOwnerDto(propertyOwnerRepository.findByEmail(email));
        return new RestApiResult<PropertyOwnerDto>(ownerDto, 0, "successful");
    }

    @Override
    @Transactional
    public List<PropertyOwnerDto> getAllOwners() {
        logger.info("Getting all owners");
        return propertyOwnerRepository.findAll().stream().map(PropertyOwnerDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PropertyDto> getAllProperties() {
        logger.info("Getting all properties");
        return propertyRepository.findAll().stream().map(PropertyDto::new).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<RepairDto> getAllRepairs() {
        logger.info("Getting all repairs");
        return repairRepository.findAll().stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @Override
    public void registerNewPropertyDto(PropertyDto propertyDto
    ) {
        try {
            Property property = propertyDto.asProperty();
            List<Property> e9s = propertyRepository.findE9s(property.getE9());
            if (!e9s.isEmpty()) {
                logger.warn("E9 already in use by another property");
                throw new IllegalArgumentException("E9 already in use by another property");
            }
            propertyRepository.create(property);
            logger.info("Adding new property: " + propertyDto.toString());
        } catch (IllegalArgumentException e) {
            logger.error("Bad Request: " + e.getMessage());
        }
    }

    @Override
    public boolean deletePropertyOwner(int ownerId
    ) {
        logger.info("Deleting PropertyOwner with id: " + ownerId);
        return propertyOwnerRepository.deleteOwner(ownerId);
    }

    @Override
    public boolean deleteRepair(int repairId
    ) {
        logger.info("Deleting repair with id: " + repairId);
        return repairRepository.deleteRepair(repairId);
    }

    @Override
    public boolean deleteProperty(int id
    ) {
        logger.info("Deleting property with id: " + id);
        propertyRepository.deleteProperty(id);
        return true;
    }

    @Override
    public void createRepair(RepairDto repair
    ) {
        try {
            logger.info("Adding new repair " + repair.toString());
            repairRepository.create(repair.asRepair());
        } catch (Exception e) {
            logger.error("Error creating new repair: " + e.getMessage());
        }
    }

    @Override
    public RestApiResult<PropertyOwnerDto> updateOwner(PropertyOwnerDto propertyOwnerDto, int id
    ) {
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
            existingOwner.setRole(propertyOwnerDto.getRole());
            entityManager.getTransaction().begin();
            entityManager.merge(existingOwner);
            entityManager.getTransaction().commit();
            return new RestApiResult<PropertyOwnerDto>(propertyOwnerDto, 0, "successful");
        } catch (Exception e) {
            return new RestApiResult<PropertyOwnerDto>(propertyOwnerDto, 0, "successful");

        }

    }

    @Override
    public RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, int id
    ) {
        try {
            Property existingProperty = propertyRepository.findById(id);
            existingProperty.setE9(propertyDto.getE9());
            existingProperty.setAddress(propertyDto.getAddress());
            existingProperty.setYearOfConstruction(propertyDto.getYearOfConstruction());
            existingProperty.setPropertyType(propertyDto.getPropertyType());
            logger.info("Updating the property: " + propertyDto.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(existingProperty);
            entityManager.getTransaction().commit();
            return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");
        } catch (Exception e) {
            return new RestApiResult<PropertyDto>(propertyDto, 0, "successful");

        }

    }

    @Override
    public RestApiResult<RepairDto> updateRepair(RepairDto repairDto, int id
    ) {
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
            logger.info("Updating the property: " + repairDto.toString());
            entityManager.getTransaction().begin();
            entityManager.merge(existingRepair);
            entityManager.getTransaction().commit();
            return new RestApiResult<RepairDto>(repairDto, 0, "successful");

        } catch (Exception e) {
            return new RestApiResult<RepairDto>(repairDto, 0, "successful");

        }

    }

    @Override
    public PropertyOwnerDto updateAddress(int id, String newAddress
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setAddress(newAddress);
            propertyOwnerRepository.create(propertyOwner);
            logger.info("Changing address of owner with id: " + id + "to :" + newAddress);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Not found");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updateEmail(int id, String email
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            if (propertyOwner == null) {
                return null;
            }

            List<PropertyOwner> results = propertyOwnerRepository.checkEmails(email);
            if (!results.isEmpty()) {
                throw new IllegalArgumentException();
            }
            propertyOwner.setEmail(email);
            propertyOwnerRepository.create(propertyOwner);
            logger.info("Changing e-mail of owner with id: " + id + " to :" + email);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Cannot update the email. Try to enter a different email");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updateName(int id, String name
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setName(name);
            propertyOwnerRepository.create(propertyOwner);
            logger.info("Changing name of owner with id: " + id + " to :" + name);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Not found");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updateSurname(int id, String surname
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setSurname(surname);
            propertyOwnerRepository.create(propertyOwner);
            logger.info("Changing surname of owner with id: " + id + " to :" + surname);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Something wrong while changing the surname. Pleasy try again");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updatePassword(int id, String password
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setPassword(password);
            propertyOwnerRepository.create(propertyOwner);
            logger.info("Changing password of owner with id: " + id + "to :" + password);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Something wrong while changing the password. Pleasy try again");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updatePhoneNumber(int id, String phoneNumber
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setPhoneNumber(phoneNumber);
            logger.info("Phone number of owner with id " + id + " will change to " + phoneNumber);
            propertyOwnerRepository.create(propertyOwner);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Something wrong while changing the phoneNumber. Pleasy try again");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updateUsername(int id, String username
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setUsername(username);
            logger.info("Username of owner with id " + id + " will change to " + username);
            propertyOwnerRepository.create(propertyOwner);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Something wrong while changing the username. Pleasy try again");
        }
        return null;
    }

    @Override
    public PropertyOwnerDto updateVat(int id, int vat
    ) {
        try {
            PropertyOwner propertyOwner = propertyOwnerRepository.read(id);
            propertyOwner.setVat(vat);
            logger.info("Vat of owner with id " + id + " will change to " + vat);
            propertyOwnerRepository.create(propertyOwner);
            return new PropertyOwnerDto(propertyOwner);
        } catch (Exception e) {
            logger.error("Something wrong while changing the vat. Pleasy try again");

        }
        return null;
    }

    @Override
    public PropertyDto updatePropertyAddress(int propertyId, String address
    ) {
        try {
            Property property = propertyRepository.read(propertyId);
            property.setAddress(address);
            logger.info("Address of property  with id " + propertyId + " will change to " + address);
            propertyRepository.create(property);
            return new PropertyDto(property);
        } catch (Exception e) {
            logger.error("Something wrong while changing the address. Pleasy try again");

        }
        return null;

    }

    @Override
    public PropertyDto updateE9(int propertyId, int e9
    ) {
        try {
            Property property = propertyRepository.read(propertyId);
            property.setE9(e9);
            logger.info("E9 of property with id " + propertyId + " will change to " + e9);
            propertyRepository.create(property);
            return new PropertyDto(property);
        } catch (Exception e) {
            logger.error("Something wrong while changing the address. Please try again");
        }
        return null;
    }

    @Override
    public PropertyDto updateYearOfConstruction(int propertyId, String yearOfConstruction
    ) {
        try {
            Property property = propertyRepository.read(propertyId);
            property.setYearOfConstruction(yearOfConstruction);
            logger.info("Year of construction  of property  with id " + propertyId + " will change to " + yearOfConstruction);
            propertyRepository.create(property);
            return new PropertyDto(property);
        } catch (Exception e) {
            logger.error("Something wrong while changing the yearOfConstruction. Pleasy try again");

        }
        return null;

    }

    @Override
    public PropertyDto updatePropertyType(int propertyId, PropertyType propertyType
    ) {
        try {
            Property property = propertyRepository.read(propertyId);
            property.setPropertyType(propertyType);
            logger.info("PropertyType of property  with id " + propertyId + " will change to " + propertyType);
            propertyRepository.create(property);
            return new PropertyDto(property);
        } catch (Exception e) {
            logger.error("Something wrong while changing the propertyType. Pleasy try again");

        }
        return null;

    }

    @Override
    public RepairDto updateRepairType(int id, RepairType repairType
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setRepairType(repairType);
            logger.info("RepairType of repair  with id " + id + " will change to " + repairType);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the repairType. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateRepairDescription(int id, String repairDescription
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setRepairDescription(repairDescription);
            logger.info("RepairDescription of repair  with id " + id + " will change to " + repairDescription);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the repairDescription. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateSubmissionDate(int id, String submissionDate
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setSubmissionDate(submissionDate);
            logger.info("SubmissionDate of repair  with id " + id + " will change to " + submissionDate);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the submissionDate. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateWorkDescription(int id, String workDescription
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setWorkDescription(workDescription);
            logger.info("Work description of repair  with id " + id + " will change to " + workDescription);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the workDescription. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateStartDate(int id, String startDate
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setStartDate(startDate);
            logger.info("Start date of repair  with id " + id + " will change to " + startDate);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the startDate. Pleasy try again");

        }
        return null;

    }

    @Override
    public RepairDto updateEndDate(int id, String endDate
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setEndDate(endDate);
            logger.info("End date of repair  with id " + id + " will change to " + endDate);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the endDate. Pleasy try again");

        }
        return null;

    }

    @Override
    public RepairDto updateCost(int id, double cost
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setCost(cost);
            repairRepository.create(repair);
            logger.info("Cost of repair  with id " + id + " will change to " + cost);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the cost. Pleasy try again");

        }
        return null;

    }

    @Override
    public RepairDto updateAcceptance(int id, boolean acceptance
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setAcceptance(acceptance);
            logger.info("Acceptance of repair  with id " + id + " will change to " + acceptance);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the acceptance. Pleasy try again");

        }
        return null;

    }

    @Override
    public RepairDto updateRepairStatus(int id, RepairStatus repairStatus
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setRepairStatus(repairStatus);
            logger.info("RepairStatus of repair  with id " + id + " will change to " + repairStatus);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the repairStatus. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateActualStartDate(int id, String actualStartDate
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setActualStartDate(actualStartDate);
            logger.info("ActualStartDate of repair  with id " + id + " will change to " + actualStartDate);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the actualStartDate. Pleasy try again");

        }
        return null;
    }

    @Override
    public RepairDto updateActualEndDate(int id, String actualEndDate
    ) {
        try {
            Repair repair = repairRepository.read(id);
            repair.setActualEndDate(actualEndDate);
            logger.info("ActualEndDate of repair  with id " + id + " will change to " + actualEndDate);
            repairRepository.create(repair);
            return new RepairDto(repair);
        } catch (Exception e) {
            logger.error("Something wrong while changing the actualEndDate. Pleasy try again");

        }
        return null;
    }

    @Override
    public PropertyDto getPropertyByE9(int e9) {
        logger.info("Returning property with e9: " + e9);
        return new PropertyDto(propertyRepository.findbyE9(e9));
    }

    @Override
    public boolean checkE9(int e9) {
        logger.info("Checking if property with e9 " + e9 + "if exists");
        return propertyRepository.checkE9IfExists(e9);
    }

    @Override
    public boolean checkVat(int vat) {
        logger.info("Returning owner with vat: " + vat);
        return propertyOwnerRepository.checkVatIfExists(vat);
    }

    @Transactional
    @Override
    public List<PropertyDto> getPropertiesByOwnerVat(int vat
    ) {
        PropertyOwner owner = propertyOwnerRepository.findByVat(vat);
        logger.info("Returning properties that belong to owner with Vat : " + vat);
        return owner.getProperties().stream().map(property -> new PropertyDto(property)).collect(Collectors.toList());
    }

    @Override
    public List<RepairDto> getRepairsBySubmissionDate(String submissionDate
    ) {
        logger.info("Returning all repairs with submission date: " + submissionDate);
        return repairRepository.findbyExactDate(submissionDate).stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @Override
    public List<RepairDto> getRepairsOfOwner(int id
    ) {
        logger.info("Returning all repairs of owner with id : " + id);
        return repairRepository.findRepairsOfOwner(id).stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @Override
    public PropertyOwnerDto getUser(String authorization
    ) {
        final String encodedUserPassword = authorization.replaceFirst("Basic" + " ", "");
        String usernameAndPassword = new String(Base64.getDecoder().decode(encodedUserPassword.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();
        logger.info("User: {} logged in at {}", username, LocalDateTime.now());
        return new PropertyOwnerDto(propertyOwnerRepository.findByUserameAndPass(username, password));
    }

}
