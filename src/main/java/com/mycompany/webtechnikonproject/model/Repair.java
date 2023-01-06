package com.mycompany.webtechnikonproject.model;

import com.mycompany.webtechnikonproject.enums.RepairStatus;
import com.mycompany.webtechnikonproject.enums.RepairType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity(name = "repair")
@Table
public class Repair extends PersistentClass {

  @ManyToOne
  @JoinColumn(name = "property_id")
  private Property property;
  @Enumerated(EnumType.STRING)
  private RepairType repairType;
  private String repairDescription;
  private String submissionDate;
  private String workDescription;
  private String startDate;
  private String endDate;
  private double cost;
  private boolean acceptance;
  @Enumerated(EnumType.STRING)
  private RepairStatus repairStatus;
  private String actualStartDate;
  private String actualEndDate;

  public Repair(String repairDescription, String submissionDate, String startDate, double cost, boolean acceptance, RepairStatus repairStatus) {
    this.repairDescription = repairDescription;
    this.submissionDate = submissionDate;
    this.startDate = startDate;
    this.cost = cost;
    this.acceptance = acceptance;
    this.repairStatus = repairStatus;

    
  }
  

}
