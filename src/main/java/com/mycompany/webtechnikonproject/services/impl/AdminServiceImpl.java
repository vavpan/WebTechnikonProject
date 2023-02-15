package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.AdminService;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;

public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LogManager.getLogger(AdminServiceImpl.class);

    @Inject
    private PropertyOwnerRepository propertyOwnerRepository;

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private RepairRepository repairRepository;

    @PersistenceContext
    private EntityManager entityManager;

    private final Properties sqlCommands = new Properties();

    {
        final ClassLoader loader = getClass().getClassLoader();
        try (InputStream config = loader.getResourceAsStream("sql.properties")) {
            sqlCommands.load(config);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    @Transactional
    public List<Repair> getPendingRepairs() {
        String jql = "SELECT r FROM repair r WHERE r.repairStatus = 'PENDING'";
        Query query = entityManager.createQuery(jql);
        return query.getResultList();
    }

    @Override
    public void proposeCosts(int id, double cost) {
        try {
            repairRepository.updateCost(id, cost);
            logger.info("The cost of the repair with id {} has been proposed", id);
        } catch (Exception e) {
            logger.warn("Can not propose the costs");
        }
    }

    @Override
    public void proposeDates(Repair repair, String startDate, String endDate) {

        try {
            repairRepository.updateStartDate(repair.getId(), startDate);
            repairRepository.updateEndDate(repair.getId(), endDate);
            logger.info("The dates of the repair with id {} have been proposed", repair.getId());
        } catch (Exception e) {
            logger.warn("Can not propose the dates");
        }
    }

    @Override
    @Transactional
    public List<Repair> displayActualDatesOfPendingRepairs() {
        String jql = "SELECT r.id,r.actualStartDate,r.actualEndDate FROM repair r WHERE r.repairStatus = 'PENDING'";
        Query query = entityManager.createQuery(jql);
        return query.getResultList();
    }

    @Override
    @Transactional
    public List<Repair> getOnGoing() {
        String jql = "SELECT r FROM repair r WHERE r.repairStatus = 'IN_PROGRESS'";
        Query query = entityManager.createQuery(jql);
        return query.getResultList();
    }

}
