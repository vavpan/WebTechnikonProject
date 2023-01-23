/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webtechnikonproject.resources;

import com.mycompany.webtechnikonproject.dto.PropertyDto;
import com.mycompany.webtechnikonproject.dto.RestApiResult;
import com.mycompany.webtechnikonproject.enums.PropertyType;
import com.mycompany.webtechnikonproject.services.OwnerService;
import jakarta.annotation.security.RolesAllowed;
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
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyDto> getProperty(@PathParam("propertyId") int propertyId) {
        return ownerService.getProperty(propertyId);
    }

    @GET
    @Path("/properties")
    @Produces("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public List<PropertyDto> getAllProperties() {
        return ownerService.getAllProperties();
    }

    @GET
    @Path("/property/e9/{e9}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyDto returnOwnerByVat(@PathParam("e9") int e9) {
        return ownerService.getPropertyByE9(e9);
    }

    @GET
    @Path("/properties/ownerVat/{ownerVat}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @RolesAllowed({"ADMIN", "USER"})
    public List<PropertyDto> returnPropertiesByOwnervat(@PathParam("ownerVat") int ownerVat) {
        return ownerService.getPropertiesByOwnerVat(ownerVat);
    }

    @PUT
    @Path("/property/{propertyId}")
    @Produces("application/json")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public RestApiResult<PropertyDto> updateProperty(PropertyDto propertyDto, @PathParam("propertyId") int propertyId) {
        return ownerService.updateProperty(propertyDto, propertyId);
    }

    @PUT
    @Path("/updateAddress/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyDto updateProperty(@PathParam("propertyId") int propertyId, String address) {
        return ownerService.updatePropertyAddress(propertyId, address);
    }

    @PUT
    @Path("/yearOfConstruction/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyDto updateYearOfConstruction(@PathParam("propertyId") int propertyId, String yearOfConstruction) {
        return ownerService.updateYearOfConstruction(propertyId, yearOfConstruction);
    }

    @PUT
    @Path("/updatePropertyType/{propertyId}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.TEXT_PLAIN)
    @RolesAllowed({"ADMIN", "USER"})
    public PropertyDto updatePropertyType(@PathParam("propertyId") int propertyId, PropertyType propertyType) {
        return ownerService.updatePropertyType(propertyId, propertyType);
    }

    @POST
    @Path("property")
    @Produces("application/json") 
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public void createNewProperty(PropertyDto property) {
        ownerService.registerNewPropertyDto(property);
    }

    @DELETE
    @Path("property/{id}")
    @Consumes("application/json")
    @RolesAllowed({"ADMIN", "USER"})
    public boolean deleteProperty(@PathParam("id") int id) {
        return ownerService.deleteProperty(id);
    }

}
