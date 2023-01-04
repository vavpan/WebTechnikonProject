package com.mycompany.webtechnikonproject.services.impl;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
import com.mycompany.webtechnikonproject.repository.PropertyRepository;
import com.mycompany.webtechnikonproject.repository.RepairRepository;
import com.mycompany.webtechnikonproject.services.IoServices;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Stateless
public class IoServiceImpl implements IoServices {

    private static final Logger logger = LogManager.getLogger(IoServiceImpl.class);

    @Inject
    private PropertyOwnerRepository propertyOwnerRepository;

    @Inject
    private PropertyRepository propertyRepository;

    @Inject
    private RepairRepository repairRepository;

    @Override
    public void savePropertyOwnerToCsv(String filename) {
        File file = new File(filename);
        List<PropertyOwner> propertyOwners = propertyOwnerRepository.readAll();
        try ( PrintWriter pw = new PrintWriter(file)) {
            pw.println("id,vat,name,surname,address,phoneNumber,email,username,password,properties");
            for (PropertyOwner propertyOwner : propertyOwners) {
                pw.println(propertyOwner.getId()
                        + "," + propertyOwner.getVat()
                        + "," + propertyOwner.getName()
                        + "," + propertyOwner.getSurname()
                        + "," + propertyOwner.getAddress()
                        + "," + propertyOwner.getPhoneNumber()
                        + "," + propertyOwner.getEmail()
                        + "," + propertyOwner.getUsername()
                        + "," + propertyOwner.getPassword()
                        + "," + propertyOwner.getProperties());
            }
            logger.info("Property owners saved to csv file ");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void savePropertyToCsv(String filename) {
        File file = new File(filename);
        List<Property> properties = propertyRepository.readAll(); // We will create read() method later
        try ( PrintWriter pw = new PrintWriter(file)) {
            pw.println("id,e9,address,yearOfConstruction,propertyType,owner,repairs");
            for (Property property : properties) {
                pw.println(
                        property.getId()
                        + "," + property.getE9()
                        + "," + property.getAddress()
                        + "," + property.getYearOfConstruction()
                        + "," + property.getPropertyType()
                        + "," + property.getOwner()
                        + "," + property.getRepairs());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        logger.info("Properties saved to csv file ");
    }

    @Override
    public void saveRepair(String filename) {
        File file = new File(filename);
        List<Repair> repairs = repairRepository.readAll(); // We will create read() method later
        try ( PrintWriter pw = new PrintWriter(file)) {
            pw.println("id,owner,property,repairType,repairDescription,submissionDate,workDescription,startDate,endDate,cost,acceptance,repairStatus,actualStartDate,actualEndDate");
            for (Repair repair : repairs) {
                pw.println(
                        repair.getId()
                        + "," + repair.getProperty()
                        + "," + repair.getRepairType()
                        + "," + repair.getRepairDescription()
                        + "," + repair.getSubmissionDate()
                        + "," + repair.getWorkDescription()
                        + "," + repair.getStartDate()
                        + "," + repair.getEndDate()
                        + "," + repair.getCost()
                        + "," + repair.isAcceptance()
                        + "," + repair.getRepairStatus()
                        + "," + repair.getActualStartDate()
                        + "," + repair.getActualEndDate()
                );
            }
            logger.info("Repairs saved to csv file ");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<String[]> readCsvFile(String fileName) {
        List<String[]> list = new ArrayList<>();
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(fileName));
            String str;
            while ((str = br.readLine()) != null) {
                list.add(str.split(","));
            }
            logger.info("The csv file {} has been read ", fileName);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return list;
    }

    @Override
    public List<PropertyOwner> loadOwnerData(String fileName) {
        List<String[]> list = readCsvFile(fileName);
        int vat;
        String name;
        String surname;
        String address;
        String phoneNumber;
        String email;
        String username;
        String password;
        List<PropertyOwner> propertyOwners = new ArrayList<>();
        for (String[] temp : list) {
            vat = Integer.parseInt(temp[0]);
            name = temp[1];
            surname = temp[2];
            address = temp[3];
            phoneNumber = temp[4];
            email = temp[5];
            username = temp[6];
            password = temp[7];
            propertyOwners.add(new PropertyOwner(vat, name, surname, address, phoneNumber, email, username, password));
            logger.info("The owner with VAT number {} has been loaded", vat);
        }
        return propertyOwners;
    }

    @Override
    public List<Property> loadPropertyData(String fileName) {

        List<String[]> list = readCsvFile(fileName);
        int e9;
        String address;
        String yearOfConstruction;
        PropertyType propertyType;
        List<Property> properties = new ArrayList<>();
        for (String[] temp : list) {
            e9 = Integer.parseInt(temp[0]);
            address = temp[1];
            yearOfConstruction = temp[2];
            propertyType = PropertyType.valueOf(temp[3]);
            properties.add(new Property(e9, address, yearOfConstruction, propertyType));
            logger.info("The property with E9 number {} has been loaded", e9);
        }

        return properties;
    }

    @Override
    public List<Repair> loadRepairData(String fileName) {
        List<String[]> list = readCsvFile(fileName);
        String repairDescription;
        LocalDate submissionDate;
        LocalDate startDate;
        double cost;
        boolean acceptance;
        RepairStatus repairStatus;
        List<Repair> repairs = new ArrayList<>();
        for (String[] temp : list) {
            repairDescription = temp[0];
            submissionDate = LocalDate.parse(temp[1]);
            startDate = LocalDate.parse(temp[2]);
            cost = Double.parseDouble(temp[3]);
            acceptance = Boolean.parseBoolean(temp[4]);
            repairStatus = RepairStatus.valueOf(temp[5]);
            repairs.add(new Repair(repairDescription, submissionDate, startDate, cost, acceptance, repairStatus));
            logger.info("The repair with has been loaded");
        }
        return repairs;
    }

    @Override
    public void readOwnersCsv(String fileName) {
        List<PropertyOwner> propertyOwners = loadOwnerData(fileName);
        for (PropertyOwner propertyOwner : propertyOwners) {
            propertyOwnerRepository.create(propertyOwner);
        }

    }

    @Override
    public void readPropertyCsv(String fileName) {
        List<Property> properties = loadPropertyData(fileName);
        for (Property property : properties) {
            propertyRepository.create(property);
        }
        
        
    }

    @Override
    public void readRepairCsv(String fileName) {
        List<Repair> repairs = loadRepairData(fileName);
        for (Repair repair : repairs) {
            repairRepository.create(repair);
        }
    }

    @Override
    public void relationshipsBetweenObjects() {
        propertyRepository.updateOwnerId(1, 1);
        propertyRepository.updateOwnerId(2, 2);
        propertyRepository.updateOwnerId(3, 2);
        propertyRepository.updateOwnerId(4, 3);
        propertyRepository.updateOwnerId(5, 4);
        propertyRepository.updateOwnerId(6, 5);
        propertyRepository.updateOwnerId(7, 6);
        propertyRepository.updateOwnerId(8, 7);
        propertyRepository.updateOwnerId(9, 8);
        propertyRepository.updateOwnerId(10, 7);
        propertyRepository.updateOwnerId(11, 9);
        propertyRepository.updateOwnerId(12, 10);
        propertyRepository.updateOwnerId(13, 11);
        propertyRepository.updateOwnerId(14, 12);
        propertyRepository.updateOwnerId(15, 13);
        propertyRepository.updateOwnerId(16, 14);
        propertyRepository.updateOwnerId(17, 15);
        propertyRepository.updateOwnerId(18, 16);
        propertyRepository.updateOwnerId(19, 17);
        propertyRepository.updateOwnerId(20, 18);
        repairRepository.updatePropertyId(1, 2);
        repairRepository.updatePropertyId(2, 1);
        repairRepository.updatePropertyId(3, 2);
        repairRepository.updatePropertyId(4, 5);
        repairRepository.updatePropertyId(5, 7);
        repairRepository.updatePropertyId(6, 8);
        repairRepository.updatePropertyId(7, 8);
        repairRepository.updatePropertyId(8, 10);
        repairRepository.updatePropertyId(9, 12);
        repairRepository.updatePropertyId(10, 20);
        repairRepository.updatePropertyId(11, 19);
        repairRepository.updatePropertyId(12, 15);
        repairRepository.updatePropertyId(13, 13);
        repairRepository.updatePropertyId(14, 13);
        repairRepository.updatePropertyId(15, 4);
        repairRepository.updatePropertyId(16, 3);
        repairRepository.updatePropertyId(17, 6);
        repairRepository.updatePropertyId(18, 11);
        repairRepository.updatePropertyId(19, 16);
        repairRepository.updatePropertyId(20, 17);
        // Updates for PropertyOwners
        propertyOwnerRepository.updateAddress(1, "Moran");
        propertyOwnerRepository.updateEmail(1, "Lasy");
        propertyOwnerRepository.updatePassword(1, "lasy123");
//         Updates for Properties
        propertyRepository.updateAddress(5, "Ledamonos 65");
        propertyRepository.updateOwnerVat(3, 426781512);
        propertyRepository.updateYearOfConstruction(4, "1980");
        propertyRepository.updatePropertyType(9, PropertyType.MAISONETTE);
        // Updates for Repairs
        repairRepository.updateAcceptance(1, false);
        repairRepository.updateCost(3, 450);
        repairRepository.updateStartDate(10, LocalDate.parse("2020-05-18"));
        repairRepository.updateEndDate(7, LocalDate.parse("2021-01-11"));
        repairRepository.updateActualEndDate(20, LocalDate.parse("2010-02-06"));
        repairRepository.updateActualStartDate(17, LocalDate.parse("2018-04-02"));
//         Update work description for Repairs
        repairRepository.updateWorkDescription(1, "Under Construction");
        repairRepository.updateWorkDescription(2, "Repaired successfully");
        repairRepository.updateWorkDescription(3, "Under Construction");
        repairRepository.updateWorkDescription(4, "Recently started");
        repairRepository.updateWorkDescription(5, "Under control");
        repairRepository.updateWorkDescription(6, "Repaired successfully");
        repairRepository.updateWorkDescription(7, "Repaired successfully");
        repairRepository.updateWorkDescription(8, "Repair of building completed");
        repairRepository.updateWorkDescription(9, "Recently started");
        repairRepository.updateWorkDescription(10, "50% of the work done");
        repairRepository.updateWorkDescription(11, "Under construction");
        repairRepository.updateWorkDescription(12, "Under construction");
        repairRepository.updateWorkDescription(13, "Repaired successfully");
        repairRepository.updateWorkDescription(14, "80% of the work done");
        repairRepository.updateWorkDescription(15, "Under construction");
        repairRepository.updateWorkDescription(16, "Recently started");
        repairRepository.updateWorkDescription(17, "Under construction");
        repairRepository.updateWorkDescription(18, "Under construction");
        repairRepository.updateWorkDescription(19, "Work recently started");
        repairRepository.updateWorkDescription(20, "Fixed");
    }

}
