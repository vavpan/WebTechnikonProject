package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.dto.RepairDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.Repair;
import java.util.List;

public interface RepairService {

    Repair getRepairFromConsole(Property property);

    void registerRepair(Property property);

    void acceptRepair(Repair repair);

    /**
     * This method changes acceptance status of a repair to false
     *
     * @param repair
     */
    void declineRepair(Repair repair);

    void createRepair(RepairDto repair);

    List<RepairDto> getAllRepairs();

    RestApiResult<RepairDto> getRepair(int repairId);

    boolean deleteRepair(int repairId);

    RestApiResult<RepairDto> updateRepair(RepairDto repairDto, int id);

    List<RepairDto> getRepairsBySubmissionDate(String submissionDate);

    List<RepairDto> getRepairsOfOwner(int id);

    RepairDto updateRepairType(int id, RepairType repairType);

    RepairDto updateRepairDescription(int id, String repairDescription);

    RepairDto updateSubmissionDate(int id, String submissionDate);

    RepairDto updateWorkDescription(int id, String workDescription);

    RepairDto updateStartDate(int id, String startDate);

    RepairDto updateEndDate(int id, String endDate);

    RepairDto updateCost(int id, double cost);

    RepairDto updateAcceptance(int id, boolean acceptance);

    RepairDto updateRepairStatus(int id, RepairStatus repairStatus);

    RepairDto updateActualStartDate(int id, String actualStartDate);

    RepairDto updateActualEndDate(int id, String actualEndDate);

}
