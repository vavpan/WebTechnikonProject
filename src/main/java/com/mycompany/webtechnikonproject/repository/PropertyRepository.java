package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.model.Property;
import java.util.List;

public interface PropertyRepository extends Repository<Property, Number> {

    Property search(int id);

    List<Property> searchByVat(int vat);

    List<Property> findAll();

    Property findbyE9(int e9);

    boolean checkE9IfExists(int e9);

    List<Property> findE9s(int e9);

    void updateOwnerVat(int propertyId, int vat);

    void updateAddress(int id, String address);

    void updateYearOfConstruction(int id, String year);

    void updatePropertyType(int id, PropertyType propertyType);

    boolean delete(int id);

    void updateOwnerId(int propertyId, int propertyOwnerId);

    List<Property> readAll();

    boolean deleteProperty(int id);

    Property findById(int id);
}
