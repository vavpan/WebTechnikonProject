package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.RepairService;
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
import java.util.Scanner;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


@Stateless
public class RepairServiceImpl implements RepairService {

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

    @Override
    public void acceptRepair(Repair repair) {
        repairRepository.updateAcceptance(repair.getId(), true);
    }

    @Override
    public void declineRepair(Repair repair) {
        repairRepository.updateAcceptance(repair.getId(), false);
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
    @Transactional
    public List<RepairDto> getAllRepairs() {
        logger.info("Getting all repairs");
        return repairRepository.findAll().stream().map(RepairDto::new).collect(Collectors.toList());
    }

    @Override
    public boolean deleteRepair(int repairId
    ) {
        logger.info("Deleting repair with id: " + repairId);
        return repairRepository.deleteRepair(repairId);
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

}
