/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

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
    @Produces("application/json")
    public void getAll() {
        ownerService.getAllProperties();
    }

    @GET
    @Path("/property/{propertyId}")
    @Produces("application/json")
    public RestApiResult<PropertyDto> getProperty(@PathParam("propertyId") int propertyId) {
        return ownerService.getProperty(propertyId);
    }

    @PUT
    @Path("/property/{propertyId}")
    @Consumes("application/json")
    public RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, @PathParam("propertyId") int propertyId) {
        return ownerService.updateProperty(propertyDto, propertyId);
    }

    @POST
    @Path("property")
    @Produces("application/json")
    @Consumes("application/json")
    public void createNewProperty(PropertyDto property) {
        ownerService.registerNewPropertyDto(property);
    }

    @DELETE
    @Path("property/{id}")
    @Consumes("application/json")
    public boolean deleteProperty(@PathParam("id") int id) {
        return ownerService.deleteProperty(id);
    }

    @DELETE
    @Path("properties")
    public void deleteAll() {
        ownerService.deleteAllProperties();
    }

}
