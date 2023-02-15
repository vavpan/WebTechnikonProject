package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.exceptions.ExceptionsCodes;
import com.mycompany.webtechnikonproject.exceptions.PropertyException;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.PropertyService;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class PropertyServiceImpl implements PropertyService {

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

    @PersistenceContext
    private EntityManager entityManager;

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
    @Transactional
    public List<PropertyDto> getAllProperties() {
        logger.info("Getting all properties");
        return propertyRepository.findAll().stream().map(PropertyDto::new).collect(Collectors.toList());
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
    public boolean deleteProperty(int id
    ) {
        logger.info("Deleting property with id: " + id);
        propertyRepository.deleteProperty(id);
        return true;
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
    @Transactional
    public List<PropertyDto> getPropertiesByOwnerVat(int vat
    ) {
        PropertyOwner owner = propertyOwnerRepository.findByVat(vat);
        logger.info("Returning properties that belong to owner with Vat : " + vat);
        return owner.getProperties().stream().map(property -> new PropertyDto(property)).collect(Collectors.toList());
    }

}
