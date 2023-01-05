package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.AdminService;
import com.mycompany.webtechnikonproject.util.JpaUtil;
import jakarta.enterprise.inject.Default;
import jakarta.persistence.Tuple;
import jakarta.persistence.TypedQuery;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

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
        try ( InputStream config = loader.getResourceAsStream("sql.properties")) {
            sqlCommands.load(config);
        } catch (IOException e) {
            throw new IOError(e);
        }
    }

    @Override
    public void displayPendingRepairs() {
        TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.pendingRepairs"), Tuple.class);
        List<Tuple> resultList = query.getResultList();
        resultList.forEach(tuple -> System.out.println("Property id: " + tuple.get(1)
                + "| Repair id: " + tuple.get(0)
                + "| Type of repair: " + tuple.get(9)
                + "| Repair description: " + tuple.get(7)
                + "| Submission date: " + tuple.get(11)
                + "| Work to be done: " + tuple.get(12)
                + "| Proposed start date: " + tuple.get(10)
                + "| Proposed end date: " + tuple.get(6)
                + "| Proposed cost: " + tuple.get(5)
                + "| Repair accepted: " + tuple.get(2)
                + "| Repair status: " + tuple.get(8)
                + "| Actual start date: " + tuple.get(4)
                + "| Actual end date: " + tuple.get(3)));
    }



    @Override
    public void proposeCosts(Repair repair, double cost) {
        try {
            repairRepository.updateCost(repair.getId(), cost);
            logger.info("The cost of the repair with id {} has been proposed", repair.getId());
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
    public void displayActualDatesOfPendingRepairs() {
        TypedQuery<Tuple> query = JpaUtil.getEntityManager().createQuery(sqlCommands.getProperty("select.start.end.dates"), Tuple.class);
        List<Tuple> resultList = query.getResultList();
        resultList.forEach(tuple -> System.out.println(
                "| Repair id: " + tuple.get(0)
                + "| Actual start date: " + tuple.get(1)
                + "| Actual end date: " + tuple.get(2)));
    }

}
