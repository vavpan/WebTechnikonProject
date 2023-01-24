package com.mycompany.webtechnikonproject.repository;

import com.mycompany.webtechnikonproject.dto.PropertyOwnerDto;
import com.mycompany.webtechnikonproject.model.PropertyOwner;
import java.util.List;

public interface PropertyOwnerRepository extends Repository<PropertyOwner> {

    PropertyOwner search(int id);

    PropertyOwner search(String email);

    List<PropertyOwner> findAll();

    void updateAddress(int id, String address);

    void updateEmail(int id, String email);

    void updatePassword(int id, String password);

    boolean delete(int id);

    List<PropertyOwner> read(String ownerName);

    List<PropertyOwner> readAll();

    void createPropertyOwner(PropertyOwner propertyOwner);

    PropertyOwner findById(int id);

    PropertyOwner findByVat(int vat);

    PropertyOwner findByEmail(String email);

    // REST API METHODS
    boolean deleteOwner(int id);

    List<PropertyOwner> findEmails(String email);

    List<PropertyOwner> findVats(int vat);

    List<PropertyOwner> findUsernames(String username);

    String checkRole(String username, String password);

    PropertyOwner findByUserameAndPass(String username, String password);

}
