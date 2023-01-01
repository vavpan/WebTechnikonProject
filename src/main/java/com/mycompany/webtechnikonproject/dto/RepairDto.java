/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.dto;

import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import com.mycompany.webtechnikonproject.model.Repair;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Data
@NoArgsConstructor
public class RepairDto {


    private RepairType repairType;
    private String repairDescription;
    private LocalDate submissionDate;
    private String workDescription;
    private LocalDate startDate;
    private LocalDate endDate;
    private double cost;
    private boolean acceptance;
    private RepairStatus repairStatus;
    private LocalDate actualStartDate;
    private LocalDate actualEndDate;
    private PropertyDto property;

    public RepairDto(Repair repair) {
 
        this.repairType = repair.getRepairType();
        this.repairDescription = repair.getRepairDescription();
        this.submissionDate = repair.getSubmissionDate();
        this.workDescription = repair.getWorkDescription();
        this.startDate = repair.getStartDate();
        this.endDate = repair.getEndDate();
        this.cost = repair.getCost();
        this.repairStatus = repair.getRepairStatus();
        this.actualStartDate = repair.getActualStartDate();
        this.actualEndDate = repair.getActualEndDate();
    }

    public Repair asRepair() {
        Repair repair = new Repair();
        repair.setRepairType(repairType);
        repair.setRepairDescription(repairDescription);
        repair.setSubmissionDate(submissionDate);
        repair.setWorkDescription(workDescription);
        repair.setStartDate(startDate);
        repair.setEndDate(endDate);
        repair.setCost(cost);
        repair.setRepairStatus(repairStatus);
        repair.setActualStartDate(actualStartDate);
        repair.setEndDate(endDate);
        return repair;
    }

}
