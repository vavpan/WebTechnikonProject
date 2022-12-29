package com.mycompany.webtechnikonproject.resources;


import com.mycompany.webtechnikonproject.services.AdminService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;


@Path("admin")
public class AdminResource {
    
    @Inject
    private AdminService adminService;
    
    
    @GET
    @Path("pendingRepairs")
    @Produces("application/json")
    public void getPendingRepairs(){
        adminService.displayPendingRepairs();
    }
    
    
   
    
}
