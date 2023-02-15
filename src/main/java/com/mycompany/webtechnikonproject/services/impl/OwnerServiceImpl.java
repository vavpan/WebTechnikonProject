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
    public boolean deletePropertyOwner(int ownerId
    ) {
        logger.info("Deleting PropertyOwner with id: " + ownerId);
        return propertyOwnerRepository.deleteOwner(ownerId);
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
    public boolean checkVat(int vat) {
        logger.info("Returning owner with vat: " + vat);
        return propertyOwnerRepository.checkVatIfExists(vat);
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
