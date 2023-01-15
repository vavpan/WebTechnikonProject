package com.mycompany.webtechnikonproject.repository.impl;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.util.JpaUtil;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RepairRepositoryImpl extends RepositoryImpl<Repair> implements RepairRepository {

    private final Properties sqlCommands = new Properties();
    private static final Logger logger = LogManager.getLogger(RepairRepositoryImpl.class);

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
        return Repair.class.getName();
    }

    @Override
    public Repair search(int id) {
        return JpaUtil.getEntityManager().find(Repair.class, id);
    }

    @Override
    public List<Repair> search(String submissionDate) {
        return entityManager.createQuery("select x from repair x where x.submissionDate =: submissionDate ", Repair.class)
                .setParameter("submissionDate", submissionDate).getResultList();
    }

    @Override
    public void updatePropertyId(int repairId, int propertyId) {
        Repair repair = entityManager.find(Repair.class, repairId);
        Property property = entityManager.find(Property.class, propertyId);
        try {
            repair.setProperty(property);
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.merge(repair);
            transaction.commit();
            logger.info("The Repair's property id has been updated");
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateRepairType(int id, RepairType repairType) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setRepairType(repairType);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            logger.info("The Repair Type with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateRepairDescription(int id, String repairDescription) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setRepairDescription(repairDescription);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            logger.info("The Repair's description with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateSubmissionDate(int id, String submissionDate) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setSubmissionDate(submissionDate);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            logger.info("The Repair's submissin date with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateWorkDescription(int id, String workDescription) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setWorkDescription(workDescription);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's work description with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateStartDate(int id, String startDate) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setStartDate(startDate);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's start date with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateEndDate(int id, String endDate) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setEndDate(endDate);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's end date with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateCost(int id, double cost) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setCost(cost);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's cost with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateAcceptance(int id, boolean acceptance) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setAcceptance(acceptance);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's acceptance with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateRepairStatus(int id, RepairStatus repairStatus) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setRepairStatus(repairStatus);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            logger.info("The Repair's status with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateActualStartDate(int id, String actualStartDate) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setActualStartDate(actualStartDate);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(repair);
            entityManager.getTransaction().commit();
            logger.info("The Repair's actual start date with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public void updateActualEndDate(int id, String actualEndDate) {
        Repair repair = entityManager.find(Repair.class, id);
        repair.setActualEndDate(actualEndDate);
        try {
            EntityTransaction transaction = entityManager.getTransaction();
            transaction.begin();
            entityManager.persist(repair);
            transaction.commit();
            logger.info("The Repair's actual end date with id {} has been updated", repair.getId());
        } catch (Exception ex) {
            logger.warn(" Can't be updated", ex);
        }
    }

    @Override
    public boolean delete(int id) {
        Repair repair = entityManager.find(Repair.class, id);
        if (repair == null) {
            return false;
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.remove(repair);
            entityManager.getTransaction().commit();
        } catch (Exception ex) {
            logger.warn("Can't be deleted", ex);
        }
        return true;
    }

    @Override
    public List<Repair> readAll() {
        List<Repair> results = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.repairs"))
                .getResultList();
        return results;
    }

    @Override
    public Class<Repair> getClassType() {
        return Repair.class;
    }

    @Override
    @Transactional
    public Repair findById(int id) {

        Repair repair = entityManager.find(Repair.class, id);
        return repair;

    }

    @Override
    public void copyValues(Repair tSource, Repair tTarget) {
        tTarget.setCost(tSource.getCost());
        tTarget.setWorkDescription(tSource.getWorkDescription());
        tTarget.setProperty(tSource.getProperty());
        tTarget.setActualEndDate(tSource.getActualStartDate());
        tTarget.setRepairDescription(tSource.getRepairDescription());
        tTarget.setRepairStatus(tSource.getRepairStatus());
        tTarget.setRepairType(tSource.getRepairType());
    }

    @Override
    public boolean deleteRepair(int id) {

        Repair repair = entityManager.find(Repair.class, id);
        if (repair == null) {
            return false;
        }
        entityManager.remove(repair);
        return true;
    }

    @Override
    public List<Repair> findAll() {
        return entityManager.createQuery("select r from repair r").getResultList();
    }

    @Override
    public List<Repair> findbyExactDate(String date) {
        return entityManager.createQuery("SELECT r FROM repair r WHERE r.submissionDate = :submissionDate", Repair.class)
                .setParameter("submissionDate", date).getResultList();
    }

    @Override
    @Transactional
    public List<Repair> findRepairsOfOwner(int id) {
        PropertyOwner propertyOwner = entityManager.find(PropertyOwner.class, id);
        List<Property> properties = propertyOwner.getProperties();
        List<Repair> repairs = new ArrayList<>();
        for (Property property : properties) {
            repairs.addAll(property.getRepairs());
        }
        return repairs;
    }

}
