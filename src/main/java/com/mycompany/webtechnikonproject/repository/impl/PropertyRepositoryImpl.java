package com.mycompany.webtechnikonproject.repository.impl;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.util.JpaUtil;
import jakarta.inject.Inject;
import jakarta.json.Json;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.NoResultException;
import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.Properties;

public class PropertyRepositoryImpl extends RepositoryImpl<Property> implements PropertyRepository {

    private static final Logger logger = LogManager.getLogger(PropertyRepositoryImpl.class);
    private final Properties sqlCommands = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream config = loader.getResourceAsStream("sql.properties")) {
            sqlCommands.load(config);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String getClassName() {
        return Property.class.getName();
    }

    @Override
    public Property search(int id) {
        return JpaUtil.getEntityManager().find(Property.class, id);
    }

    @Override
    public List<Property> searchByVat(int vat) {
        List<PropertyOwner> propertyOwners = entityManager.createQuery(sqlCommands.getProperty("select.owner.byVat"), PropertyOwner.class)
                .setParameter("vat", vat).getResultList();
        return propertyOwners.get(0).getProperties();
    }

    @Override
    public void updateOwnerVat(int propertyId, int vat) {
        Property property = entityManager.find(Property.class, propertyId);
        try {
            PropertyOwner propertyOwner = property.getOwner();
            propertyOwner.setVat(vat);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(propertyOwner);
            transaction.commit();
        } catch (Exception ex) {
            logger.warn("OwnerVat can't be updated", ex);
        }
    }

    @Override
    public void updateAddress(int id, String address) {
        Property property = entityManager.find(Property.class, id);
        try {
            property.setAddress(address);
            entityManager.getTransaction().begin();
            entityManager.persist(property);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            logger.warn("Can't updated", ex);
        }
    }

    @Override
    public void updateYearOfConstruction(int id, String year) {
        Property property = entityManager.find(Property.class, id);
        try {
            property.setYearOfConstruction(year);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(property);
            transaction.commit();
            logger.info("The year of construction of the property with E9 {} has been updated", property.getYearOfConstruction());
        } catch (Exception e) {
            logger.warn("Can't be upadated", e);
        }
    }

    @Override
    public void updatePropertyType(int id, PropertyType propertyType) {
        Property property = entityManager.find(Property.class, id);
        try {
            property.setPropertyType(propertyType);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(property);
            transaction.commit();
            logger.info("The property type of the property with E9 {} has been updated", property.getYearOfConstruction());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public boolean delete(int id) {
        List<Repair> repairList = entityManager.createQuery("select r from repair r where r.property.id=:propertyId", Repair.class)
                .setParameter("propertyId", id).getResultList();
        try {
            for (Repair repair : repairList) {
                entityManager.getTransaction().begin();
                entityManager.find(Property.class, repair.getId());
                entityManager.remove(repair);
                entityManager.getTransaction().commit();
            }
            Property property = entityManager.find(Property.class, id);
            entityManager.getTransaction().begin();
            entityManager.remove(property);
            entityManager.getTransaction().commit();
            logger.info("Deletion completed successfully");
        } catch (Exception e) {
            logger.warn("Can't be deleted", e);
        }
        return true;
    }

    @Override
    public void updateOwnerId(int propertyId, int propertyOwnerId) {
        Property property = entityManager.find(Property.class, propertyId);
        PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, propertyOwnerId);
        try {
            property.setOwner(propertyOwner);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(property);
            transaction.commit();
            logger.info("Owner's id has been updated");
        } catch (Exception e) {
            logger.warn("Can't be upadated", e);
        }
    }

    @Override
    public List<Property> readAll() {
        List<Property> results = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.properties"))
                .getResultList();
        return results;
    }

    @Override
    public boolean deleteProperty(int id) {
        Property property = entityManager.find(Property.class, id);
        if (property == null) {
            return false;
        }
        entityManager.remove(property);
        return true;
    }

    @Override
    @Transactional
    public Property findById(int id) {
        Property property = entityManager.find(Property.class, id);
        return property;

    }

    @Override
    public Class<Property> getClassType() {
        return Property.class;
    }

    @Override
    public void copyValues(Property tSource, Property tTarget) {
        tTarget.setAddress(tSource.getAddress());
        tTarget.setOwner(tSource.getOwner());
        tTarget.setId(tSource.getId());
        tTarget.setPropertyType(tSource.getPropertyType());
        tTarget.setYearOfConstruction(tSource.getYearOfConstruction());
    }

    @Override
    public List<Property> findAll() {
        return entityManager.createQuery("select p from property p").getResultList();
    }

    @Override
    public Property findbyE9(int e9) {
        try {
            return entityManager.createQuery("SELECT p from property p where p.e9 =:e9", Property.class)
                    .setParameter("e9", e9).getSingleResult();
        } catch (NoResultException ex) {
            throw new NotFoundException("Property with e9 " + e9 + " not found");
        } catch (NonUniqueResultException ex) {
            throw new InternalServerErrorException("Multiple properties found with e9 " + e9);
        }
    }

    @Override
    public boolean checkE9IfExists(int e9) {
        try {
            entityManager.createQuery("SELECT p from property p where p.e9 =:e9", Property.class)
                    .setParameter("e9", e9).getSingleResult();
            return true;
        } catch (NoResultException ex) {
            return false;
        } catch (NonUniqueResultException ex) {
            throw new InternalServerErrorException("Multiple properties found with e9 " + e9);
        }
    }

    @Override
    public List<Property> findE9s(int e9) {
        return entityManager.createQuery("SELECT p FROM property p WHERE p.e9 = :e9", Property.class)
                .setParameter("e9", e9)
                .getResultList();
    }

}
