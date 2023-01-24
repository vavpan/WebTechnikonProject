//package com.mycompany.webtechnikonproject.repository.impl;
//
//import com.mycompany.webtechnikonproject.model.PropertyOwner;
//import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
//import jakarta.persistence.EntityManager;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.when;
//import static org.mockito.Mockito.mock;
//
//class PropertyOwnerRepositoryImplTest {
//
//    private PropertyOwnerRepository propertyOwnerRepository;
//    private EntityManager entityManager;
//
//    @BeforeEach
//    void setUp() {
//        entityManager = mock(EntityManager.class);
//        propertyOwnerRepository = new PropertyOwnerRepositoryImpl();
//    }
//   
//    @Test
//    void searchWhenIdIsInvalidThenReturnNull() {
//        int id = -1;
//        PropertyOwner propertyOwner = propertyOwnerRepository.search(id);
//        assertNull(propertyOwner);
//    }
//
//    @Test
//    void updateAddressWhenIdIsValid() {
//        PropertyOwner propertyOwner
//                = new PropertyOwner(
//                        123456789,
//                        "John",
//                        "Smith",
//                        "Athens",
//                        "6912345678",
//                        "johnsmith@gmail.com",
//                        "johnsmith",
//                        "12345",
//                        "ADMIN");
//        propertyOwner.setId(1);
//        when(entityManager.find(PropertyOwner.class, 1)).thenReturn(propertyOwner);
//        propertyOwnerRepository.updateAddress(1, "Thessaloniki");
//        assertEquals("Thessaloniki", propertyOwner.getAddress());
//    }
//
//    @Test
//    void updatePasswordWhenIdIsValid() {
//        PropertyOwner propertyOwner
//                = new PropertyOwner(
//                        123456789,
//                        "John",
//                        "Doe",
//                        "Athens",
//                        "6912345678",
//                        "john@doe.com",
//                        "johndoe",
//                        "12345",
//                        "ADMIN");
//        propertyOwner.setId(1);
//        when(entityManager.find(PropertyOwner.class, 1)).thenReturn(propertyOwner);
//        propertyOwnerRepository.updatePassword(1, "54321");
//        assertEquals("54321", propertyOwner.getPassword());
//    }
//
//}
