/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.NoArgsConstructor;

/**
 *
 * @author ADMIN
 */
@Path("propertyResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PropertyResource {

    @Inject
    private OwnerService ownerService;
    
   
    @GET
    public Response ping() {
        return Response
                .ok("propertypang")
                .build();
    }

    @POST
    @Path("property")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewProperty(PropertyDto property) {
        ownerService.registerNewPropertyDto(property);
    }

    @DELETE
    @Path("/{id}")
    @Consumes("application/json")
    public void deleteProperty(@PathParam("id") int id){
        ownerService.deleteProperty(id);
    }



}
