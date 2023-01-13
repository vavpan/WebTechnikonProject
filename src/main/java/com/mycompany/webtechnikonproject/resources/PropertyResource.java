/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import java.util.List;

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
    @Path("/property/{propertyId}")
    @Produces("application/json")
    public RestApiResult<PropertyDto> getProperty(@PathParam("propertyId") int propertyId) {
        return ownerService.getProperty(propertyId);
    }

    @GET
    @Path("/properties")
    @Produces("application/json")
    public List<PropertyDto> getAllProperties() {
        return ownerService.getAllProperties();
    }

    @PUT
    @Path("/property/{propertyId}")
    @Produces("application/json")
    @Consumes("application/json")
    public RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, @PathParam("propertyId") int propertyId) {
        return ownerService.updateProperty(propertyDto, propertyId);
    }

    @PUT
    @Path("/updateAddress/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyDto updateProperty(@PathParam("propertyId") int propertyId, String address) {
        return ownerService.updatePropertyAddress(propertyId, address);
    }

    @PUT
    @Path("/yearOfConstruction/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyDto updateYearOfConstruction(@PathParam("propertyId") int propertyId, String yearOfConstruction) {
        return ownerService.updateYearOfConstruction(propertyId, yearOfConstruction);
    }

    @PUT
    @Path("/updatePropertyType/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    public PropertyDto updatePropertyType(@PathParam("propertyId") int propertyId, PropertyType propertyType) {
        return ownerService.updatePropertyType(propertyId, propertyType);
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

}
