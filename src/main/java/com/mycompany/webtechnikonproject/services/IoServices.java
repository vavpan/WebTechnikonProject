package com.mycompany.webtechnikonproject.services;

import com.mycompany.webtechnikonproject.model.Property;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.model.Repair;
import java.util.List;


public interface IoServices {

  /**
   * This method saves the property owners table in to a csv file
   *
   * @param filename
   */
  void savePropertyOwnerToCsv(String filename);

  /**
   * This method saves the property table in to a csv file
   *
   * @param filename
   */
  void savePropertyToCsv(String filename);

  /**
   * This method saves the repairs table in to a csv file
   *
   * @param filename
   */
  void saveRepair(String filename);

  /**
   * This method reads a csv file and saves it's content in a list
   *
   * @param fileName
   * @return
   */
  List<String[]> readCsvFile(String fileName);

  /**
   * This method takes a list of csv input and returns a list of type
   * PropertyOwner
   *
   * @param fileName
   * @return
   */
  List<PropertyOwner> loadOwnerData(String fileName);

  /**
   * This method takes a list of csv input and returns a list of type Property
   *
   * @param fileName
   * @return
   */
  List<Property> loadPropertyData(String fileName);

  /**
   * This method takes a list of csv input and returns a list of type Repair
   *
   * @param fileName
   * @return
   */
  List<Repair> loadRepairData(String fileName);

  
  public void readOwnersCsv(String fileName);
 
  public void readPropertyCsv(String fileName);
  
  public void readRepairCsv(String fileName);
  
  public void relationshipsBetweenObjects();
}