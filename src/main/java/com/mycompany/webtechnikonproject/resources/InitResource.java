/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.model.PropertyOwner;
import com.mycompany.webtechnikonproject.services.IoServices;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

/**
 *
 * @author ADMIN
 */
@Path("/initialization")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class InitResource {
    
    @Inject
    private IoServices ioServices;
    
    @GET
    public void init() {
        
        ioServices.readOwnersCsv("C:\\Users\\vavil\\Documents\\NetBeansProjects\\WebTechnikonProject\\owners.csv");
        ioServices.readPropertyCsv("C:\\Users\\vavil\\Documents\\NetBeansProjects\\WebTechnikonProject\\property.csv");
        ioServices.readRepairCsv("C:\\Users\\vavil\\Documents\\NetBeansProjects\\WebTechnikonProject\\repairs.csv");
//        ioServices.relationshipsBetweenObjects();
        
    }
    
}
