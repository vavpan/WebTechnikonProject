package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.model.Repair;
import java.time.LocalDate;
import java.util.List;

public interface RepairRepository extends Repository<Repair> {

    boolean delete(int id);

    Repair search(int id);

    List<Repair> search(LocalDate submissionDate);

    void updatePropertyId(int repairId, int propertyId);

    void updateRepairType(int id, RepairType repairType);

    void updateRepairDescription(int id, String repairDescription);

    void updateSubmissionDate(int id, LocalDate submissionDate);

    void updateWorkDescription(int id, String workDescription);

    void updateStartDate(int id, LocalDate startDate);

    void updateEndDate(int id, LocalDate endDate);

    void updateCost(int id, double cost);

    void updateAcceptance(int id, boolean acceptance);

    void updateRepairStatus(int id, RepairStatus repairStatus);

    void updateActualStartDate(int id, LocalDate actualStartDate);

    void updateActualEndDate(int id, LocalDate actualEndDate);

    List<Repair> readAll();

    Repair findById(int id);

    boolean deleteRepair(int id);

}
