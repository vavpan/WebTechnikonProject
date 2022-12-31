package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import java.util.List;

public interface PropertyRepository extends Repository<Property> {

  Property search(int id);

  List<Property> searchByVat(int vat);

  void updateOwnerVat(int propertyId, int vat);

  void updateAddress(int id, String address);

  void updateYearOfConstruction(int id, String year);

  void updatePropertyType(int id, PropertyType propertyType);

  boolean delete(int id);

  public void updateOwnerId(int propertyId, int propertyOwnerId);

  List<Property> readAll();
}