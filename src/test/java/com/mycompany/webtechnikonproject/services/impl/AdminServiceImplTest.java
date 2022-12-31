//package com.mycompany.webtechnikonproject.services.impl;
//
//
//import com.mycompany.webtechnikonproject.model.Repair;
//import com.mycompany.webtechnikonproject.repository.PropertyOwnerRepository;
//import com.mycompany.webtechnikonproject.repository.PropertyRepository;
//import com.mycompany.webtechnikonproject.repository.RepairRepository;
//import com.mycompany.webtechnikonproject.services.AdminService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.times;
//
//class AdminServiceImplTest {
//
//
//    private AdminService adminService;
//    private PropertyOwnerRepository propertyOwnerRepository;
//    private PropertyRepository propertyRepository;
//    private RepairRepository repairRepository;
//
////    @BeforeEach
////    void setUp() {
////        propertyOwnerRepository = mock(PropertyOwnerRepository.class);
////        propertyRepository = mock(PropertyRepository.class);
////        repairRepository = mock(RepairRepository.class);
////        adminService =
////                new AdminServiceImpl(propertyOwnerRepository, propertyRepository, repairRepository);
////    }
//
//
//
//    @Test
//    void proposeCostsShouldProposeTheCostOfARepair() {
//        Repair repair = new Repair();
//        repair.setId(1);
//        double cost = 100;
//        adminService.proposeCosts(repair, cost);
//        verify(repairRepository, times(1)).updateCost(repair.getId(), cost);
//
//    }
//
//}
//
//
