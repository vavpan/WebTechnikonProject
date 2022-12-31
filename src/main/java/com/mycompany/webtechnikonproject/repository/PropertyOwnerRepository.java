package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.model.PropertyOwner;
import java.util.List;

public interface PropertyOwnerRepository extends Repository<PropertyOwner> {

  PropertyOwner search(int id);

  PropertyOwner search(String email);

  void updateAddress(int id, String address);

  void updateEmail(int id, String email);

  void updatePassword(int id, String password);

  boolean delete(int id);

  List<PropertyOwner> read(String ownerName);

  List<PropertyOwner> readAll();
  
  void createPropertyOwner(PropertyOwner propertyOwner);
  
  
  PropertyOwner findById(int id);
  
  

}